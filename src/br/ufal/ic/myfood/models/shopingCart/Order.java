package br.ufal.ic.myfood.models.shopingCart;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.AtributoNaoExiste;
import br.ufal.ic.myfood.exceptions.ProdutoNaoEncontrado;
import br.ufal.ic.myfood.records.PairKey;

import java.util.List;
import java.util.Locale;

public class Order {

    String id;
    String clientId;
    String enterpriseId;
    String state;
    List<PairKey<String,Float>> products; //fist(name),Second(Price)
    float subtotal;

    public Order() {
    }

    public Order(String id, String clientId, String enterprise, String state, List<PairKey<String,Float>> products) {
        this.id = id;
        this.clientId = clientId;
        this.enterpriseId = enterprise;
        this.state = state;
        this.products = products;
        this.subtotal = 0;
    }

    public String getAtribute(String atribute) throws AtributoNaoExiste {;
        return switch (atribute) {
            case "cliente"     -> getClientId();
            case "empresa"    -> getEnterpriseId();
            case "estado"   -> getState();
            case "produtos" -> this.toString();
            case "valor"    -> String.format(Locale.US, "%.2f",this.getSubtotal());
            default -> throw new AtributoNaoExiste();
        };
    }

    public void addProduct(PairKey<String,Float> product){
        this.products.add(product);
        subtotal += product.getSecond();
    }

    public void removeProduct(PairKey <String,Float> product) throws ProdutoNaoEncontrado { // a verificaçao também deverá ocorrer na classe que controla

        boolean status = false;

        for(PairKey<String,Float> P : products){

            if( P.getFirst().equals(product) ){
                this.products.remove(product);
                subtotal -= product.getSecond();
                status = true;
                break;
            }
        }

        if(!status){
            throw new ProdutoNaoEncontrado();
        }

    }

    public void removeProductByName(String productName) throws ProdutoNaoEncontrado {
        boolean found = false;

        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getFirst().equals(productName)) {
                float price = products.get(i).getSecond();
                products.remove(i);
                subtotal -= price;
                found = true;
                break;
            }
        }

        if(!found) {
            throw new ProdutoNaoEncontrado();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterprise) {
        this.enterpriseId = enterprise;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PairKey<String,Float>> getProducts() {
        return products;
    }

    public void setProducts(List<PairKey<String,Float>> products) {
        this.products = products;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        for (int i = 0; i < this.products.size(); i++) {

            sb.append(this.products.get(i).getFirst());

            if (i < this.products.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");

        return sb.toString();
    }
}
