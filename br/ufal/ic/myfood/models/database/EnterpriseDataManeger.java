package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.records.PairKey;
import br.ufal.ic.myfood.models.enterprise.Enterprise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnterpriseDataManeger extends DataManger<Enterprise> {

    private final String ENTERPISE_FILE = getFILE_PATH() + "enterprise_by_id.xml";
    private final String DOMAIN_FILE = getFILE_PATH() + "Owner_by_name.xml";
    private final String NAME_ADDRESS_FILE = getFILE_PATH() + "enterprise_by_name_address.xml";
    private final String RAW_ENTERPRISE_LIST_BY_OWNER = getFILE_PATH() + "raw_enterprise_list_by_owner.xml";
    private final String ENTERPRISE_LIST_BY_OWNER_NAME_FILE = getFILE_PATH() + "enterprize_list_by_owner_name.xml";

    private Map<String,Enterprise> enterpriseById;
    private Map<PairKey<String,String>, String> iDenterpriseByNameAdress;
    private Map<String,String> ownerbyname;
    private Map<String,List<String>> rawEnterpriseListbyOwner;
    private Map<String, Map<String, List<String>>> enterpriseListByOwnerName;

    public EnterpriseDataManeger() throws FileError {
        enterpriseById = loadMapFromXML(ENTERPISE_FILE);
        iDenterpriseByNameAdress = loadMapFromXML(NAME_ADDRESS_FILE);
        ownerbyname = loadMapFromXML(DOMAIN_FILE);
        rawEnterpriseListbyOwner = loadMapFromXML(RAW_ENTERPRISE_LIST_BY_OWNER);
        enterpriseListByOwnerName = loadMapFromXML(ENTERPRISE_LIST_BY_OWNER_NAME_FILE);
    }

    public boolean nameExists(String name){
        return ownerbyname.containsKey(name);
    }


    public boolean idbyNameAdressExists(String name, String adress){
        return iDenterpriseByNameAdress.containsKey(makeKey(name, adress));
    }

    public String getIdbyName(String name) throws NomeInvalido {
        if(!nameExists(name)){
            throw new NomeInvalido();
        }

        return ownerbyname.get(name);
    }

    public List getEnterprizeListByOwner(String ownerId) throws EmpresanaoCadastrada {

        if(!rawEnterpriseListbyOwner.containsKey(ownerId)){
            throw new EmpresanaoCadastrada();
        }

        return rawEnterpriseListbyOwner.get(ownerId);
    }

    public String getIdEnterpriseByOwnerNameIndex(String ownerId, String name, int index)
        throws  NaoExisteEmpresaComEsseNome,IndiceMaiorQueEsperado{

        if(!enterpriseListByOwnerName.containsKey(ownerId)){
            throw new NaoExisteEmpresaComEsseNome();
        }

        Map<String,List<String>> enterpises = enterpriseListByOwnerName.get(ownerId);

        if(!enterpises.containsKey(name)){
            throw new NaoExisteEmpresaComEsseNome();
        }

        List<String> enterprisesByName = enterpises.get(name);

        if(index >= enterprisesByName.size()){
            throw new IndiceMaiorQueEsperado();
        }

        return enterprisesByName.get(index);
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

       String id = enterprise.getId();
       String ownerId = enterprise.getOwnerId();
       String name = enterprise.getName();
       String adress = enterprise.getAdress();

       fillEnterpriseById(id, enterprise);
       fillIdEnterpriseByNameAdress(id,name,adress);
       fillEnterpriseDomainOwner(name,ownerId);
       fillRawEnterpriseListByOwner(ownerId,id);
       fillEnterepiseListByOwnerName(ownerId,name,id);

    }

    @Override
    public void saveData() throws SaveError {
        saveMapToXML(ownerbyname, DOMAIN_FILE);
        saveMapToXML(enterpriseById,ENTERPISE_FILE);
        saveMapToXML(iDenterpriseByNameAdress, NAME_ADDRESS_FILE);
        saveMapToXML(rawEnterpriseListbyOwner,RAW_ENTERPRISE_LIST_BY_OWNER);
        saveMapToXML(enterpriseListByOwnerName,ENTERPRISE_LIST_BY_OWNER_NAME_FILE);
    }

    @Override
    public void resetData() {

        iDenterpriseByNameAdress.clear();
        ownerbyname.clear();
        enterpriseById.clear();
        enterpriseListByOwnerName.clear();
        rawEnterpriseListbyOwner.clear();

        resetFiles(ENTERPISE_FILE, DOMAIN_FILE,NAME_ADDRESS_FILE,
                ENTERPRISE_LIST_BY_OWNER_NAME_FILE,RAW_ENTERPRISE_LIST_BY_OWNER);
    }

    private void fillEnterpriseById(String id, Enterprise enterprise){
        enterpriseById.put(id, enterprise);
    }

    private void fillIdEnterpriseByNameAdress(String id, String name, String adress){

        iDenterpriseByNameAdress.put(
                makeKey(name, adress),
                id
        );
    }

    private void fillEnterpriseDomainOwner(String name, String ownerId){

        if(!ownerbyname.containsKey(name)){
            ownerbyname.put(name,ownerId);
        }
    }

    private void fillRawEnterpriseListByOwner(String ownerId, String id){

        if(!rawEnterpriseListbyOwner.containsKey(ownerId)){
            rawEnterpriseListbyOwner.put(ownerId,new ArrayList<String>());
        }

        List<String> list = rawEnterpriseListbyOwner.get(ownerId);
        list.add(id);
    }

    private void fillEnterepiseListByOwnerName(String ownerId, String name, String id) {

        if (!enterpriseListByOwnerName.containsKey(ownerId)) {
            enterpriseListByOwnerName.put(
                    ownerId,
                    new HashMap<String, List<String>>()
            );
        }

        Map<String, List<String>> map = enterpriseListByOwnerName.get(ownerId);

        if (!map.containsKey(name)) {
            map.put(name, new ArrayList<String>());
        }

        List<String> list = map.get(name);
        list.add(id);
    }

    private PairKey<String,String> makeKey(String name, String address){
        return new PairKey<String,String>(name, address);
    }

}
