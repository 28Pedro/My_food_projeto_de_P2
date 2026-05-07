package br.ufal.ic.myfood.models.database;
import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.users.Client;
import br.ufal.ic.myfood.models.users.DeliveryMan;
import br.ufal.ic.myfood.models.users.Owner;
import br.ufal.ic.myfood.models.users.User;

import java.util.Map;

public class UserDataManage extends DataManger<User> {

    private final String ID_FILE = getFILE_PATH() + "users_by_id.xml";
    private final String EMAIL_FILE = getFILE_PATH() + "users_by_email.xml";
    private final String LICENCE_PLATE_FILE = getFILE_PATH() + "delivery_mans_by_licence_plate.xml";

    private Map<String, User> userbyIDList;
    private Map<String, String> idbyEmailList;
    private Map<String, String> idbyLicensePlate;

    public UserDataManage() throws FileError {
       userbyIDList = loadMapFromXML(ID_FILE);
       idbyEmailList = loadMapFromXML(EMAIL_FILE);
       idbyLicensePlate =loadMapFromXML(LICENCE_PLATE_FILE);
    }

    @Override
    public void saveObject(User user)
    {
        String id = user.getId();
        String email = user.getEmail();

        userbyIDList.put(id, user);
        idbyEmailList.put(email, id);

    }

    public void saveLicencePlate(DeliveryMan user){
        idbyLicensePlate.put(user.getLicensePlate(), user.getId());
    }

    public boolean LicencePlateExits(String licence_plate){
        return idbyLicensePlate.containsKey(licence_plate);
    }

    public String getAtributeById(String id, String atribute)
    throws UsuarioNaoExisteException,AtributoInvalido{

       if(!userbyIDList.containsKey(id)){
           throw new UsuarioNaoExisteException();
       }

       User user = userbyIDList.get(id);
       return user.getAtribute(atribute);
    }

    public String getIdByEmail(String email) throws UsuarioNaoExisteException{
        if(!idbyEmailList.containsKey(email)) {
            throw new UsuarioNaoExisteException();
        }

        return idbyEmailList.get(email);
    }

    public User getUserById(String id) throws UsuarioNaoExisteException {
        if (!idExists(id)) {
            throw new UsuarioNaoExisteException();
        }
        return userbyIDList.get(id);
    }

    public boolean emailExists(String email){

        return idbyEmailList.containsKey(email);
    }

    public boolean idExists(String id){

        return userbyIDList.containsKey(id);
    }

    @Override
    public void saveData() throws SaveError {
        saveMapToXML(userbyIDList, ID_FILE);
        saveMapToXML(idbyEmailList, EMAIL_FILE);
        saveMapToXML(idbyLicensePlate,LICENCE_PLATE_FILE);
    }

    @Override
    public void resetData() {

        userbyIDList.clear();
        idbyEmailList.clear();
        idbyLicensePlate.clear();

        resetFiles(ID_FILE, EMAIL_FILE, LICENCE_PLATE_FILE);
    }

}
