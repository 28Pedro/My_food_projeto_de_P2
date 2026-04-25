package br.ufal.ic.myfood.models.enterprise;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public class SuperMarket extends Enterprise {

    private String closes;
    private String open;
    private String supermarketType;

    public SuperMarket() {
    }

    public SuperMarket(String type, String ownerId, String name, String adress, String id, String closes, String open, String supermarketType) {
        super(type, ownerId, name, adress, id);
        this.closes = closes;
        this.open = open;
        this.supermarketType = supermarketType;
    }

    @Override
    public String getAtribute(String atribute) throws AtributoInvalido {

       return switch (atribute) {
            case "tipoMercado" -> this.getSupermarketType();
            case "abre" -> this.getOpen();
            case "fecha" -> this.getCloses();
            default -> super.getAtribute(atribute);
        };
    }

    public String getCloses() {
        return closes;
    }

    public void setCloses(String closes) {
        this.closes = closes;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getSupermarketType() {
        return supermarketType;
    }

    public void setSupermarketType(String supermarketType) {
        this.supermarketType = supermarketType;
    }

}
