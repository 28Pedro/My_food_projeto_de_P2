package br.ufal.ic.myfood.models.users;

public class Owner extends User {

    public Owner(){}

    public Owner(String id, String name, String email, String password, String adress, String cpf) {
        super(id, name, email, password, adress, cpf);
    }
}
