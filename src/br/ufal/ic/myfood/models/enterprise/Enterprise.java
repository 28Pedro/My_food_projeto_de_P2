package br.ufal.ic.myfood.models.enterprise;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public abstract class Enterprise {

    private String type;
    private String name;
    private String adress;
    private String ownerId;
    private String id;


    public Enterprise() {
    }

    public Enterprise(String type,String ownerId, String name, String adress, String id) {

        this.type = type;
        this.name = name;
        this.adress = adress;
        this.ownerId = ownerId;
        this.id = id;

    }

    public String getAtribute(String atribute) throws AtributoInvalido{
        return switch (atribute) {
            case "nome" -> this.getName();
            case "endereco" -> this.getAdress();
            case "dono" -> this.getOwnerId();
            case "tipoEmpresa" -> this.getType();

            default -> throw new AtributoInvalido();
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[" + getName() + ", " + getAdress() + "]";
    }
}
