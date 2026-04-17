package br.ufal.ic.myfood.exceptions;

public class FileError extends Exception{
    public FileError(String FILE_PATH){super("Arquivo XML inválido ou vazio: "  + FILE_PATH);}
}
