package br.ufal.ic.myfood.exceptions;

public class AdicionarEmPedidoFechado extends Exception{
    public AdicionarEmPedidoFechado(){super("Nao e possivel adcionar produtos a um pedido fechado");}
}
