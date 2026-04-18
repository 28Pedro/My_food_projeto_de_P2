package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.EnterpriseDataManeger;
import br.ufal.ic.myfood.models.enterprise.Enterprise;
import br.ufal.ic.myfood.models.enterprise.Restaurant;
import br.ufal.ic.myfood.models.validator.EnterpriseValidator;

import java.util.List;
import java.util.UUID;

public class EnterpriseManager {

    EnterpriseDataManeger enterpriseDataManeger;
    UserIntegrator userIntegrator;
    EnterpriseValidator enterpriseValidator;

    public EnterpriseManager( UserIntegrator userIntegrator) throws FileError {
        enterpriseDataManeger = new EnterpriseDataManeger();
        this.userIntegrator = userIntegrator;
        this.enterpriseValidator = new EnterpriseValidator(enterpriseDataManeger,userIntegrator);
    }

    public String createEnterprise(String entrepriseType, String ownerId, String name, String adress,
                                 String kitchenType) throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,
                                EmpresaComMesmoNomeeLocal, NomeInvalido{

       enterpriseValidator.validateCreateEnterprise(ownerId, name, adress);

        String enterpiseId = generateId();

        Enterprise newRestaurant = new Restaurant(entrepriseType,ownerId,name,adress,enterpiseId,kitchenType);

        enterpriseDataManeger.saveObject(newRestaurant);

        return enterpiseId;
    }

    public String getEntrepriseListByOwner(String ownerId) throws EmpresanaoCadastrada,
            UsuarioNaoPodeCriarEmpresa {

        enterpriseValidator.validateUser(ownerId);

        List<String> entrepriseList;
        try {
            entrepriseList = enterpriseDataManeger.getEnterprizeListByOwner(ownerId);

        } catch (EmpresanaoCadastrada e) {
            return "{[]}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        for (int i = 0; i < entrepriseList.size(); i++) {
            String id = entrepriseList.get(i);

            sb.append(enterpriseDataManeger.getEnterpriseByID(id).toString());

            if (i < entrepriseList.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]}");

        return sb.toString();
    }

    public String getIdEmpresa(String ownerId, String name, int index)
            throws NomeInvalido, IndiceMaiorQueEsperado,
            UsuarioNaoPodeCriarEmpresa, IndiceInvalido, NaoExisteEmpresaComEsseNome {

        enterpriseValidator.validateRequestGetIdEnterprise(ownerId,name,index);

        return enterpriseDataManeger.getIdEnterpriseByOwnerNameIndex(ownerId,name,index);
    }

    public void saveData() throws SaveError {
        enterpriseDataManeger.saveData();
    }

    public void resetData(){
        enterpriseDataManeger.resetData();
    }

    public String getAtributoEmpresa(String id, String atribute)
            throws EmpresanaoCadastrada, AtributoInvalido {

        Enterprise enterprise = enterpriseDataManeger.getEnterpriseByID(id);
        enterpriseValidator.validateAtribute(atribute);

        if(atribute.equalsIgnoreCase("dono")) {
            try {
                return userIntegrator.getUserNameById(enterprise.getAtribute(atribute));
            } catch (Exception e) {
                throw new EmpresanaoCadastrada();
            }
        }else{
            return enterprise.getAtribute(atribute);
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}

