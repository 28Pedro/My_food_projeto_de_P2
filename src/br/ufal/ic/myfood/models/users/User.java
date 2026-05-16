package br.ufal.ic.myfood.models.users;

import br.ufal.ic.myfood.enums.UserType;
import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String adress;
    private UserType userType;

    public User(){}

    public User(String id, String name, String email, String password, String adress, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.userType = userType;
    }

    public String getAtribute(String atribute) throws AtributoInvalido {

        return switch (atribute) { // retirado o to lowerCase, tomar cuidado com erros
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString(){
        return this.getId();
    }
}
