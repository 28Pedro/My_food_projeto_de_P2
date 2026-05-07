package br.ufal.ic.myfood.models.integrators;

import br.ufal.ic.myfood.exceptions.EmpresanaoCadastrada;
import br.ufal.ic.myfood.exceptions.PedidoNaoEncontrado;
import br.ufal.ic.myfood.exceptions.UsuarioNaoEEntregador;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.manageres.OrderManeger;
import br.ufal.ic.myfood.models.order.Order;
import br.ufal.ic.myfood.records.OrderInfo;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;

public class OrderIntegrator {

    private final OrderManeger orderManeger;

    public OrderIntegrator(OrderManeger orderManeger){
        this.orderManeger = orderManeger;
    }

    public OrderInfo getOrderInfo(String orderId)
    throws PedidoNaoEncontrado {

        Order order = orderManeger.getOrderById(orderId);

        List<PairKey<String,Float>> orderProductList = order.getProducts();
        List<String> deliveryProductList = new ArrayList<>();

        for(PairKey<String,Float> product : orderProductList){
            deliveryProductList.add(product.getFirst());
        }

        return new OrderInfo(order.getClientId(),
                             order.getEnterpriseId(),
                             order.getId(),
                             deliveryProductList
        );
    }

    public boolean orderIsReady(String orderId) throws PedidoNaoEncontrado{

        Order order = orderManeger.getOrderById(orderId);

        return order.getState().equals("pronto");

    }

    public void makeDelivery(String orderId,String deliveryManId)
    throws PedidoNaoEncontrado, EmpresanaoCadastrada, UsuarioNaoEEntregador, UsuarioNaoExisteException {
        orderManeger.MakeDelivery(orderId,deliveryManId);
    }

    public void finishDelivery(String orderId) throws PedidoNaoEncontrado {
        orderManeger.finishDelivery(orderId);
    }

    public boolean orderExists(String orderId){
       return orderManeger.orderExists(orderId);
    }

}
