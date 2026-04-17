package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.*;

public class CoreManeger {

    UserManager userManager;
    EnterpriseManager enterpriseManager;
    UserIntegrator userIntegrator;

    public CoreManeger() throws FileError{
        this.userManager = new UserManager();
        this.userIntegrator = new UserIntegrator(userManager);
        this.enterpriseManager = new EnterpriseManager(userIntegrator);
    }

    public void zerarSistema(){
       userManager.resetData();
       enterpriseManager.resetData();
    }

    public void encerrarSistema() throws SaveError {
        userManager.saveData();
        enterpriseManager.saveData();
    }

    public String getAtributoUsuario(String id, String atributo) throws UsuarioNaoExisteException {
        return userManager.getAtributebyId(id,atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida{

        userManager.createUser(nome,email,senha,endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws CPFinvalido,UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        userManager.createUser(nome,email,senha,endereco,cpf);
    }

    public String login(String email, String senha) throws LoginError {
        return userManager.login(email, senha);
    }

    public String createEnterprise(String enterpriseType, String ownerId, String name,
                                 String adress, String kitchenType) throws UsuarioNaoPodeCriarEmpresa,
                                NomeDeEmpresaJaExiste, EmpresaComMesmoNomeeLocal,
                                NomeInvalido, UsuarioNaoExisteException {
        return enterpriseManager.createEnterprise(enterpriseType,ownerId,name,adress,kitchenType);
    }

    public String getEnterprizesOfUser(String ownerId) throws EmpresanaoCadastrada, UsuarioNaoPodeCriarEmpresa, UsuarioNaoExisteException {
       return enterpriseManager.getEntrepriseListByOwner(ownerId);
    }

    public String getAtributoEmpresa(String empresaId, String atributo) throws EmpresanaoCadastrada, AtributoInvalido, UsuarioNaoExisteException {
        return enterpriseManager.getAtributoEmpresa(empresaId, atributo);
    }

    public String getIdEmpresa(String ownerId, String nome, int indice) throws EmpresanaoCadastrada, NomeInvalido, IndiceMaiorQueEsperado, UsuarioNaoExisteException, UsuarioNaoPodeCriarEmpresa, IndiceInvalido, NaoExisteEmpresaComEsseNome {
        return enterpriseManager.getIdEmpresa(ownerId, nome, indice);
    }

}
