package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.exceptions.NaoEUmEntregadorValido;
import br.ufal.ic.myfood.exceptions.PedidoNaoEncontrado;
import br.ufal.ic.myfood.exceptions.PedidoNaoEstaPronto;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.database.DeliveryDataManeger;
import br.ufal.ic.myfood.models.integrators.OrderIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;

public class DeliveryValidator extends Validator<DeliveryDataManeger> {

    UserIntegrator userIntegrator;
    OrderIntegrator orderIntegrator;

    public DeliveryValidator(DeliveryDataManeger deliveryDataManeger, UserIntegrator userIntegrator,
                             OrderIntegrator orderIntegrator){
        super(deliveryDataManeger);
        this.userIntegrator = userIntegrator;
        this.orderIntegrator = orderIntegrator;
    }

    public void validateDeliveryRequest(String orderId, String deliveryManId)
    throws PedidoNaoEncontrado,PedidoNaoEstaPronto,NaoEUmEntregadorValido {

        if(!orderIntegrator.orderIsReady(orderId)){
            throw new PedidoNaoEstaPronto();
        }

        try {
            if(!userIntegrator.userIsDeliveryMan(deliveryManId)){
                throw new NaoEUmEntregadorValido();
            }
        } catch (UsuarioNaoExisteException e) {
            throw new NaoEUmEntregadorValido();
        }


    }



}
