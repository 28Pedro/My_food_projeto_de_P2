package br.ufal.ic.myfood.models.integrators;

import br.ufal.ic.myfood.exceptions.EmpresanaoCadastrada;
import br.ufal.ic.myfood.models.core.EnterpriseManager;

public class EnterpriseIntegrator {

    private final EnterpriseManager enterpriseManager;

    public EnterpriseIntegrator(EnterpriseManager enterpriseManager){
        this.enterpriseManager = enterpriseManager;
    }

    public String getEnterpiseNameById(String entrepriseId) throws EmpresanaoCadastrada {
        return enterpriseManager.getEnterpriseNameById(entrepriseId);
    }



}
