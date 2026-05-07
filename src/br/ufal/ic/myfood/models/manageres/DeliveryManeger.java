package br.ufal.ic.myfood.models.manageres;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.DeliveryDataManeger;
import br.ufal.ic.myfood.models.delivery.Delivery;
import br.ufal.ic.myfood.models.integrators.EnterpriseIntegrator;
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
    private EnterpriseIntegrator enterpriseIntegrator;

  public  DeliveryManeger(UserIntegrator userIntegrator, OrderIntegrator orderIntegrator,
    EnterpriseIntegrator enterpriseIntegrator) throws FileError {
        deliveryDataManeger = new DeliveryDataManeger();
        deliveryValidator = new DeliveryValidator(deliveryDataManeger,userIntegrator,orderIntegrator);
        this.orderIntegrator = orderIntegrator;
        this.userIntegrator = userIntegrator;
        this.enterpriseIntegrator = enterpriseIntegrator;
    }

    public String createDelivery(String orderId, String deliveryManId, String destination)
    throws PedidoNaoEstaPronto,PedidoNaoEncontrado, NaoEUmEntregadorValido,EntregadorAindaEmEntrega,
            NaoExistePedidoParaEntrega{

        deliveryValidator.validateDeliveryRequest(orderId,deliveryManId);
        OrderInfo orderInfo = orderIntegrator.getOrderInfo(orderId);

        String id = generateId();

        String clientId = orderInfo.client();

        if (destination == null){
            try {
               destination = userIntegrator.getuserAdress(clientId);
            } catch (UsuarioNaoExisteException | AtributoInvalido e) {
                throw new NaoExistePedidoParaEntrega();
            }
        }

        Delivery delivery = new Delivery(id,
                clientId,
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

    public String getDeliveryAtributeById(String deliveryId, String atribute)
    throws AtributoNaoExiste,AtributoInvalido, NaoExisteEntregaId {

      deliveryValidator.validateAtrinbute(atribute);

      Delivery delivery = deliveryDataManeger.getDeliverybyId(deliveryId);

      return switch (atribute){
          case "cliente"     -> {
              try {
                 yield  userIntegrator.getUserNameById(delivery.getClient());
              } catch (UsuarioNaoExisteException e) {
                  throw new AtributoInvalido();
              }
          }
          case "empresa"    -> {
              try {
                 yield  enterpriseIntegrator.getEnterpriseNameById(delivery.getEnterprise());
              } catch (EmpresanaoCadastrada e) {
                  throw new AtributoInvalido();
              }
          }
          case "entregador"    -> {
              try {
                 yield  userIntegrator.getUserNameById(delivery.getDeliveryManId());
              } catch (UsuarioNaoExisteException e) {
                  throw new AtributoInvalido();
              }
          }

          default -> delivery.getAtribute(atribute);

      };

    }

    public String getDeliveryIdbyOrderId(String orderId)throws NaoExisteEntregaId{
      return deliveryDataManeger.getDeliveryByOrderId(orderId);
    }

    public void finishDelivery(String deliveryId) throws NadaParaSerEntregue{
      try {
         Delivery delivery = deliveryDataManeger.getDeliverybyId(deliveryId);
         orderIntegrator.finishDelivery(delivery.getOrderId());
         deliveryDataManeger.removeDeliveryToDeliveryMan(delivery.getDeliveryManId());
      } catch (NaoExisteEntregaId | PedidoNaoEncontrado e) {
          throw new NadaParaSerEntregue();
      }

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
