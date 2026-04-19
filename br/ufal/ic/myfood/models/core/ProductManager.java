package br.ufal.ic.myfood.models.core;


import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.ProductDataManeger;
import br.ufal.ic.myfood.models.products.Product;
import br.ufal.ic.myfood.models.products.RestaurantProduct;
import br.ufal.ic.myfood.models.validator.ProductValidator;
import br.ufal.ic.myfood.records.PairKey;

import java.util.List;
import java.util.UUID;

public class ProductManager {

    private ProductDataManeger productDataManeger;
    private ProductValidator productValidator;
    private EnterpriseIntegrator enterpriseIntegrator;

    public ProductManager(EnterpriseIntegrator enterpriseIntegrator) throws FileError {
        this.productDataManeger = new ProductDataManeger();
        this.productValidator = new ProductValidator(productDataManeger);
        this.enterpriseIntegrator = enterpriseIntegrator;
    }

     public String createProduct(String name, float value, String category, String entrepiseId)
             throws NomeInvalido, ValorInvalido, CategoriaInvalido,
             JaExisteUmProdutoComEsseNomeParaEssaEmpresa {

         productValidator.validateCreateProduct(name, value, category, entrepiseId);

          String productId = generateId();
          RestaurantProduct restaurantProduct = new RestaurantProduct(productId, name, value, category, entrepiseId);
          productDataManeger.saveObject(restaurantProduct);

          return productId;

      }

     public void editProduct(String id, String name, float value, String category) throws ProdutoNaoCadastrado,
              NomeInvalido, ValorInvalido, CategoriaInvalido {

          productValidator.validateGeneralProductOperation(name, value, category);
          productDataManeger.modifyProduct(id, name, value, category);
      }

    public String getProductAtribute(String name, String enterpiseId, String atribute)
        throws ProdutoNaoEncontrado, AtributoNaoExiste{

        String productId = productDataManeger.getProductIDbyNameEnterpise(name, enterpiseId);
        Product product = productDataManeger.getProductById(productId);

        if (atribute.equalsIgnoreCase("empresa")) {
            try {
                String enterpriseName = enterpriseIntegrator.getEnterpiseNameById(product.getAtribute(atribute));
                return enterpriseName;
            } catch (EmpresanaoCadastrada e) {
                throw new ProdutoNaoEncontrado();
            }
        }

        return product.getAtribute(atribute);
    }

    public String getProductListByEnterprise(String entepriseId) throws EmpresaNaoEncontrada {

        try {
            enterpriseIntegrator.getEnterpiseNameById(entepriseId);
        } catch (EmpresanaoCadastrada e) {
            throw new EmpresaNaoEncontrada();
        }

        List<String> entrepriseList = productDataManeger.getProductListByEnterprise(entepriseId);
        if(entrepriseList.isEmpty()){
            return "{[]}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        for (int i = 0; i < entrepriseList.size(); i++) {

            sb.append(entrepriseList.get(i));

            if (i < entrepriseList.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");

        return sb.toString();
    }

    public PairKey<String,Float> getProductNameAndValueById(String name, String enterpiseId)
            throws ProdutoNaoEncontrado {

        Product product = productDataManeger.getProductById(
                productDataManeger.getProductIDbyNameEnterpise(name, enterpiseId));

        return new PairKey<String,Float>(product.getName(), product.getValue());
    }

    public void saveData() throws SaveError {
        productDataManeger.saveData();
    }

    public void resetData(){
        productDataManeger.resetData();
    }

    public String getProductEnterpriseId(String productId) throws Exception {
        Product product = productDataManeger.getProductById(productId);
        return product.getAtribute("empresa");
    }

    public String getProductNameById(String productId) throws Exception {
        Product product = productDataManeger.getProductById(productId);
        return product.getName();
    }

    public float getProductPriceById(String productId) throws Exception {
        Product product = productDataManeger.getProductById(productId);
        return product.getValue();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
