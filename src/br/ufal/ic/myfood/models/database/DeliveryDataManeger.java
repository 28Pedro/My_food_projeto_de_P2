package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.models.delivery.Delivery;

import java.util.Map;

public class DeliveryDataManeger extends DataManger<Delivery> {

    private final String DELIVERY_BY_ID_FILE = getFILE_PATH() + "delivery_by_id.xml";

    private Map<String,Delivery> deliveryById;

    public DeliveryDataManeger() throws FileError {
        deliveryById = loadMapFromXML(DELIVERY_BY_ID_FILE);

    }

    @Override
    public void saveObject(Delivery delivery){

        String id = delivery.getId();

        deliveryById.put(id,delivery);

    }

    @Override
    public void saveData() throws SaveError {
        saveMapToXML(deliveryById, DELIVERY_BY_ID_FILE);
    }

    @Override
    public void resetData() {

        deliveryById.clear();
        resetFiles(DELIVERY_BY_ID_FILE);
    }
}
