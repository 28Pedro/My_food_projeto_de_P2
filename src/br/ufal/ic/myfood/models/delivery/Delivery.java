package br.ufal.ic.myfood.models.delivery;

import java.util.List;

public class Delivery {
    private String id;
    private String client; //Nome do cliente no formato String, obtido do pedido.
    private String enterprise; // Nome da empresa no formato String, obtido do pedido.
    private String orderId;  // Recebe o numero do pedido a qual possui os itens a serem entregues, do tipo int.
    private String deliveryManId;  // Recebe id do int do entregador que vai realizar a entrega do pedido.
    private String destination;  //Recebe uma String com o endereço de entrega, caso null utiliza como padrão o endereço do cliente.
    private List<String> itensList;  // Lista com o nome de todos os produtos, obtido do perido.



}
