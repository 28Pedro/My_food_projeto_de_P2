package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.integrators.EnterpriseIntegrator;
import br.ufal.ic.myfood.models.integrators.ProductIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;

public class CoreManeger {

    private UserManager userManager;
    private EnterpriseManager enterpriseManager;
    private UserIntegrator userIntegrator;
    private EnterpriseIntegrator enterpriseIntegrator;
    private ProductManager productManager;
    private ProductIntegrator productIntegrator;
    private ShopingCartManeger shopingCartManeger;


    public CoreManeger() throws FileError{
        this.userManager = new UserManager();
        this.userIntegrator = new UserIntegrator(userManager);
        this.enterpriseManager = new EnterpriseManager(userIntegrator);
        this.enterpriseIntegrator = new EnterpriseIntegrator(enterpriseManager);
        this.productManager = new ProductManager(enterpriseIntegrator);
        this.productIntegrator = new ProductIntegrator(productManager);
        this.shopingCartManeger = new ShopingCartManeger(userIntegrator, productIntegrator, enterpriseIntegrator);
    }

    public void zerarSistema(){
       userManager.resetData();
       enterpriseManager.resetData();
       productManager.resetData();
       shopingCartManeger.resetData();
    }

    public void encerrarSistema() throws SaveError {
        userManager.saveData();
        enterpriseManager.saveData();
        productManager.saveData();
        shopingCartManeger.saveData();
    }

    public String getAtributoUsuario(String id, String atributo)
            throws UsuarioNaoExisteException,AtributoInvalido {
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
                                NomeDeEmpresaJaExiste, EmpresaComMesmoNomeeLocal, NomeInvalido {

        return enterpriseManager.createEnterprise(enterpriseType,ownerId,name,adress,kitchenType);
    }

    public String getEnterprizesOfUser(String ownerId) throws EmpresanaoCadastrada,
            UsuarioNaoPodeCriarEmpresa{

       return enterpriseManager.getEntrepriseListByOwner(ownerId);
    }

    public String getAtributoEmpresa(String enterpriseId, String atribute) throws EmpresanaoCadastrada,
            AtributoInvalido {

        return enterpriseManager.getAtributoEmpresa(enterpriseId, atribute);
    }

    public String getIdEmpresa(String ownerId, String name, int index) throws NomeInvalido,
            IndiceMaiorQueEsperado, UsuarioNaoPodeCriarEmpresa, IndiceInvalido,
            NaoExisteEmpresaComEsseNome {

        return enterpriseManager.getIdEmpresa(ownerId, name, index);
    }

    public String createProduct(String empresa, String nome, float valor, String categoria)
            throws NomeInvalido, ValorInvalido, CategoriaInvalido, JaExisteUmProdutoComEsseNomeParaEssaEmpresa {
        return productManager.createProduct(nome, valor, categoria, empresa);
    }

    public void editProduct(String produto, String nome, float valor, String categoria)
            throws ProdutoNaoCadastrado, NomeInvalido, ValorInvalido, CategoriaInvalido {
        productManager.editProduct(produto, nome, valor, categoria);
    }

    public String getProductAtribute(String nome, String empresa, String atributo)
            throws ProdutoNaoEncontrado, AtributoNaoExiste {
        return productManager.getProductAtribute(nome, empresa, atributo);
    }

    public String getProductListByEnterprise(String empresa) throws EmpresaNaoEncontrada {
        return productManager.getProductListByEnterprise(empresa);
    }

    public String createOrder(String clientId, String enterpise)
            throws DoisPedidosMesmaEmpresa, DonoNaoPodeFazerPedido {
        return shopingCartManeger.createOrder(clientId,enterpise);
    }

    public void addProductToOrder(String orderId, String productId) throws NaoExistePedidoEmAberto,
            AdicionarEmPedidoFechado, ProdutoNaoPertenceAEmpresa{
        shopingCartManeger.addProduct(orderId, productId);
    }

    public String getOrderAttribute(String orderId, String atributo) throws AtributoInvalido,
            PedidoNaoEncontrado, AtributoNaoExiste, UsuarioNaoExisteException,
            EmpresanaoCadastrada {
        return shopingCartManeger.getOrderAtribute(orderId, atributo);
    }

    public void closeOrder(String orderId) throws PedidoNaoEncontrado {
        shopingCartManeger.closeOrder(orderId);
    }

    public void removeProductFromOrder(String orderId, String productName) throws ProdutoInvalido,
            RemoverEmPedidoFechado, ProdutoNaoEncontrado,
            PedidoNaoEncontrado {
        shopingCartManeger.removeProduct(orderId, productName);
    }

    public String getOrderNumber(String clientId, String enterpriseId, int index) throws IndiceMaiorQueEsperado {
        return shopingCartManeger.getOrderNumber(clientId, enterpriseId, index);
    }

}
