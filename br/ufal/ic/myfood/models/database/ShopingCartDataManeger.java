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

    private final String SHOPING_CART_BY_ID_FILE = getFILE_PATH() + "shoping_cart_by_Id.xml";
    private final String OPEN_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE = getFILE_PATH() + "open_shoping_cart_by_client_enterprise.xml";
    private final String CLOSE_SHOPING_ID_BY_CLIENT_ENTERPIRSE = getFILE_PATH() + "close_shoping_cart_by_client_enterprise.xml";
    private final String ALL_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE = getFILE_PATH() + "all_shoping_cart_by_client_enterprise.xml";

    private Map<String, Order> shopingCartById;
    private Map<PairKey<String,String>, String> openShopingCartIdByClientEnterprise;
    private Map<PairKey<String,String>, String> closeShopingCartIdByClientEnterprise;
    private Map<PairKey<String,String>, java.util.List<String>> allShopingCartIdByClientEnterprise;

    public ShopingCartDataManeger() throws FileError {

        shopingCartById = loadMapFromXML(SHOPING_CART_BY_ID_FILE);
        openShopingCartIdByClientEnterprise = loadMapFromXML(OPEN_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE);
        closeShopingCartIdByClientEnterprise = loadMapFromXML(CLOSE_SHOPING_ID_BY_CLIENT_ENTERPIRSE);
        allShopingCartIdByClientEnterprise = loadMapFromXML(ALL_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE);
    }

    public void saveObject(Order order){
        String id = order.getId();
        String enterpiseId = order.getEnterpriseId();
        String clientId = order.getClientId();

        fillShopingCartById(order, id);
        fillShopingCartIdByClientEnterprise(clientId, enterpiseId, id);
        fillAllShopingCartList(clientId, enterpiseId, id);

    }
    
    public Order getShopingCartById(String id)
        throws Exception {
        if(!shopingCartById.containsKey(id)){
            throw new Exception("Pedido nao encontrado");
        }
        return shopingCartById.get(id);
    }
    
    public void changeOrderState(String clientId, String enterpiseId){
       String id = openShopingCartIdByClientEnterprise.remove(makeKey(clientId, enterpiseId));
         closeShopingCartIdByClientEnterprise.put(makeKey(clientId, enterpiseId), id);
    }

    public boolean OrderByNameEnterpriseExists(String clientId, String enterpriseId){
        return openShopingCartIdByClientEnterprise.containsKey(makeKey(clientId, enterpriseId));
    }

    @Override
    public void saveData() throws SaveError {

        saveMapToXML(shopingCartById, SHOPING_CART_BY_ID_FILE);
        saveMapToXML(openShopingCartIdByClientEnterprise, OPEN_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE);
        saveMapToXML(closeShopingCartIdByClientEnterprise,CLOSE_SHOPING_ID_BY_CLIENT_ENTERPIRSE);
        saveMapToXML(allShopingCartIdByClientEnterprise, ALL_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE);
    }

    @Override
    public void resetData(){
        shopingCartById.clear();
        openShopingCartIdByClientEnterprise.clear();
        closeShopingCartIdByClientEnterprise.clear();
        allShopingCartIdByClientEnterprise.clear();

        resetFiles(SHOPING_CART_BY_ID_FILE, OPEN_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE,
                CLOSE_SHOPING_ID_BY_CLIENT_ENTERPIRSE, ALL_SHOPING_ID_BY_CLIENT_ENTERPIRSE_FILE);
    }

    private void fillShopingCartById(Order order, String Id){
        shopingCartById.put(Id, order);
    }

    private void fillShopingCartIdByClientEnterprise(String clientId,
                  String enterpriseId, String shopingCartId){
         openShopingCartIdByClientEnterprise.put(makeKey(clientId, enterpriseId), shopingCartId);
     }

    private void fillAllShopingCartList(String clientId, String enterpriseId, String orderId) {
        PairKey<String,String> key = makeKey(clientId, enterpriseId);
        if (!allShopingCartIdByClientEnterprise.containsKey(key)) {
            allShopingCartIdByClientEnterprise.put(key, new ArrayList<>());
        }
        allShopingCartIdByClientEnterprise.get(key).add(orderId);
    }

     public Order getOrderById(String id){
         return shopingCartById.get(id);
     }

    public List<String> getAllOrdersByClientEnterprise(String clientId, String enterpriseId){
        PairKey<String,String> key = makeKey(clientId, enterpriseId);
        List<String> orders = allShopingCartIdByClientEnterprise.getOrDefault(key, new java.util.ArrayList<>());
        return new java.util.ArrayList<>(orders);
    }


}




