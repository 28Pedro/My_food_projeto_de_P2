package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.exceptions.CategoriaInvalido;
import br.ufal.ic.myfood.exceptions.JaExisteUmProdutoComEsseNomeParaEssaEmpresa;
import br.ufal.ic.myfood.exceptions.NomeInvalido;
import br.ufal.ic.myfood.exceptions.ValorInvalido;
import br.ufal.ic.myfood.models.database.ProductDataManeger;

public class ProductValidator extends Validator<ProductDataManeger> {

    public ProductValidator(ProductDataManeger dataBase) {
        super(dataBase);
    }

    public void validateCreateProduct(String name, float value, String category, String entrepiseId)
            throws NomeInvalido, ValorInvalido, CategoriaInvalido,
            JaExisteUmProdutoComEsseNomeParaEssaEmpresa {

        validateGeneralProductOperation(name, value, category);

        if(dataBase.nameByEnterpiseExists(entrepiseId, name)){
            throw new JaExisteUmProdutoComEsseNomeParaEssaEmpresa();
        }

    }

    public void validateGeneralProductOperation(String name, float value, String category)
            throws NomeInvalido, ValorInvalido, CategoriaInvalido {

        validateName(name);
        validateValue(value);
        validateCategory(category);
    }



    public void validateName(String name) throws NomeInvalido {
        if (!fildExists(name)) {
            throw new NomeInvalido();
        }
    }

    public void validateValue(float value) throws ValorInvalido {
        if (value <= 0) {
            throw new ValorInvalido();
        }
    }

     public void validateCategory(String category) throws CategoriaInvalido{
        if (!fildExists(category)) {
            throw new CategoriaInvalido();
        }
    }
}
