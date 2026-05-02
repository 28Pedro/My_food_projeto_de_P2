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

    public Delivery() {
    }

    public Delivery(String id, String client, String enterprise, String orderId, String deliveryManId, String destination, List<String> itensList) {
        this.id = id;
        this.client = client;
        this.enterprise = enterprise;
        this.orderId = orderId;
        this.deliveryManId = deliveryManId;
        this.destination = destination;
        this.itensList = itensList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(String deliveryManId) {
        this.deliveryManId = deliveryManId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getItensList() {
        return itensList;
    }

    public void setItensList(List<String> itensList) {
        this.itensList = itensList;
    }
}
