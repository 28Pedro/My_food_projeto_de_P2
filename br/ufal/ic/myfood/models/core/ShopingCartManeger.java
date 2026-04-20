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

    public void addProduct(String orderId, String productId) throws NaoExistePedidoEmAberto,
            AdicionarEmPedidoFechado, ProdutoNaoPertenceAEmpresa{

        shopingCartValidator.validateAddProduct(orderId,productId);

        try {
            Order order = shopingCartDataManeger.getOrderById(orderId);
            PairKey<String,Float> key = productIntegrator.getProductInfo(productId);
            order.addProduct(key);
        }catch (Exception e){
            throw new NaoExistePedidoEmAberto();
        }

    }

    public String getOrderAtribute(String orderId, String atribute) throws AtributoInvalido,
            PedidoNaoEncontrado, AtributoNaoExiste, UsuarioNaoExisteException,
            EmpresanaoCadastrada {

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

    public void closeOrder(String orderId) throws PedidoNaoEncontrado {
        Order order = shopingCartDataManeger.getOrderById(orderId);

        order.setState("preparando");
        shopingCartDataManeger.changeOrderState(order.getClientId(), order.getEnterpriseId());
    }

    public void removeProduct(String orderId, String productName) throws ProdutoInvalido,
            RemoverEmPedidoFechado, ProdutoNaoEncontrado, PedidoNaoEncontrado {

        shopingCartValidator.validateRemoveProduct(orderId,productName);

        Order order = shopingCartDataManeger.getOrderById(orderId);
        order.removeProductByName(productName);

    }

    public String getOrderNumber(String clientId, String enterpriseId, int index) throws IndiceMaiorQueEsperado {
        List<String> allOrders = shopingCartDataManeger.getAllOrdersByClientEnterprise(clientId, enterpriseId);
        shopingCartValidator.getOrderNumberValidator(allOrders,index);
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
