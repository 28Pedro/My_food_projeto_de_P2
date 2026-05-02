package br.ufal.ic.myfood.exceptions;

public class LiberarPedidoAberto extends Exception{
    public LiberarPedidoAberto(){super("Nao e possivel liberar um produto que nao esta sendo preparado");}
}
