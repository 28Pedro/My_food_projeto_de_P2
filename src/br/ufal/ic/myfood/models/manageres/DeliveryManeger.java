package br.ufal.ic.myfood.models.manageres;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.DeliveryDataManeger;
import br.ufal.ic.myfood.models.delivery.Delivery;
import br.ufal.ic.myfood.models.integrators.OrderIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;
import br.ufal.ic.myfood.models.validator.DeliveryValidator;
import br.ufal.ic.myfood.records.OrderInfo;

import java.util.UUID;

public class DeliveryManeger{

    private DeliveryDataManeger deliveryDataManeger;
    private DeliveryValidator deliveryValidator;
    private OrderIntegrator orderIntegrator;
    private UserIntegrator userIntegrator;

  public  DeliveryManeger(UserIntegrator userIntegrator, OrderIntegrator orderIntegrator) throws FileError {
        deliveryDataManeger = new DeliveryDataManeger();
        deliveryValidator = new DeliveryValidator(deliveryDataManeger,userIntegrator,orderIntegrator);
        this.orderIntegrator = orderIntegrator;
        this.userIntegrator = userIntegrator;
    }

    public String createDelivery(String orderId, String deliveryManId, String destination)
    throws PedidoNaoEstaPronto,PedidoNaoEncontrado, NaoEUmEntregadorValido,EntregadorAindaEmEntrega {

        deliveryValidator.validateDeliveryRequest(orderId,deliveryManId);
        OrderInfo orderInfo = orderIntegrator.getOrderInfo(orderId);

        String id = generateId();

        Delivery delivery = new Delivery(id,
                orderInfo.client(),
                orderInfo.enterpriseId(),
                orderInfo.orderId(),
                deliveryManId,
                destination,
                orderInfo.productList()
        );

        try {
            orderIntegrator.makeDelivery(orderId, deliveryManId);

        } catch (EmpresanaoCadastrada | UsuarioNaoEEntregador | UsuarioNaoExisteException e) {
            throw new NaoEUmEntregadorValido();
        }

        deliveryDataManeger.atributeDeliveryToDeliveryMan(id,deliveryManId);

        deliveryDataManeger.saveObject(delivery);

        return id;

    }

    public void saveData() throws SaveError {
       deliveryDataManeger.saveData();
    }

    public void resetData(){
        deliveryDataManeger.resetData();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
