package br.ufal.ic.myfood.models.database;
import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.users.User;

import java.util.Map;

public class UserDataManage extends DataManger<User> {

    private final String ID_FILE = getFILE_PATH() + "users_by_id.xml";
    private final String EMAIL_FILE = getFILE_PATH() + "users_by_email.xml";

    private Map<String, User> userbyIDList;
    private Map<String, String> idbyEmailList;

    public UserDataManage() throws FileError {
       userbyIDList = loadMapFromXML(ID_FILE);
       idbyEmailList = loadMapFromXML(EMAIL_FILE);

    }

    @Override
    public void saveObject(User user)
    {
        userbyIDList.put(user.getId(), user);
        idbyEmailList.put(user.getEmail(), user.getId());

    }

    public String getAtributeById(String id, String atribute)
    throws UsuarioNaoExisteException{

       if(!userbyIDList.containsKey(id)){
           throw new UsuarioNaoExisteException();
       }

       User user = userbyIDList.get(id);

        return switch (atribute.toLowerCase()) {
            case "nome"     -> user.getName();
            case "email"    -> user.getEmail();
            case "endereco" -> user.getAdress();
            case "cpf"      -> user.getCpf();
            case "senha"    -> user.getPassword();
            default -> null;
        };
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
    }

    @Override
    public void resetData() {

        userbyIDList.clear();
        idbyEmailList.clear();

        resetFiles(ID_FILE, EMAIL_FILE);
    }

}
