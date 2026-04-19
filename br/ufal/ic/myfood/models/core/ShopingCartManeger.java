package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.ShopingCartDataManeger;
import br.ufal.ic.myfood.models.integrators.EnterpriseIntegrator;
import br.ufal.ic.myfood.models.integrators.ProductIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;
import br.ufal.ic.myfood.models.shopingCart.Order;
import br.ufal.ic.myfood.models.validator.ShopingCartValidator;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopingCartManeger {

    private ShopingCartDataManeger shopingCartDataManeger;
    private ShopingCartValidator shopingCartValidator;
    private ProductIntegrator productIntegrator;
    private UserIntegrator userIntegrator;
    private EnterpriseIntegrator enterpriseIntegrator;

    public ShopingCartManeger(UserIntegrator userIntegrator, ProductIntegrator productIntegrator,
                            EnterpriseIntegrator enterpriseIntegrator)
    throws FileError {
        this.productIntegrator = productIntegrator;
        this.userIntegrator = userIntegrator;
        this.enterpriseIntegrator = enterpriseIntegrator;
        shopingCartDataManeger = new ShopingCartDataManeger();
        shopingCartValidator = new ShopingCartValidator(shopingCartDataManeger, userIntegrator,
                productIntegrator);
    }

    public String createOrder(String ClientId, String enterpiseId)
        throws DoisPedidosMesmaEmpresa, DonoNaoPodeFazerPedido {
        shopingCartValidator.validateOrder(ClientId,enterpiseId);

        String id = generateId();
        Order order = new Order(id, ClientId, enterpiseId, "aberto", new ArrayList<>());
        shopingCartDataManeger.saveObject(order);

        return id;
    }

    public void addProduct(String orderId, String productId) throws Exception {

        shopingCartValidator.validateAddProduct(orderId,productId);

        try {
            Order order = shopingCartDataManeger.getOrderById(orderId);
            PairKey<String,Float> key = productIntegrator.getProductInfo(productId);
            order.addProduct(key);
        }catch (Exception e){
            throw new NaoExistePedidoEmAberto();
        }

    }

    public String getOrderAtribute(String orderId, String atribute) throws Exception {

        shopingCartValidator.validadateGetAtribute(orderId, atribute);

        try {
            String result = "";

            Order order = shopingCartDataManeger.getOrderById(orderId);
            if("cliente".equalsIgnoreCase(atribute)) {
                result = userIntegrator.getUserNameById(order.getClientId());
            } else if("empresa".equalsIgnoreCase(atribute)) {
                result = enterpriseIntegrator.getEnterpiseNameById(order.getEnterpriseId());
            } else {
                result = order.getAtribute(atribute);
            }

            if(result.isBlank()){
                throw new AtributoNaoExiste();
            };

            return result;

        }catch (PedidoNaoEncontrado e){
            throw new AtributoNaoExiste();
        }
    }

    public void closeOrder(String orderId) throws Exception {
        Order order = shopingCartDataManeger.getOrderById(orderId);
        if(order == null) {
            throw new Exception("Pedido nao encontrado");
        }
        order.setState("preparando");
        shopingCartDataManeger.changeOrderState(order.getClientId(), order.getEnterpriseId());
    }

    public void removeProduct(String orderId, String productName) throws Exception {
        if(productName == null || productName.trim().isEmpty()) {
            throw new Exception("Produto invalido");
        }

        Order order = shopingCartDataManeger.getOrderById(orderId);
        if(order == null) {
            throw new Exception("Pedido nao encontrado");
        }

        if(order.isState().equals("preparando")) {
            throw new Exception("Nao e possivel remover produtos de um pedido fechado");
        }

        List<PairKey<String,Float>> products = order.getProducts();
        boolean found = false;

        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getFirst().equals(productName)) {
                float price = products.get(i).getSecond();
                products.remove(i);
                order.setSubtotal(order.getSubtotal() - price);
                found = true;
                break;
            }
        }

        if(!found) {
            throw new Exception("Produto nao encontrado");
        }

        shopingCartDataManeger.saveData();
    }

    public String getOrderNumber(String clientId, String enterpriseId, int index) throws Exception {
        List<String> allOrders = shopingCartDataManeger.getAllOrdersByClientEnterprise(clientId, enterpriseId);
        if(allOrders == null || index >= allOrders.size()) {
            throw new IndiceMaiorQueEsperado();
        }
        return allOrders.get(index);
    }

    public void saveData() throws SaveError {
        shopingCartDataManeger.saveData();
    }

    public void resetData(){
        shopingCartDataManeger.resetData();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
