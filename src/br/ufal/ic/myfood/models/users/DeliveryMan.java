package br.ufal.ic.myfood.models.users;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.NaoExistePedidoParaEntrega;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;

public class DeliveryMan extends User{

    private String vehicle;
    private String licensePlate;
    private List<PairKey<String,String>> enterprises_Name_Address;
    private List<String> deliveryByIdList;
    private List<String> deliveryByIdPriorityList;

    public DeliveryMan() {
        enterprises_Name_Address = new ArrayList<PairKey<String, String>>();
        deliveryByIdList = new ArrayList<String>();
        deliveryByIdPriorityList = new ArrayList<String>();
    }

    public DeliveryMan(String id, String name, String email, String password, String adress, String vehicle, String licensePlate) {
        super(id, name, email, password, adress);
        this.vehicle = vehicle;
        this.licensePlate = licensePlate;
        enterprises_Name_Address = new ArrayList<PairKey<String, String>>();
        deliveryByIdList = new ArrayList<String>();
        deliveryByIdPriorityList = new ArrayList<String>();
    }

    @Override
    public String getAtribute(String atribute) throws AtributoInvalido {

      return switch (atribute){
          case "veiculo" -> getVehicle();
          case "placa" ->getLicensePlate();
          default -> super.getAtribute(atribute);
       };

    }

    public void addOrder(String orderId, boolean priority){
        if(priority){
            deliveryByIdPriorityList.add(orderId);
        }else{
            deliveryByIdList.add(orderId);
        }
    }

    public void removeOrder(String orderId, boolean priority){

        if(priority){
            deliveryByIdPriorityList.remove(orderId);
        }else{
            deliveryByIdList.remove(orderId);
        }

    }

    public String getFirstOrder() throws NaoExistePedidoParaEntrega{

        if(!deliveryByIdPriorityList.isEmpty()){
           return deliveryByIdPriorityList.getFirst();
        } else if (!deliveryByIdList.isEmpty()) {
           return deliveryByIdList.getFirst();
        } else {
           throw new NaoExistePedidoParaEntrega();
        }
    }

    public void addEnterpise(PairKey<String,String> nameAddress){
        enterprises_Name_Address.add(nameAddress);
    }

    public String getEnterpizesList(){

        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        for (int i = 0; i < enterprises_Name_Address.size(); i++) {

            String id_1 = enterprises_Name_Address.get(i).getFirst();
            String id_2 = enterprises_Name_Address.get(i).getSecond();

            sb.append("[")
                    .append(id_1)
                    .append(",")
                    .append(" ")
                    .append(id_2)
                    .append("]");

            if (i < enterprises_Name_Address.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");

        return sb.toString();
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<PairKey<String, String>> getEnterprises_Name_Address() {
        return enterprises_Name_Address;
    }

    public List<String> getDeliveryByIdList() {
        return deliveryByIdList;
    }

    public void setDeliveryByIdList(List<String> deliveryByIdList) {
        this.deliveryByIdList = deliveryByIdList;
    }

    public List<String> getDeliveryByIdPriorityList() {
        return deliveryByIdPriorityList;
    }

    public void setDeliveryByIdPriorityList(List<String> deliveryByIdPriorityList) {
        this.deliveryByIdPriorityList = deliveryByIdPriorityList;
    }

    public void setEnterprises_Name_Address(List<PairKey<String, String>> enterprises_Name_Address) {
        this.enterprises_Name_Address = enterprises_Name_Address;
    }
}
