package br.ufal.ic.myfood.models.integrators;

import br.ufal.ic.myfood.models.manageres.ProductManager;
import br.ufal.ic.myfood.records.PairKey;

public class ProductIntegrator {

    private final ProductManager productManager;

    public ProductIntegrator(ProductManager productManager){
        this.productManager = productManager;
    }

    public PairKey<String,Float> getProductInfo(String prodouctId)
    throws Exception {
        return new PairKey<String,Float>
                (getProductNameById(prodouctId),getProductPriceById(prodouctId));
    }

    public String getProductEnterpriseId(String productId) throws Exception {
        return productManager.getProductEnterpriseId(productId);
    }

    private String getProductNameById(String productId) throws Exception {
        return productManager.getProductNameById(productId);
    }

    private float getProductPriceById(String productId) throws Exception {
        return productManager.getProductPriceById(productId);
    }


}
