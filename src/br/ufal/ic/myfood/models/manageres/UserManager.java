package br.ufal.ic.myfood.models.manageres;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.UserDataManage;
import br.ufal.ic.myfood.models.integrators.EnterpriseIntegrator;
import br.ufal.ic.myfood.models.users.DeliveryMan;
import br.ufal.ic.myfood.models.validator.UserValidator;
import br.ufal.ic.myfood.models.users.Client;
import br.ufal.ic.myfood.models.users.Owner;
import br.ufal.ic.myfood.models.users.User;
import br.ufal.ic.myfood.records.PairKey;

import java.util.UUID;

public class UserManager {

   private UserDataManage userDataManage;
   private UserValidator userDataValidation;
   private EnterpriseIntegrator enterpriseIntegrator;

    public UserManager() throws FileError {
        this.userDataManage = new UserDataManage();
        this.userDataValidation = new UserValidator(userDataManage);

    }

    public void createUser(String name, String email,String password,String adress)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        userDataValidation.ValidateComunUserRuler(name,email,password,adress);
        userDataValidation.validateEmailExists(email);

        User newClient = new Client(generateId(),name,email,password,adress);
        userDataManage.saveObject(newClient);
    }

    public void  createUser(String name, String email, String password, String adress, String CPF)
            throws CPFinvalido,UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        userDataValidation.isCPFValid(CPF);
        userDataValidation.ValidateComunUserRuler(name,email,password,adress);
        userDataValidation.validateEmailExists(email);

        User newOwner = new Owner(generateId(),name,email,password,adress,CPF);
        userDataManage.saveObject(newOwner);
    }

    public void createUser(String name, String email,String password, String adress, String vehicle,
            String licensePlate)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida,VeiculoInvalido,PlacaInvalido {

        userDataValidation.validateDeliveryManRules(vehicle,licensePlate);
        userDataValidation.ValidateComunUserRuler(name,email,password,adress);
        userDataValidation.validateEmailExists(email);

        User newDeliveryMan = new DeliveryMan(generateId(),name,email,password,adress,vehicle,
                                                licensePlate);

        userDataManage.saveObject(newDeliveryMan);
        userDataManage.saveLicencePlate((DeliveryMan)newDeliveryMan);
    }

    public void addDeliveryManEnterprise(String enterpriseId, String userId)
    throws UsuarioNaoExisteException,UsuarioNaoEEntregador,EmpresanaoCadastrada{

        User user = userDataManage.getUserById(userId);

        userDataValidation.validateUserIsDeliveryMan(user);

        PairKey<String,String> EnterpriseNameAdress = enterpriseIntegrator.
                getEnterpiseNameAdressById(enterpriseId);

        ((DeliveryMan) user).addEnterpise(EnterpriseNameAdress);
    }

    public String getEnterpisesByDeliveryMan(String userId) throws
    UsuarioNaoExisteException,UsuarioNaoEEntregador{

        User user = userDataManage.getUserById(userId);
        userDataValidation.validateUserIsDeliveryMan(user);

        return ((DeliveryMan)user).getEnterpizesList();

    }

    public String login(String email, String password) throws LoginError{
        return userDataValidation.validateLogin(email, password);
    }

    public String getAtributebyId(String id, String atribute)
            throws UsuarioNaoExisteException, AtributoInvalido{

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

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public void setEnterpriseIntegrator(EnterpriseIntegrator enterpriseIntegrator){
        this.enterpriseIntegrator = enterpriseIntegrator;
    }

}
