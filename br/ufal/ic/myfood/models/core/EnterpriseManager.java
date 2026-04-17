package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.database.EnterpriseDataManeger;
import br.ufal.ic.myfood.models.enterprise.Enterprise;
import br.ufal.ic.myfood.models.enterprise.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnterpriseManager {

    EnterpriseDataManeger enterpriseDataManeger;
    UserIntegrator userIntegrator;

    public EnterpriseManager( UserIntegrator userIntegrator) throws FileError {
        enterpriseDataManeger = new EnterpriseDataManeger();
        this.userIntegrator = userIntegrator;
    }

    public String createEnterprise(String entrepriseType, String ownerId, String name, String adress,
                                 String kitchenType) throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,
                                EmpresaComMesmoNomeeLocal, NomeInvalido, UsuarioNaoExisteException {

        if(!userIntegrator.userIsOwner(ownerId)){
            throw new UsuarioNaoPodeCriarEmpresa();
        }
        if(!isNameValid(name, ownerId)){
            throw new NomeDeEmpresaJaExiste();
        }
        if(enterpriseDataManeger.idbyNameAdressExists(name, adress)){
            throw new EmpresaComMesmoNomeeLocal();
        }

        String empresaId = generateId();
        Enterprise newRestaurant = new Restaurant(entrepriseType,ownerId,name,adress,kitchenType,empresaId);

        enterpriseDataManeger.saveObject(newRestaurant);

        return empresaId;
    }

    public String getEntrepriseListByOwner(String ownerId) throws EmpresanaoCadastrada, UsuarioNaoPodeCriarEmpresa, UsuarioNaoExisteException {

        if(!userIntegrator.userIsOwner(ownerId)){
            throw new UsuarioNaoPodeCriarEmpresa();
        }

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

    private boolean isNameValid(String name, String ownerId) throws NomeInvalido {

        if(enterpriseDataManeger.nameExists(name)) {
            return (enterpriseDataManeger.getIdbyName(name).equals(ownerId));
        }

        return true;
    }

    public void saveData() throws SaveError {
        enterpriseDataManeger.saveData();
    }

    public void resetData(){
        enterpriseDataManeger.resetData();
    }

    public String getAtributoEmpresa(String empresaId, String atributo) throws EmpresanaoCadastrada, AtributoInvalido, UsuarioNaoExisteException {

        Enterprise empresa = enterpriseDataManeger.getEnterpriseByID(empresaId);

        if(atributo == null || atributo.trim().isEmpty()){
            throw new AtributoInvalido();
        }

        return switch (atributo.toLowerCase()) {
            case "nome" -> empresa.getName();
            case "endereco" -> empresa.getAdress();
            case "tipocozinha" -> empresa.getKitchenType();
            case "dono" -> userIntegrator.getUserNameById(empresa.getOwnerId());
            default -> throw new AtributoInvalido();
        };
    }

    public String getIdEmpresa(String ownerId, String nome, int indice)
            throws EmpresanaoCadastrada, NomeInvalido, IndiceMaiorQueEsperado,
            UsuarioNaoExisteException, UsuarioNaoPodeCriarEmpresa, IndiceInvalido,
            NaoExisteEmpresaComEsseNome {

        if(nome == null || nome.trim().isEmpty()){
            throw new NomeInvalido();
        }

        if(indice < 0){
            throw new IndiceInvalido();
        }

        if(!userIntegrator.userIsOwner(ownerId)){
            throw new UsuarioNaoPodeCriarEmpresa();
        }

        List<String> empresas = enterpriseDataManeger.getEnterprizeListByOwner(ownerId);

        List<String> empresasComNome = new ArrayList<>();
        for(String id : empresas){
            if(enterpriseDataManeger.getEnterpriseByID(id).getName().equals(nome)){
                empresasComNome.add(id);
            }
        }

        if(empresasComNome.isEmpty()){
            throw new NaoExisteEmpresaComEsseNome();
        }

        if(indice >= empresasComNome.size()){
            throw new IndiceMaiorQueEsperado();
        }

        return empresasComNome.get(indice);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
