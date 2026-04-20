package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.PedidoNaoEncontrado;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.models.shopingCart.Order;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopingCartDataManeger extends DataManger<Order> {

    private final String ORDER_BY_ID_FILE = getFILE_PATH() + "shoping_cart_by_Id.xml";
    private final String OPEN_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE = getFILE_PATH() + "open_shoping_cart_by_client_enterprise.xml";
    private final String ALL_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE = getFILE_PATH() + "all_shoping_cart_by_client_enterprise.xml";

    private Map<String, Order> orderById;
    private Map<PairKey<String,String>, String> openOrderIdByClientEnterprise;
    private Map<PairKey<String,String>, java.util.List<String>> allordersIdsByClientEnterprise;

    public ShopingCartDataManeger() throws FileError {

        orderById = loadMapFromXML(ORDER_BY_ID_FILE);
        openOrderIdByClientEnterprise = loadMapFromXML(OPEN_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE);
        allordersIdsByClientEnterprise = loadMapFromXML(ALL_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE);
    }

    public void saveObject(Order order){
        String id = order.getId();
        String enterpiseId = order.getEnterpriseId();
        String clientId = order.getClientId();

        fillShopingCartById(order, id);
        fillShopingCartIdByClientEnterprise(clientId, enterpiseId, id);
        fillAllShopingCartList(clientId, enterpiseId, id);

    }

    public boolean orderExists(String id){
        return orderById.containsKey(id);
    }

    public boolean orderisOpen(String id)
        throws PedidoNaoEncontrado{

        if(!orderExists(id)){
            throw new PedidoNaoEncontrado();
        }

        Order order = getOrderById(id);
        return "aberto".equals(order.isState());
    }

    public Order getOrderById(String id)
        throws PedidoNaoEncontrado {

        if(!orderExists(id)){
            throw new PedidoNaoEncontrado();
        }
        return orderById.get(id);
    }

    public String getOrderEnterprise(String id)
        throws PedidoNaoEncontrado{

        Order order = getOrderById(id);

        return order.getEnterpriseId();
    }
    
    public void changeOrderState(String clientId, String enterpiseId){
       openOrderIdByClientEnterprise.remove(makeKey(clientId, enterpiseId));

    }

    public boolean OrderByNameEnterpriseExists(String clientId, String enterpriseId){
        return openOrderIdByClientEnterprise.containsKey(makeKey(clientId, enterpriseId));
    }

    @Override
    public void saveData() throws SaveError {

        saveMapToXML(orderById, ORDER_BY_ID_FILE);
        saveMapToXML(openOrderIdByClientEnterprise, OPEN_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE);
        saveMapToXML(allordersIdsByClientEnterprise, ALL_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE);
    }

    @Override
    public void resetData(){
        orderById.clear();
        openOrderIdByClientEnterprise.clear();
        allordersIdsByClientEnterprise.clear();

        resetFiles(ORDER_BY_ID_FILE, OPEN_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE,
                ALL_ORDER_ID_BY_CLIENT_ENTERPIRSE_FILE);
    }

    private void fillShopingCartById(Order order, String Id){
        orderById.put(Id, order);
    }

    private void fillShopingCartIdByClientEnterprise(String clientId,
                  String enterpriseId, String shopingCartId){
         openOrderIdByClientEnterprise.put(makeKey(clientId, enterpriseId), shopingCartId);
     }

    private void fillAllShopingCartList(String clientId, String enterpriseId, String orderId) {
        PairKey<String,String> key = makeKey(clientId, enterpriseId);
        if (!allordersIdsByClientEnterprise.containsKey(key)) {
            allordersIdsByClientEnterprise.put(key, new ArrayList<>());
        }
        allordersIdsByClientEnterprise.get(key).add(orderId);
    }

    public List<String> getAllOrdersByClientEnterprise(String clientId, String enterpriseId){
        PairKey<String,String> key = makeKey(clientId, enterpriseId);
        List<String> orders = allordersIdsByClientEnterprise.getOrDefault(key, new java.util.ArrayList<>());
        return new java.util.ArrayList<>(orders);
    }


}




