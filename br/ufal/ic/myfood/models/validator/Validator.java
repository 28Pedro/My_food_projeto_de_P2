package br.ufal.ic.myfood.models.validator;

public abstract class Validator<D> {

    protected final D dataBase;

     public  Validator (D dataBase){
         this.dataBase = dataBase;
     }

     protected boolean fildExists(String Fild)
     {
         return Fild != null && !Fild.isBlank();
     }

}
