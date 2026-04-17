package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.UserDataManage;
import br.ufal.ic.myfood.models.validator.UserValidator;
import br.ufal.ic.myfood.models.users.Client;
import br.ufal.ic.myfood.models.users.Owner;
import br.ufal.ic.myfood.models.users.User;

import java.util.UUID;

public class UserManager {

   private UserDataManage userDataManage;
   private UserValidator userDataValidation;

    public UserManager() throws FileError {
        this.userDataManage = new UserDataManage();
        this.userDataValidation = new UserValidator();

    }

    public void createUser(String name, String email,String password,String adress)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        ValidateComunUserRules(name,email,password,adress);

        User newClient = new Client(generateId(),name,email,password,adress,null);
        userDataManage.saveObject(newClient);
    }

    public void  createUser(String name, String email, String password, String adress, String CPF)
            throws CPFinvalido,UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        userDataValidation.isCPFValid(CPF);

        ValidateComunUserRules(name,email,password,adress);

        User newOwner = new Owner(generateId(),name,email,password,adress,CPF);
        userDataManage.saveObject(newOwner);
    }

    public String login(String email, String password) throws LoginError{
        if(userDataManage.emailExists(email)){

            try {
                String id = userDataManage.getIdByEmail(email);

                User curent = userDataManage.getUserById(id);

                if (userDataValidation.validatepassworld(curent.getPassword(),
                        password)) {
                    return curent.toString();
                }
            } catch (UsuarioNaoExisteException e) {
                throw new LoginError();
            }
        }
        throw new LoginError();
    }

    public String getAtributebyId(String id, String atribute) throws UsuarioNaoExisteException{

        if(!userDataManage.idExists(id))
        {
            throw new UsuarioNaoExisteException();
        }

        return userDataManage.getAtributeById(id, atribute);
    }

    public boolean userIsOwner(String id) throws UsuarioNaoExisteException{

        User user = userDataManage.getUserById(id);
        return user instanceof Owner;
    }

    public boolean userIsClient(String id) throws UsuarioNaoExisteException{
        User user = userDataManage.getUserById(id);
        return user instanceof Client;
    }

    public void saveData() throws SaveError{
        userDataManage.saveData();
    }

    public void resetData(){
        userDataManage.resetData();
    }

    private void ValidateComunUserRules(String name, String email,String password,String adress)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        userDataValidation.ValidateComunUserRuler(name,email,password,adress);

        if(userDataManage.emailExists(email)){
            throw new UsuarioJaExisteException();
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
