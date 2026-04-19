package br.ufal.ic.myfood.models.integrators;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.core.UserManager;

public class UserIntegrator {

    final private UserManager userManager;

    public UserIntegrator(UserManager userManager){
        this.userManager = userManager;
    }

    public boolean userIsOwner(String id) throws UsuarioNaoExisteException {
       return userManager.userIsOwner(id);
    }

    public String getUserNameById(String id)
            throws UsuarioNaoExisteException, AtributoInvalido {

        return userManager.getAtributebyId(id, "nome");
    }

}
