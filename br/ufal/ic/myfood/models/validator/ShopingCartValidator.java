package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.exceptions.DoisPedidosMesmaEmpresa;
import br.ufal.ic.myfood.exceptions.DonoNaoPodeFazerPedido;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.core.UserIntegrator;
import br.ufal.ic.myfood.models.database.ShopingCartDataManeger;

public class ShopingCartValidator extends Validator<ShopingCartDataManeger> {

    private final UserIntegrator userIntegrator;

    public ShopingCartValidator(ShopingCartDataManeger dataManager,
           UserIntegrator userIntegrator) {
        super(dataManager);
        this.userIntegrator = userIntegrator;
    }

    //expectError "Dono de empresa nao pode fazer um pedido" criarPedido cliente=${id1} empresa=${e1}
    //expectError "Nao existe pedido em aberto" adicionarProduto numero=9999 produto=${p1}
    //expectError "Nao e permitido ter dois pedidos em aberto para a mesma empresa" criarPedido cliente=${id2} empresa=${e1}

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


}
