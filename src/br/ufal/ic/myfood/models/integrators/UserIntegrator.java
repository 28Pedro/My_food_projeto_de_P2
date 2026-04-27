package br.ufal.ic.myfood.models.integrators;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.manageres.UserManager;

public class UserIntegrator {

    final private UserManager userManager;

    public UserIntegrator(UserManager userManager){
        this.userManager = userManager;
    }

    public boolean userIsOwner(String id) throws UsuarioNaoExisteException {
       return userManager.userIsOwner(id);
    }

    public String getUserNameById(String id)
            throws UsuarioNaoExisteException{
        try {
            return userManager.getAtributebyId(id, "nome");
        } catch (AtributoInvalido e) {
           throw new UsuarioNaoExisteException();
        }

    }

    public String getUserEmailbyId(String id) throws UsuarioNaoExisteException
     {

        try {
            return userManager.getAtributebyId(id,"email");

        } catch (AtributoInvalido e) {

            throw new UsuarioNaoExisteException();
        }
    }


}
