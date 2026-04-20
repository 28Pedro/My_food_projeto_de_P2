package br.ufal.ic.myfood.models.database;

import br.ufal.ic.myfood.exceptions.FileError;
import br.ufal.ic.myfood.exceptions.SaveError;
import br.ufal.ic.myfood.records.PairKey;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class DataManger<O> {

    private final String FILE_PATH = "src/br/ufal/ic/myfood/data/";

    public <K, V> void saveMapToXML(Map<K, V> map, String filePath)
    throws SaveError{
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)))) {
            encoder.writeObject(map);
        } catch (FileNotFoundException e) {
            throw new SaveError();
        }
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> loadMapFromXML(String filePath) throws FileError {
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object data = decoder.readObject();
            return (Map<K, V>) data;
        } catch (Exception e) {
            throw new FileError(filePath);
        }
    }

    public void resetFiles(String... filePaths) {
        for (String path : filePaths) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public String getFILE_PATH(){
        return FILE_PATH;
    }

    protected PairKey<String,String> makeKey(String str1, String str2){
        return new PairKey<String,String>(str1, str2);
    }

    public abstract void saveData() throws Exception;
    public abstract void resetData();
    public abstract void saveObject(O object);
}
