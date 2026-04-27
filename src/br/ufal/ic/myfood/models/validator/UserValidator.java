package br.ufal.ic.myfood.models.validator;
import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.UserDataManage;
import br.ufal.ic.myfood.models.users.DeliveryMan;
import br.ufal.ic.myfood.models.users.User;

public class UserValidator extends Validator<UserDataManage> {

    private final int CPF_SIZE = 14;

     public UserValidator(UserDataManage database){
        super(database);
    }

    public void ValidateComunUserRuler(String name, String email,String password,String adress)
            throws NomeInvalido, EnderecoInvalido, SenhaInvalida, EmailInvalido{

            if(!fildExists(name)){
                throw new NomeInvalido();
            }
            if(!validateEmail(email)){
                throw new EmailInvalido();
            }
            if(!fildExists(password)){
                throw new SenhaInvalida();
            }
            if(!fildExists(adress)){
                throw new EnderecoInvalido();
        }
    }

    public void isCPFValid(String cpf) throws CPFinvalido {

        if (cpf == null ||
                cpf.length() != CPF_SIZE ||
                !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
        ) { throw new CPFinvalido(); }

    }

    public String validateLogin(String email, String recivedPassworld)
            throws LoginError {
            try {
                String id = dataBase.getIdByEmail(email);
                String storedPassword = dataBase.getAtributeById(id, "senha");
                if(storedPassword.equals(recivedPassworld)){
                    return id;
                }
            } catch (Exception e) {
                throw new LoginError();
            }
        throw new LoginError();
    }

    public void validateDeliveryManRules(String veicle, String licensePlate)
    throws VeiculoInvalido,PlacaInvalido{

         if(!fildExists(veicle)){
             throw new VeiculoInvalido();
         }
         if(!fildExists(licensePlate) || dataBase.LicencePlateExits(licensePlate)){
             throw new PlacaInvalido();
         }

    }

    public void validateEmailExists(String email) throws UsuarioJaExisteException {
        if(dataBase.emailExists(email)){
            throw new UsuarioJaExisteException();
        }
    }

    public void validateUserIsDeliveryMan(User user)
    throws UsuarioNaoEEntregador{

         if(!(user instanceof DeliveryMan)){
             throw new UsuarioNaoEEntregador();
         }
    }

    private boolean validateEmail(String email){
        return  (fildExists(email) &&
              email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")
        );
    }
}