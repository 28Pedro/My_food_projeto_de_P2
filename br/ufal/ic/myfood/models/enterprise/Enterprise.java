package br.ufal.ic.myfood.models.enterprise;

public abstract class Enterprise {
    private String entreprisetype;
    private String name;
    private String adress;
    private String ownerId;
    private String id;
    private String kitchenType;

    public Enterprise() {
    }

    public Enterprise(String entreprisetype, String ownerId, String name, String adress, String kitchenType, String id) {
        this.entreprisetype = entreprisetype;
        this.name = name;
        this.adress = adress;
        this.ownerId = ownerId;
        this.id = id;
        this.kitchenType = kitchenType;
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

    public void setEntreprisetype(String entreprisetype) {
        this.entreprisetype = entreprisetype;
    }

    public String getEntreprisetype() {
        return entreprisetype;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    @Override
    public String toString() {
        return "[" + getName() + ", " + getAdress() + "]";
    }
}
