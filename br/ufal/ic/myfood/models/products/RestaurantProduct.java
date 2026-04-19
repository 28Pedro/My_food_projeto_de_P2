package br.ufal.ic.myfood.models.products;

import br.ufal.ic.myfood.exceptions.AtributoNaoExiste;

public class RestaurantProduct extends Product{

    private String category;

    public RestaurantProduct() {

    }

    public RestaurantProduct(String id, String name, float value, String category, String enterpriseID) {
        super(id, name, value, enterpriseID);
        this.category = category;
    }

    public void editProduct(String name, float value, String category){
        super.editProduct(name,value);
        this.setCategory(category);
    }

    @Override
    public String getAtribute(String atribute)
            throws AtributoNaoExiste {

        if (atribute.equalsIgnoreCase("categoria")) {
            return getCategory();
        }
        return super.getAtribute(atribute);

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
