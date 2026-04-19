package br.ufal.ic.myfood.models.core;

import br.ufal.ic.myfood.exceptions.EmpresanaoCadastrada;

public class EnterpriseIntegrator {

    private final EnterpriseManager enterpriseManager;

    public EnterpriseIntegrator(EnterpriseManager enterpriseManager){
        this.enterpriseManager = enterpriseManager;
    }

    public String getEnterpiseNameById(String entrepriseId) throws EmpresanaoCadastrada {
        return enterpriseManager.getEnterpriseNameById(entrepriseId);
    }



}
