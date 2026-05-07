package br.ufal.ic.myfood.models.manageres;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.OrderDataManeger;
import br.ufal.ic.myfood.models.integrators.EnterpriseIntegrator;
import br.ufal.ic.myfood.models.integrators.ProductIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;
import br.ufal.ic.myfood.models.order.Order;
import br.ufal.ic.myfood.models.validator.OrderValidator;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderManeger {

    private OrderDataManeger orderDataManeger;
    private OrderValidator orderValidator;
    private ProductIntegrator productIntegrator;
    private UserIntegrator userIntegrator;
    private EnterpriseIntegrator enterpriseIntegrator;

    public OrderManeger(UserIntegrator userIntegrator, ProductIntegrator productIntegrator,
                        EnterpriseIntegrator enterpriseIntegrator)
    throws FileError {
        this.productIntegrator = productIntegrator;
        this.userIntegrator = userIntegrator;
        this.enterpriseIntegrator = enterpriseIntegrator;
        orderDataManeger = new OrderDataManeger();
        orderValidator = new OrderValidator(orderDataManeger, userIntegrator,
                productIntegrator);
    }

    public String createOrder(String ClientId, String enterpiseId)
        throws DoisPedidosMesmaEmpresa, DonoNaoPodeFazerPedido {
        orderValidator.validateOrder(ClientId,enterpiseId);

        String id = generateId();
        Order order = new Order(id, ClientId, enterpiseId, "aberto", new ArrayList<>());
        orderDataManeger.saveObject(order);

        return id;
    }

    public void addProduct(String orderId, String productId) throws NaoExistePedidoEmAberto,
            AdicionarEmPedidoFechado, ProdutoNaoPertenceAEmpresa{

        orderValidator.validateAddProduct(orderId,productId);

        try {
            Order order = orderDataManeger.getOrderById(orderId);
            PairKey<String,Float> key = productIntegrator.getProductInfo(productId);
            order.addProduct(key);
        }catch (Exception e){
            throw new NaoExistePedidoEmAberto();
        }

    }

    public String getOrderAtribute(String orderId, String atribute) throws AtributoInvalido,
            PedidoNaoEncontrado, AtributoNaoExiste, UsuarioNaoExisteException,
            EmpresanaoCadastrada {

        orderValidator.validadateGetAtribute(orderId, atribute);

        try {
            String result = "";

            Order order = orderDataManeger.getOrderById(orderId);
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

    public Order getOrderById(String orderId) throws PedidoNaoEncontrado{
        return orderDataManeger.getOrderById(orderId);
    }

    public void closeOrder(String orderId) throws PedidoNaoEncontrado {
        Order order = orderDataManeger.getOrderById(orderId);

        order.setState("preparando");
        orderDataManeger.changeOrderState(order.getClientId(), order.getEnterpriseId());
    }

    public void removeProduct(String orderId, String productName) throws ProdutoInvalido,
            RemoverEmPedidoFechado, ProdutoNaoEncontrado, PedidoNaoEncontrado {

        orderValidator.validateRemoveProduct(orderId,productName);

        Order order = orderDataManeger.getOrderById(orderId);
        order.removeProductByName(productName);

    }

    public String getOrderNumber(String clientId, String enterpriseId, int index) throws IndiceMaiorQueEsperado {
        List<String> allOrders = orderDataManeger.getAllOrdersByClientEnterprise(clientId, enterpriseId);
        orderValidator.getOrderNumberValidator(allOrders,index);
        return allOrders.get(index);
    }

    public void releaseOrder(String orderId)
    throws PedidoNaoEncontrado,EmpresanaoCadastrada,LiberarPedidoAberto,PedidoJaLiberado,
            UsuarioNaoEEntregador, UsuarioNaoExisteException{

        Order order = orderDataManeger.getOrderById(orderId);
        orderValidator.validateReleaseOrder(order);

        String enterpriseId = order.getEnterpriseId();

        order.setState("pronto");

        orderDataManeger.addprontOrder(enterpriseId,orderId);


        List<String> deliveryManEmailList = enterpriseIntegrator.getDeliveryManList(enterpriseId);

        boolean priority = enterpriseIntegrator.enterpriseIsPharmacy(enterpriseId);

        userIntegrator.addDeliveryManListOrder(deliveryManEmailList,orderId,priority);

    }

    public void MakeDelivery(String orderId, String deliveryManId)
    throws PedidoNaoEncontrado,EmpresanaoCadastrada,UsuarioNaoEEntregador,
            UsuarioNaoExisteException{

        Order order = orderDataManeger.getOrderById(orderId);
       order.setState("entregando");

       String enterpriseId = order.getEnterpriseId();
       List<String> deliveryManEmailList = enterpriseIntegrator.getDeliveryManList(enterpriseId);

       boolean priority = enterpriseIntegrator.enterpriseIsPharmacy(enterpriseId);

       userIntegrator.removeDeliveryManListOrder(deliveryManEmailList,orderId,priority,deliveryManId);

    }

    public void finishDelivery(String orderId) throws PedidoNaoEncontrado{
       Order order = orderDataManeger.getOrderById(orderId);
       order.setState("entregue");
    }

    public void saveData() throws SaveError {
        orderDataManeger.saveData();
    }

    public void resetData(){
        orderDataManeger.resetData();
    }

    public List<String> getProntOrdersByEnterprise(String enterpriseId){
        return orderDataManeger.getProntOrdersByEnterprise(enterpriseId);
    }

    public boolean orderExists(String orderId){
        return orderDataManeger.orderExists(orderId);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
