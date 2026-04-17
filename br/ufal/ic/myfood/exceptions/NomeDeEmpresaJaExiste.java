package br.ufal.ic.myfood.exceptions;

public class NomeDeEmpresaJaExiste  extends Exception{
    public NomeDeEmpresaJaExiste(){super("Empresa com esse nome ja existe");}
}
