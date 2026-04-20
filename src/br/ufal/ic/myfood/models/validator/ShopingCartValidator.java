package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.integrators.ProductIntegrator;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;
import br.ufal.ic.myfood.models.database.ShopingCartDataManeger;

import java.util.List;

public class ShopingCartValidator extends Validator<ShopingCartDataManeger> {

    private final UserIntegrator userIntegrator;
    private final ProductIntegrator productIntegrator;

    public ShopingCartValidator(ShopingCartDataManeger dataManager,
           UserIntegrator userIntegrator, ProductIntegrator productIntegrator) {
        super(dataManager);
        this.userIntegrator = userIntegrator;
        this.productIntegrator = productIntegrator;
    }

    public void validateOrder(String clientId, String enterpriseId)
    throws DoisPedidosMesmaEmpresa, DonoNaoPodeFazerPedido {

        try {
            if(userIntegrator.userIsOwner(clientId)){
                throw new DonoNaoPodeFazerPedido();
            }
        } catch (UsuarioNaoExisteException e) {
            throw new DonoNaoPodeFazerPedido();
        }

        if(dataBase.OrderByNameEnterpriseExists(clientId, enterpriseId)){
            throw new DoisPedidosMesmaEmpresa();
        }
    }

    public void validateAddProduct(String orderId, String productId)
    throws NaoExistePedidoEmAberto, AdicionarEmPedidoFechado, ProdutoNaoPertenceAEmpresa {

        if(!dataBase.orderExists(orderId)) {
            throw new NaoExistePedidoEmAberto();
        }

        try {
            if(!dataBase.orderisOpen(orderId)) {
                throw new AdicionarEmPedidoFechado();
            }
        }catch (PedidoNaoEncontrado e){
            throw new AdicionarEmPedidoFechado();
        }

        try {
            String productEnterprise = productIntegrator.getProductEnterpriseId(productId);
            if (!dataBase.getOrderEnterprise(orderId).equals(productEnterprise)) {
                throw new ProdutoNaoPertenceAEmpresa();
            }
        }catch (Exception e){
            throw new ProdutoNaoPertenceAEmpresa();
        }
    }

    public void validadateGetAtribute(String orderId, String atribute)
        throws AtributoInvalido,PedidoNaoEncontrado,AtributoNaoExiste{

        if(!fildExists(atribute)) {
            throw new AtributoInvalido();
        }

        if(!isValidAttribute(atribute)) {
            throw new AtributoNaoExiste();
        }

        if(!dataBase.orderExists(orderId)){
            throw new PedidoNaoEncontrado();
        }

    }

    public void validateRemoveProduct(String orderId,String prouctName)
            throws ProdutoInvalido,RemoverEmPedidoFechado{
        if(!fildExists(prouctName)){
            throw new ProdutoInvalido();
        }

        try {
            if(!dataBase.orderisOpen(orderId)){
                throw new RemoverEmPedidoFechado();
            }
        } catch (PedidoNaoEncontrado e) {
            throw new RemoverEmPedidoFechado();
        }

    }

    public void getOrderNumberValidator(List<String> allOrders, int index)
        throws IndiceMaiorQueEsperado{
        if(allOrders == null || index >= allOrders.size()) {
            throw new IndiceMaiorQueEsperado();
        }
    }

    private boolean isValidAttribute(String atribute) {
        String lower = atribute.toLowerCase();
        return lower.equals("cliente") || lower.equals("empresa") ||
                lower.equals("estado") || lower.equals("produtos") || lower.equals("valor");
    }

}
