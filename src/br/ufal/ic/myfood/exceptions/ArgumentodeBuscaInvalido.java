package br.ufal.ic.myfood.exceptions;

public class ArgumentodeBuscaInvalido extends IllegalArgumentException{

   public ArgumentodeBuscaInvalido(String atribute){super("Atributo desconhecido: " + atribute);}
}
