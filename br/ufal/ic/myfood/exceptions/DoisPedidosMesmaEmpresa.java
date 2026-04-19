package br.ufal.ic.myfood.exceptions;

public class DoisPedidosMesmaEmpresa extends Exception{
    public DoisPedidosMesmaEmpresa(){super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");}
}
