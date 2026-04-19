package br.ufal.ic.myfood.exceptions;

public class PedidoFechado extends Exception{
    public PedidoFechado(){super("Nao e possivel adcionar produtos a um pedido fechado");}
}
