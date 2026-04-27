package br.ufal.ic.myfood.models.enterprise;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;

import java.util.ArrayList;
import java.util.List;

public abstract class Enterprise {

    private String type;
    private String name;
    private String adress;
    private String ownerId;
    private String id;
    private List<String> deliveryManEmailList;


    public Enterprise() {
    }

    public Enterprise(String type,String ownerId, String name, String adress, String id) {

        this.type = type;
        this.name = name;
        this.adress = adress;
        this.ownerId = ownerId;
        this.id = id;
        this.deliveryManEmailList = new ArrayList<>();

    }

    public String getAtribute(String atribute) throws AtributoInvalido{
        return switch (atribute) {
            case "nome" -> this.getName();
            case "endereco" -> this.getAdress();
            case "dono" -> this.getOwnerId();
            case "tipoEmpresa" -> this.getType();

            default -> throw new AtributoInvalido();
        };
    }

    public void addDeliveryMan(String deliveryManEmail){
        deliveryManEmailList.add(deliveryManEmail);
    }

    public String getDeliveryManList(){

        StringBuilder sb = new StringBuilder();

        sb.append("{[");

        for (int i = 0; i < deliveryManEmailList.size(); i++) {

            String id = deliveryManEmailList.get(i);

            sb.append(id);

            if (i < deliveryManEmailList.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");

        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDeliveryManEmailList() {
        return deliveryManEmailList;
    }

    public void setDeliveryManEmailList(List<String> deliveryManEmailList) {
        this.deliveryManEmailList = deliveryManEmailList;
    }

    @Override
    public String toString() {
        return "[" + getName() + ", " + getAdress() + "]";
    }
}
