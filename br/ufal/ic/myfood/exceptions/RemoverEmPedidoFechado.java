package br.ufal.ic.myfood.exceptions;

public class RemoverEmPedidoFechado extends Exception{
    public RemoverEmPedidoFechado(){super("Nao e possivel remover produtos de um pedido fechado");}
}
