package br.ufal.ic.myfood.core;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.integrators.EnterpriseIntegrator;
import br.ufal.ic.myfood.models.integrators.OrderIntegrator;
import br.ufal.ic.myfood.models.integrators.ProductIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;
import br.ufal.ic.myfood.models.manageres.*;

import java.util.List;

public class Core {

    private UserManager userManager;
    private EnterpriseManager enterpriseManager;
    private UserIntegrator userIntegrator;
    private EnterpriseIntegrator enterpriseIntegrator;
    private ProductManager productManager;
    private ProductIntegrator productIntegrator;
    private ShopingCartManeger shopingCartManeger;
    private OrderIntegrator orderIntegrator;
    private DeliveryManeger deliveryManeger;


    public Core() throws FileError{
        this.userManager = new UserManager();
        this.userIntegrator = new UserIntegrator(userManager);
        this.enterpriseManager = new EnterpriseManager(userIntegrator);
        this.enterpriseIntegrator = new EnterpriseIntegrator(enterpriseManager);
        this.productManager = new ProductManager(enterpriseIntegrator);
        this.productIntegrator = new ProductIntegrator(productManager);
        this.shopingCartManeger = new ShopingCartManeger(userIntegrator, productIntegrator, enterpriseIntegrator);
        this.orderIntegrator = new OrderIntegrator(shopingCartManeger);
        this.deliveryManeger = new DeliveryManeger(userIntegrator,orderIntegrator,enterpriseIntegrator);

        this.userManager.setEnterpriseIntegrator(enterpriseIntegrator);
    }

    public void zerarSistema(){
       userManager.resetData();
       enterpriseManager.resetData();
       productManager.resetData();
       shopingCartManeger.resetData();
       deliveryManeger.resetData();
    }

    public void encerrarSistema() throws SaveError {
        userManager.saveData();
        enterpriseManager.saveData();
        productManager.saveData();
        shopingCartManeger.saveData();
        deliveryManeger.saveData();
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

    public void criarUsuario(String nome, String email, String senha, String endereco,
                             String vehicle, String licensePlate)
            throws UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida,VeiculoInvalido,PlacaInvalido{

        userManager.createUser(nome,email,senha,endereco,vehicle,licensePlate);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws CPFinvalido,UsuarioJaExisteException, NomeInvalido, EmailInvalido, EnderecoInvalido,
            SenhaInvalida {

        userManager.createUser(nome,email,senha,endereco,cpf);
    }

    public String login(String email, String senha) throws LoginError {
        return userManager.login(email, senha);
    }

    public void addDeliveryMan(String enterpriseId, String userId)
    throws EmpresanaoCadastrada,UsuarioNaoEEntregador,UsuarioNaoExisteException{

        userManager.addDeliveryManEnterprise(enterpriseId,userId);
        enterpriseManager.addDeliveryMan(enterpriseId,userId);

        List<String> prontOrders = shopingCartManeger.getProntOrdersByEnterprise(enterpriseId);
        String deliveryManEmail = userIntegrator.getUserEmailbyId(userId);
        boolean isPharmacy = enterpriseManager.enterpriseIsPharmacy(enterpriseId);

        if(prontOrders != null){
            userManager.addProntOrdersToDeliveryMan(deliveryManEmail, prontOrders, isPharmacy);
        } //!
    }

    public String getEnterprisesByDeliveryMan(String userId)
    throws UsuarioNaoEEntregador,UsuarioNaoExisteException{

        return userManager.getEnterpisesByDeliveryMan(userId);
    }

    public String createEnterprise(String enterpriseType, String ownerId, String name,
                                 String adress, String kitchenType) throws UsuarioNaoPodeCriarEmpresa,
                                NomeDeEmpresaJaExiste, EmpresaComMesmoNomeeLocal, NomeInvalido,
                                EnderecoEmpresaInvalido, TipoEmpresaInvalido{

        return enterpriseManager.createEnterprise(enterpriseType,ownerId,name,adress,kitchenType);
    }

    public String createEnterprise(String enterpriseType, String ownerId, String name,
                                   String adress, String open, String closes, String supermarketType)
            throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste, EmpresaComMesmoNomeeLocal,
                    NomeInvalido,FormatoDeHoraInvalido,HorarioInvalido,EnderecoEmpresaInvalido,
                    TipoEmpresaInvalido,TipoMercadoInvalido{

        return enterpriseManager.createEnterprise(enterpriseType,ownerId,name,adress,open
        ,closes,supermarketType);
    }

    public String createEnterprise(String enterpriseType, String ownerId, String name,
                                   String adress, boolean open24Hours, int numberOfEmploys) throws UsuarioNaoPodeCriarEmpresa,
            NomeDeEmpresaJaExiste, EmpresaComMesmoNomeeLocal, NomeInvalido,
            EnderecoEmpresaInvalido, TipoEmpresaInvalido{

        return enterpriseManager.createEnterprise(enterpriseType,ownerId,name,adress,open24Hours,numberOfEmploys);
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

    public String getDelivryManListByEnterprise(String enterpriseId) throws
            EmpresanaoCadastrada{

        return enterpriseManager.getDeviveryMensList(enterpriseId);
    }

    public void supermarketChangeOperation(String id, String open, String closes)
            throws FormatoDeHoraInvalido, HorarioInvalido, MercadoInvalido{
        enterpriseManager.supermarketChangeOperation(id,open,closes);
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

    public void releaseOrder(String orderId)
            throws PedidoNaoEncontrado,EmpresanaoCadastrada,LiberarPedidoAberto,
            UsuarioNaoEEntregador, UsuarioNaoExisteException,PedidoJaLiberado{
        shopingCartManeger.releaseOrder(orderId);
    }

    public String getOrderByDeliveryMan(String deliveryManId)
            throws UsuarioNaoEEntregador,UsuarioNaoExisteException, EntregadorNaoTemEmpresa,
            NaoExistePedidoParaEntrega{
         return userManager.getOrderByDeliveryMan(deliveryManId);
    }

    public String createDelivery(String orderId, String deliveryManId, String destination)
    throws PedidoNaoEstaPronto,PedidoNaoEncontrado,NaoEUmEntregadorValido,EntregadorAindaEmEntrega,
            NaoExistePedidoParaEntrega{
        return deliveryManeger.createDelivery(orderId,deliveryManId,destination);
    }

    public String getDeliveryAtributeById(String deliveryId, String atribute)
            throws AtributoNaoExiste,AtributoInvalido, NaoExisteEntregaId {
        return deliveryManeger.getDeliveryAtributeById(deliveryId,atribute);
    }

    public String getDeliveryIdbyOrderId(String orderId)throws NaoExisteEntregaId{
        return deliveryManeger.getDeliveryIdbyOrderId(orderId);
    }

    public void finishDelivery(String deliveryId) throws NadaParaSerEntregue{
        deliveryManeger.finishDelivery(deliveryId);
    }


}
