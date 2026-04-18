package br.ufal.ic.myfood.models.enterprise;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public class Restaurant extends Enterprise{

    private String kitchenType;

    public Restaurant() {}

    public Restaurant(String type ,String ownerId, String name, String adress, String id, String kitchenType) {
        super(type,ownerId, name, adress, id);
        this.kitchenType = kitchenType;
    }

    @Override
    public String getAtribute(String atribute) throws AtributoInvalido {

        if (atribute.equalsIgnoreCase("tipoCozinha")) {
            return getKitchenType();
        }else{
            return super.getAtribute(atribute);
        }
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }
}
