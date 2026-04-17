package br.ufal.ic.myfood.models.users;

public class Client extends User{

    public Client(){}

    public Client(String id, String name, String email, String password, String adress, String cpf) {
        super(id, name, email, password, adress, null);
    }

}
