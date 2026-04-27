package br.ufal.ic.myfood.models.users;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.records.PairKey;

import java.util.ArrayList;
import java.util.List;

// decidi por enquanto que a melhor opção seria implementar a lista de empresas direto nos entregadors
// e a lista dos entrgadores por empresa direto nas empresas

public class DeliveryMan extends User{

    private String vehicle;
    private String licensePlate;
    private List<PairKey<String,String>> enterprises_Name_Address;

    public DeliveryMan() {

    }

    public DeliveryMan(String id, String name, String email, String password, String adress, String vehicle, String licensePlate) {
        super(id, name, email, password, adress);
        this.vehicle = vehicle;
        this.licensePlate = licensePlate;
        enterprises_Name_Address = new ArrayList<PairKey<String, String>>();
    }

    @Override
    public String getAtribute(String atribute) throws AtributoInvalido {

      return switch (atribute){
          case "veiculo" -> getVehicle();
          case "placa" ->getLicensePlate();
          default -> super.getAtribute(atribute);
       };

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

    public void setEnterprises_Name_Address(List<PairKey<String, String>> enterprises_Name_Address) {
        this.enterprises_Name_Address = enterprises_Name_Address;
    }
}
