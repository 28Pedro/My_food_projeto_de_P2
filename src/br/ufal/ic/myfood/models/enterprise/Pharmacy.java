package br.ufal.ic.myfood.models.enterprise;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public class Pharmacy extends Enterprise{
    private boolean open24Hours;
    private int numberOfEmploys;

    public Pharmacy() {
    }

    public Pharmacy(String type, String ownerId, String name, String adress, String id,
                    boolean open24Hours, int numberOfEmploys) {
        super(type, ownerId, name, adress, id);
        this.open24Hours = open24Hours;
        this.numberOfEmploys = numberOfEmploys;
    }

    @Override
    public String getAtribute(String atribute) throws AtributoInvalido {

        return switch (atribute) {
            case "aberto24Horas" -> Boolean.toString(this.getOpen24Hours());
            case "numeroFuncionarios" -> Integer.toString(this.getNumberOfEmploys());
            default -> super.getAtribute(atribute);
        };
    }


    public boolean getOpen24Hours() {
        return open24Hours;
    }

    public void setOpen24Hours(boolean open24Hours) {
        this.open24Hours = open24Hours;
    }

    public int getNumberOfEmploys() {
        return numberOfEmploys;
    }

    public void setNumberOfEmploys(int numberOfEmploys) {
        this.numberOfEmploys = numberOfEmploys;
    }
}
