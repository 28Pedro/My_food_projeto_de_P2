package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.PedidoNaoEncontrado;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.models.delivery.Delivery;

import java.util.Map;

public class DeliveryDataManeger extends DataManger<Delivery> {

    private final String DELIVERY_BY_ID_FILE = getFILE_PATH() + "delivery_by_id.xml";
    private final String DELIVERY_ID_BY_DELIVERYMAN_FILE = getFILE_PATH() + "delivery_id_by_delivery_man.xml";

    private Map<String,Delivery> deliveryById;
    private Map<String,String> deliveryIdBydeliveryMan;

    public DeliveryDataManeger() throws FileError {
        deliveryById = loadMapFromXML(DELIVERY_BY_ID_FILE);
        deliveryIdBydeliveryMan = loadMapFromXML(DELIVERY_ID_BY_DELIVERYMAN_FILE);

    }

    @Override
    public void saveObject(Delivery delivery){

        String id = delivery.getId();

        deliveryById.put(id,delivery);

    }

    public void atributeDeliveryToDeliveryMan(String delivery_Id, String deliveryManId){
        deliveryIdBydeliveryMan.put(deliveryManId,delivery_Id);
    }

    public void removeDeliveryToDeliveryMan(String deliveryManId)
    throws PedidoNaoEncontrado{
        if(!deliveryIdBydeliveryMan.containsKey(deliveryManId)){
            throw new PedidoNaoEncontrado();
        }

        deliveryIdBydeliveryMan.remove(deliveryManId);
    }

    public boolean deliveryManIsbusy(String deliveryManId){
        return deliveryIdBydeliveryMan.containsKey(deliveryManId);
    }

    @Override
    public void saveData() throws SaveError {
        saveMapToXML(deliveryById, DELIVERY_BY_ID_FILE);
        saveMapToXML(deliveryIdBydeliveryMan, DELIVERY_ID_BY_DELIVERYMAN_FILE);

    }

    @Override
    public void resetData() {
        deliveryById.clear();
        deliveryIdBydeliveryMan.clear();
        resetFiles(DELIVERY_BY_ID_FILE,DELIVERY_ID_BY_DELIVERYMAN_FILE);
    }
}
