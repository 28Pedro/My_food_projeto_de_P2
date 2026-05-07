package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.integrators.UserIntegrator;
import br.ufal.ic.myfood.models.database.EnterpriseDataManeger;

public class EnterpriseValidator extends Validator<EnterpriseDataManeger>{

    private final int TIME_LENGTH = 5;
    private final UserIntegrator userIntegrator;

    public EnterpriseValidator(EnterpriseDataManeger database, UserIntegrator userIntegrator){
        super(database);
        this.userIntegrator = userIntegrator;
    }

    public void validateCreateEnterprise(String ownerId, String name, String adress, String enterpiseType)
            throws UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,EmpresaComMesmoNomeeLocal,
            NomeInvalido, EnderecoEmpresaInvalido,TipoEmpresaInvalido {

        validateUser(ownerId);

        if(!fildExists(name) ){
            throw new NomeInvalido();
        }

        if(!fildExists(adress)){
            throw new EnderecoEmpresaInvalido();
        }

        if(!TypeExists(enterpiseType)){
            throw new TipoEmpresaInvalido();
        }

        if(!isNameValid(name, ownerId)){
            throw new NomeDeEmpresaJaExiste();
        }
        if(dataBase.idbyNameAdressExists(name, adress)){
            throw new EmpresaComMesmoNomeeLocal();
        }
    }

    public void validateSupermarketRequests(String ownerId, String name, String adress, String enterpiseType,
                                            String open, String closes, String supermaketType)
    throws FormatoDeHoraInvalido,HorarioInvalido,TipoMercadoInvalido,UsuarioNaoPodeCriarEmpresa, NomeDeEmpresaJaExiste,
            EmpresaComMesmoNomeeLocal, NomeInvalido, EnderecoEmpresaInvalido,TipoEmpresaInvalido{


        if(open == null || closes == null){ // essa validação diferencia Blank de Null por isso não usar da classe mãe
            throw new HorarioInvalido();
        }

        if(open.isBlank() || closes.isBlank()){ // essa validação diferencia Blank de Null por isso não usar da classe mãe
            throw new FormatoDeHoraInvalido();
        }

        if(!fildExists(supermaketType)){
            throw new TipoMercadoInvalido();
        }

        validateCreateEnterprise(ownerId,name,adress,enterpiseType);

        GeneralTimeValidation(open,closes);

    }

    public void validateRequestGetIdEnterprise(String ownerId, String name, int index)
    throws NomeInvalido, IndiceInvalido, UsuarioNaoPodeCriarEmpresa{

        if(!fildExists(name)){
            throw new NomeInvalido();
        }

        if(index < 0){
            throw new IndiceInvalido();
        }

        validateUser(ownerId);

    }

    public void validateUser(String ownerId) throws UsuarioNaoPodeCriarEmpresa{

        try {
            if (!userIntegrator.userIsOwner(ownerId)) {
                throw new UsuarioNaoPodeCriarEmpresa();
            }
        } catch (Exception e) {
            throw new UsuarioNaoPodeCriarEmpresa();
        }
    }

    public void  validateAtribute(String atribute) throws AtributoInvalido{
        if(!fildExists(atribute)){
            throw new AtributoInvalido();
        }
    }

    private boolean isNameValid(String name, String ownerId) throws NomeInvalido {

        if(dataBase.nameExists(name)) {
            return (dataBase.getIdbyName(name).equals(ownerId));
        }

        return true;
    }

    public void GeneralTimeValidation(String open, String closes)
    throws FormatoDeHoraInvalido,HorarioInvalido{

        if(open == null || closes == null){ // essa validação diferencia Blank de Null por isso não usar da classe mãe
            throw new HorarioInvalido();
        }

        validateTimeFormat(open);
        validateTimeFormat(closes);

        int openMinutes = toMinutes(open);
        int closeMinutes = toMinutes(closes);

        if (closeMinutes <= openMinutes) {
            throw new HorarioInvalido();
        }
    }

    private void validateTimeFormat(String time)
        throws FormatoDeHoraInvalido, HorarioInvalido{

        if ( time.length() != TIME_LENGTH ||
             !time.matches("\\d{2}:\\d{2}")
        ) { throw new FormatoDeHoraInvalido(); }

        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new HorarioInvalido();
        }
    }

    private int toMinutes(String time) {

        String[] parts = time.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour * 60 + minute;
    }

    private boolean TypeExists(String enterpriseType) {

        if (enterpriseType == null) {
            return false;
        }

        return (enterpriseType.matches("restaurante") ||
                enterpriseType.matches("mercado") ||
                enterpriseType.matches("farmacia"));
    }



}
