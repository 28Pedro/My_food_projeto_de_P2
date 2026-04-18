package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.core.UserIntegrator;
import br.ufal.ic.myfood.models.database.EnterpriseDataManeger;

public class EnterpriseValidator extends Validator<EnterpriseDataManeger>{

    private final UserIntegrator userIntegrator;

    public EnterpriseValidator(EnterpriseDataManeger database, UserIntegrator userIntegrator){
        super(database);
        this.userIntegrator = userIntegrator;
    }

    public void validateCreateEnterprise(String ownerId, String name, String adress) throws UsuarioNaoPodeCriarEmpresa,
            NomeDeEmpresaJaExiste,EmpresaComMesmoNomeeLocal, NomeInvalido {

        validateUser(ownerId);

        if(!isNameValid(name, ownerId)){
            throw new NomeDeEmpresaJaExiste();
        }
        if(dataBase.idbyNameAdressExists(name, adress)){
            throw new EmpresaComMesmoNomeeLocal();
        }
    }

    public void validateRequestGetIdEnterprise(String ownerId, String name, int index)
    throws NomeInvalido, IndiceInvalido, UsuarioNaoPodeCriarEmpresa{

        if(!fildExists(name)){
            throw new NomeInvalido();
        }

        if(index < 0){
            throw new IndiceInvalido();
        }

        validateUser(ownerId);

    }

    public void validateUser(String ownerId) throws UsuarioNaoPodeCriarEmpresa{

        try {
            if (!userIntegrator.userIsOwner(ownerId)) {
                throw new UsuarioNaoPodeCriarEmpresa();
            }
        } catch (Exception e) {
            throw new UsuarioNaoPodeCriarEmpresa();
        }
    }

    public void  validateAtribute(String atribute) throws AtributoInvalido{
        if(!fildExists(atribute)){
            throw new AtributoInvalido();
        }
    }

    private boolean isNameValid(String name, String ownerId) throws NomeInvalido {

        if(dataBase.nameExists(name)) {
            return (dataBase.getIdbyName(name).equals(ownerId));
        }

        return true;
    }

}
