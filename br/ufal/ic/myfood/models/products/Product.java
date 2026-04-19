package br.ufal.ic.myfood.models.products;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.AtributoNaoExiste;

public abstract class Product {

    private String id;
    private String enterpriseId;
    private String name;
    private float value;

    public Product() {
    }

    public Product(String id, String name, float value, String enterpriseId){
        this.id = id;
        this.name = name;
        this.value = value;
        this.enterpriseId = enterpriseId;
    }

    public void editProduct(String name, float value){
        this.setName(name);
        this.setValue(value);
    }

    public String getAtribute(String atribute)
            throws AtributoNaoExiste {

        return switch (atribute.toLowerCase()) {
            case "valor"     -> String.format(java.util.Locale.US, "%.2f",this.getValue());
            case "empresa"    -> getEnterpriseId();
            default -> throw new AtributoNaoExiste();
        };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString(){
        return getName();
    }
}
