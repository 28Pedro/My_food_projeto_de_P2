package br.ufal.ic.myfood.models.validator;

import br.ufal.ic.myfood.models.database.DataManger;

public abstract class Validator<D> {

    protected final D dataBase;

     public  Validator (D dataBase){
         this.dataBase = dataBase;
     }

     protected boolean FildExists(String Fild)
     {
         return Fild != null && !Fild.isBlank();
     }

}
