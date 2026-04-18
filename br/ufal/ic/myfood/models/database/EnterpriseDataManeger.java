package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.records.PairKey;
import br.ufal.ic.myfood.exceptions.EmpresanaoCadastrada;
import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.NomeInvalido;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.models.enterprise.Enterprise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnterpriseDataManeger extends DataManger<Enterprise> {

    private final String ENTERPISE_FILE = getFILE_PATH() + "enterprise_by_id.xml";
    private final String ENTERPISE_OWNER_FILE = getFILE_PATH() + "id_enterprize_by_owner_Adress.xml";
    private final String DOMAIN_FILE = getFILE_PATH() + "Owner_by_name.xml";
    private final String ENTERPRIZES_BY_OWNER = getFILE_PATH() + "enterprizes_by_owner.xml";
    private final String NAME_ADDRESS_FILE = getFILE_PATH() + "enterprise_by_name_address.xml";

    private Map<String,Enterprise> enterpriseById;
    private Map<PairKey<String,String>, String> iDenterpriseByOwnerAdress;
    private Map<PairKey<String,String>, String> iDenterpriseByNameAdress;
    private Map<String,String> ownerbyname;
    private Map<String, List<String>> enterprizesByOwner;

    public EnterpriseDataManeger() throws FileError {
        enterpriseById = loadMapFromXML(ENTERPISE_FILE);
        iDenterpriseByOwnerAdress = loadMapFromXML(ENTERPISE_OWNER_FILE);
        iDenterpriseByNameAdress = loadMapFromXML(NAME_ADDRESS_FILE);
        ownerbyname = loadMapFromXML(DOMAIN_FILE);
        enterprizesByOwner = loadMapFromXML(ENTERPRIZES_BY_OWNER);

    }

    public boolean nameExists(String name){
        return ownerbyname.containsKey(name);
    }

    public boolean idbyOwnerAdressExists(String ownerId, String adress){
        return iDenterpriseByOwnerAdress.containsKey(makeKeyOwnerAdress(ownerId,adress));
    }

    public boolean idbyNameAdressExists(String name, String adress){
        return iDenterpriseByNameAdress.containsKey(makeKeyNameAddress(name, adress));
    }

    public boolean idExists(String id){
        return enterpriseById.containsKey(id);
    }

    public List<String> getEnterprizeListByOwner(String ownerId) throws
            EmpresanaoCadastrada{

        if(!enterprizesByOwner.containsKey(ownerId)){
            throw new EmpresanaoCadastrada();
        }
        return enterprizesByOwner.get(ownerId);
   }

    public String getIdbyName(String name) throws NomeInvalido {
        if(!nameExists(name)){
            throw new NomeInvalido();
        }

        return ownerbyname.get(name);
    }

    public Enterprise getEnterpriseByID(String id)
            throws EmpresanaoCadastrada{
        if(!enterpriseById.containsKey(id)){
            throw new EmpresanaoCadastrada();
        }
        return enterpriseById.get(id);
    }

    @Override
    public void saveObject(Enterprise enterprise){

        enterpriseById.put(enterprise.getId(), enterprise);

        iDenterpriseByOwnerAdress.put(
                makeKeyOwnerAdress(enterprise.getOwnerId(), enterprise.getAdress()),
                enterprise.getId()
        );

        iDenterpriseByNameAdress.put(
                makeKeyNameAddress(enterprise.getName(), enterprise.getAdress()),
                enterprise.getId()
        );

        if(!ownerbyname.containsKey(enterprise.getName())){
            ownerbyname.put(enterprise.getName(),enterprise.getOwnerId());
        }

        if(!enterprizesByOwner.containsKey(enterprise.getOwnerId())){
            enterprizesByOwner.put(enterprise.getOwnerId(), new ArrayList<String>());
        }

        addEnterpiseId(enterprizesByOwner.get(enterprise.getOwnerId()), enterprise.getId());

    }

    @Override
    public void saveData() throws SaveError {
        saveMapToXML(ownerbyname, DOMAIN_FILE);
        saveMapToXML(enterprizesByOwner,ENTERPRIZES_BY_OWNER);
        saveMapToXML(enterpriseById,ENTERPISE_FILE);
        saveMapToXML(iDenterpriseByOwnerAdress,ENTERPISE_OWNER_FILE);
        saveMapToXML(iDenterpriseByNameAdress, NAME_ADDRESS_FILE);
    }

    @Override
    public void resetData() {
        iDenterpriseByOwnerAdress.clear();
        iDenterpriseByNameAdress.clear();
        ownerbyname.clear();
        enterprizesByOwner.clear();
        enterpriseById.clear();
        resetFiles(ENTERPISE_FILE, DOMAIN_FILE,ENTERPISE_OWNER_FILE,ENTERPRIZES_BY_OWNER, NAME_ADDRESS_FILE);
    }

    private void addEnterpiseId(List<String> Idlist, String entrepriseId){

        if(Idlist == null){
            Idlist = new ArrayList<String>();
        }
        Idlist.add(entrepriseId);
    }

    private PairKey<String,String> makeKeyOwnerAdress(String OwnerId, String adress){
        return new PairKey<String,String>(OwnerId,adress);
    }

    private PairKey<String,String> makeKeyNameAddress(String name, String address){
        return new PairKey<String,String>(name, address);
    }

}
