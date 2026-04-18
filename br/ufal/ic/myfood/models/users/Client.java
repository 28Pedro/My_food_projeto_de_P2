package br.ufal.ic.myfood.models.users;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public class Client extends User{

    public Client(){}

    public Client(String id, String name, String email, String password, String adress) {
        super(id, name, email, password, adress);
    }

}
