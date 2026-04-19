package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.ProdutoNaoEncontrado;
import br.ufal.ic.myfood.records.PairKey;

public class ProductIntegrator {

    private final ProductManager productManager;

    public ProductIntegrator(ProductManager productManager){
        this.productManager = productManager;
    }

    public PairKey<String,Float> getProductInfo(String name, String enterpriseId)
    throws ProdutoNaoEncontrado {
        return productManager.getProductNameAndValueById(name, enterpriseId);
    }

    public String getProductEnterpriseId(String productId) throws Exception {
        return productManager.getProductEnterpriseId(productId);
    }

    public String getProductNameById(String productId) throws Exception {
        return productManager.getProductNameById(productId);
    }

    public float getProductPriceById(String productId) throws Exception {
        return productManager.getProductPriceById(productId);
    }


}
