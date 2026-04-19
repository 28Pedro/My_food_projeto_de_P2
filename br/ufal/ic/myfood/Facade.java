package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.core.CoreManeger;


public class Facade {

    CoreManeger coreManeger;

    public Facade() throws FileError{
        coreManeger = new CoreManeger();
    }

    public void zerarSistema(){
        coreManeger.zerarSistema();
    }

    public void encerrarSistema() throws SaveError{

        coreManeger.encerrarSistema();

    }

    public String getAtributoUsuario(String id, String atributo)
            throws UsuarioNaoExisteException,AtributoInvalido {

        return coreManeger.getAtributoUsuario(id,atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida{

        coreManeger.criarUsuario(nome,email,senha,endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws CPFinvalido,UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        coreManeger.criarUsuario(nome,email,senha,endereco,cpf);
    }

    public String login(String email, String senha) throws LoginError {
       return coreManeger.login(email, senha);
    }

    public String criarEmpresa (String tipoEmpresa, String dono, String nome, String endereco, String tipoCozinha)
                            throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,
                            EmpresaComMesmoNomeeLocal, NomeInvalido, UsuarioNaoExisteException{
        return coreManeger.createEnterprise(tipoEmpresa,dono,nome,endereco,tipoCozinha);
    }

    public String getEmpresasDoUsuario(String IdDono) throws EmpresanaoCadastrada, UsuarioNaoPodeCriarEmpresa, UsuarioNaoExisteException {
        return coreManeger.getEnterprizesOfUser(IdDono);
    }

    public String getAtributoEmpresa(String empresaId, String atributo) throws EmpresanaoCadastrada, AtributoInvalido, UsuarioNaoExisteException {
        return coreManeger.getAtributoEmpresa(empresaId, atributo);
    }

    public String getIdEmpresa(String ownerId, String nome, int indice) throws EmpresanaoCadastrada, NomeInvalido, IndiceMaiorQueEsperado, UsuarioNaoExisteException, UsuarioNaoPodeCriarEmpresa, IndiceInvalido, NaoExisteEmpresaComEsseNome {
        return coreManeger.getIdEmpresa(ownerId, nome, indice);
    }

    public String criarProduto(String empresa, String nome, float valor, String categoria)
            throws NomeInvalido, ValorInvalido, CategoriaInvalido, JaExisteUmProdutoComEsseNomeParaEssaEmpresa {
        return coreManeger.createProduct(empresa, nome, valor, categoria);
    }

    public void editarProduto(String produto, String nome, float valor, String categoria)
            throws ProdutoNaoCadastrado, NomeInvalido, ValorInvalido, CategoriaInvalido {
        coreManeger.editProduct(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, String empresa, String atributo)
            throws ProdutoNaoEncontrado, AtributoNaoExiste {
        return coreManeger.getProductAtribute(nome, empresa, atributo);
    }

    public String listarProdutos(String empresa) throws EmpresaNaoEncontrada {
        return coreManeger.getProductListByEnterprise(empresa);
    }

}
