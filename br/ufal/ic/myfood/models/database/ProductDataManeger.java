package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.products.Product;
import br.ufal.ic.myfood.models.products.RestaurantProduct;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDataManeger extends DataManger<Product> {

    private final String PRODUCT_BY_ID_FILE = getFILE_PATH() + "product_by_id.xml";
    private final String ENTERPRISE_POSISICION_BY_PRODUCT_ID_FILE = getFILE_PATH() + "enterprise_posicion_by_product_id.xml";
    private final String NAME_LIST_BY_ENTERPRISE = getFILE_PATH() + "name_list_by_enterprise.xml";
    private final String PRODUCT_BY_NAME_ENTERPRISE = getFILE_PATH() + "product_by_name_enterprise.xml";

    private Map<String, Product> productById;
    private Map<String, PairKey<String,Integer>> enterprisePosicionByProdutId;
    private Map<String, List<String>> nameListByEnterprise;
    private Map<PairKey<String,String>, String> productByNameEnterprise;

    public ProductDataManeger() throws FileError {
        productById = loadMapFromXML(PRODUCT_BY_ID_FILE);
        enterprisePosicionByProdutId = loadMapFromXML(ENTERPRISE_POSISICION_BY_PRODUCT_ID_FILE);
        nameListByEnterprise = loadMapFromXML(NAME_LIST_BY_ENTERPRISE);
        productByNameEnterprise = loadMapFromXML(PRODUCT_BY_NAME_ENTERPRISE);
    }

    public void modifyProduct(String id,String name, float value, String category)
    throws ProdutoNaoCadastrado{

        if(!productById.containsKey(id)){
            throw new ProdutoNaoCadastrado();
        }

         Product product = productById.get(id);
         String oldName = product.getName();
         String enterpriseId = product.getEnterpriseId();

         if(product instanceof RestaurantProduct) {
             ((RestaurantProduct) product).editProduct(name, value, category);
         }

         modifyProductNameLists(enterprisePosicionByProdutId.get(id), name);

         productByNameEnterprise.remove(makeKey(oldName, enterpriseId));
         productByNameEnterprise.put(makeKey(name, enterpriseId), id);

    }

    public List<String> getProductListByEnterprise(String enterpiseId){

        if(!nameListByEnterprise.containsKey(enterpiseId)){
            return new ArrayList<>();
        }

        return nameListByEnterprise.get(enterpiseId);
    }

    public Product getProductById(String id) throws ProdutoNaoEncontrado{
        if(!productById.containsKey(id)){
            throw new ProdutoNaoEncontrado();
        }

        return productById.get(id);
    }

    public String getProductIDbyNameEnterpise(String name, String enterpriseId)
        throws ProdutoNaoEncontrado{

        PairKey<String,String> key = makeKey(name,enterpriseId);

        if(!productByNameEnterprise.containsKey(key)){
            throw new ProdutoNaoEncontrado();
        }
        return productByNameEnterprise.get(key);
    }

    public boolean nameByEnterpiseExists(String enterpiseId, String name){
         if(!nameListByEnterprise.containsKey(enterpiseId)){
             return false;
         }
         List<String> list = nameListByEnterprise.get(enterpiseId);
         return list.contains(name);
    }

    @Override
    public void saveObject(Product product){

        String id = product.getId();
        String enterpriseId = product.getEnterpriseId();
        String name = product.getName();

        fillProductById(product,id);
        int posicion = fillNamelistByEnterprise(enterpriseId, name);
        fillEnterprisePosicionByProdutId(id, makeKey(enterpriseId,posicion));
        fillProductByNameEnterprise(makeKey(name,enterpriseId), id);

    }

    @Override
    public void saveData() throws SaveError {

        saveMapToXML(productById,PRODUCT_BY_ID_FILE);
        saveMapToXML(enterprisePosicionByProdutId, ENTERPRISE_POSISICION_BY_PRODUCT_ID_FILE);
        saveMapToXML(nameListByEnterprise, NAME_LIST_BY_ENTERPRISE);
        saveMapToXML(productByNameEnterprise,PRODUCT_BY_NAME_ENTERPRISE);
    }

    @Override
    public void resetData() {

        productById.clear();
        enterprisePosicionByProdutId.clear();
        nameListByEnterprise.clear();
        productByNameEnterprise.clear();

        resetFiles(PRODUCT_BY_ID_FILE, ENTERPRISE_POSISICION_BY_PRODUCT_ID_FILE,
                NAME_LIST_BY_ENTERPRISE,PRODUCT_BY_NAME_ENTERPRISE);
    }

    private void modifyProductNameLists(PairKey<String,Integer> enterpriseAndPosicion,
                                        String name ){
       List<String> list = nameListByEnterprise.get(enterpriseAndPosicion.getFirst());
       list.set(enterpriseAndPosicion.getSecond(),name);
    }

    private void fillProductById(Product product, String id){
        productById.put(id,product);
    }

    private void fillEnterprisePosicionByProdutId(String id,
                PairKey<String,Integer> enterpriseAndPosicion ){
       enterprisePosicionByProdutId.put(id,enterpriseAndPosicion);
    }

    private int fillNamelistByEnterprise(String enterpriseId, String name){
       if(!nameListByEnterprise.containsKey(enterpriseId)){
           nameListByEnterprise.put(enterpriseId, new ArrayList<>());
       }

       List<String> list = nameListByEnterprise.get(enterpriseId);
       int posicion = list.size();
       list.add(name);
       return posicion;
    }

    private void fillProductByNameEnterprise(PairKey<String,String> nameAndEnterpise, String id){
        productByNameEnterprise.put(nameAndEnterpise,id);
    }

    protected PairKey<String,Integer> makeKey(String enterpriseId, int posicion){
        return new PairKey<String,Integer>(enterpriseId, posicion);
    }

    protected PairKey<String,String> makeKey(String name, String enterpriseId){
        return new PairKey<String,String>(name, enterpriseId);
    }

}
