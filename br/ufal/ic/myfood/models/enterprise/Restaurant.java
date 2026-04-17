package br.ufal.ic.myfood.models.enterprise;

public class Restaurant extends Enterprise{

    public Restaurant() {}

    public Restaurant(String entreprisetype, String ownerId, String name, String adress, String kitchenType, String id) {
        super(entreprisetype, ownerId, name, adress, kitchenType, id);
    }
}
