package br.ufal.ic.myfood.models.users;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String adress;

    public User(){}

    public User(String id, String name, String email, String password, String adress) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.adress = adress;
    }

    public String getAtribute(String atribute) throws AtributoInvalido {

        return switch (atribute.toLowerCase()) {
            case "nome"     -> getName();
            case "email"    -> getEmail();
            case "endereco" -> getAdress();
            case "senha"    -> getPassword();
            default -> throw new AtributoInvalido();
        };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString(){
        return this.getId();
    }
}
