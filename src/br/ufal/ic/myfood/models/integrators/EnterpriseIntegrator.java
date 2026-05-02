package br.ufal.ic.myfood.models.integrators;

import br.ufal.ic.myfood.exceptions.AtributoInvalido;
import br.ufal.ic.myfood.exceptions.EmpresanaoCadastrada;
import br.ufal.ic.myfood.models.manageres.EnterpriseManager;
import br.ufal.ic.myfood.records.PairKey;

import java.util.List;

public class EnterpriseIntegrator {

    private final EnterpriseManager enterpriseManager;

    public EnterpriseIntegrator(EnterpriseManager enterpriseManager){
        this.enterpriseManager = enterpriseManager;
    }

    public String getEnterpiseNameById(String entrepriseId) throws EmpresanaoCadastrada {
        return enterpriseManager.getEnterpriseNameById(entrepriseId);
    }

    public PairKey<String,String> getEnterpiseNameAdressById(String enterpiseId)
    throws EmpresanaoCadastrada{

        try {
            return new PairKey<String,String>(getEnterpiseNameById(enterpiseId),
                    getEnterpriseAdressById(enterpiseId)
            );
        } catch (AtributoInvalido e) {
            throw new EmpresanaoCadastrada();
        }

    }

    public List<String> getDeliveryManList(String enterpriseId)
    throws EmpresanaoCadastrada{
        return enterpriseManager.GetDeliveryManList(enterpriseId);
    }

    public boolean enterpriseIsPharmacy(String enterpriseId){

    }

    private String getEnterpriseAdressById(String enterpriseId) throws AtributoInvalido,
            EmpresanaoCadastrada{

        return  enterpriseManager.getAtributoEmpresa(enterpriseId,"endereco");
    }



}
