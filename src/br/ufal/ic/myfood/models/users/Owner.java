package br.ufal.ic.myfood.models.users;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

public class Owner extends User {

    private String cpf;

    public Owner(){}

    public Owner(String id, String name, String email, String password, String adress, String cpf) {
        super(id, name, email, password, adress);
        this.cpf = cpf;
    }

    @Override
    public String getAtribute(String atribute) throws AtributoInvalido {

        if (atribute.equalsIgnoreCase("cpf")) {
            return getCpf();
        }

        return super.getAtribute(atribute);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
