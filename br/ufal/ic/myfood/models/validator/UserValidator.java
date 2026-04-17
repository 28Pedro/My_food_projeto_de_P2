package br.ufal.ic.myfood.models.validator;
import br.ufal.ic.myfood.exceptions.*;

public class UserValidator extends Validator{

    private final int CPF_SIZE = 14;

    public void ValidateComunUserRuler(String name, String email,String password,String adress)
            throws NomeInvalido, EnderecoInvalido, SenhaInvalida, EmailInvalido{

            if(!FildExists(name)){
                throw new NomeInvalido();
            }
            if(!validateEmail(email)){
                throw new EmailInvalido();
            }
            if(!FildExists(password)){
                throw new SenhaInvalida();
            }
            if(!FildExists(adress)){
                throw new EnderecoInvalido();
        }
    }

    public void isCPFValid(String cpf) throws CPFinvalido {

        if (cpf == null ||
                cpf.length() != CPF_SIZE ||
                !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
        ) { throw new CPFinvalido(); }

    }

    public boolean validatepassworld(String typePassworld, String expectedPassworld) {
        return (typePassworld.equals(expectedPassworld));
    }

    @Override
    protected boolean FildExists(String Fild)
    {
        return Fild != null && !Fild.isBlank();
    }

    private boolean validateEmail(String email){
        return  (FildExists(email) &&
              email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")
        );
    }
}