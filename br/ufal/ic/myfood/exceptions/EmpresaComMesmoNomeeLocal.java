package br.ufal.ic.myfood.exceptions;

public class EmpresaComMesmoNomeeLocal extends Exception{
    public EmpresaComMesmoNomeeLocal(){super("Proibido cadastrar duas empresas com o mesmo nome e local");}
}
