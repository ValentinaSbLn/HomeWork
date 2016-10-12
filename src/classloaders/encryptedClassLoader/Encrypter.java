package classloaders.encryptedClassLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Valentina on 10.10.2016.
 */
public class Encrypter {
    String key;
    Encrypter(String key){
        this.key=key;
    }

    public void encrypt(File file) {

        Path filePath = Paths.get(file.getPath());

        byte[] byteEncrptFile;
        try {
            byteEncrptFile = Files.readAllBytes(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла", e);
        }
       try{
           int keyForEncrypt = Integer.parseInt(key);

        for (int i = 0; i < byteEncrptFile.length; i++) {
            byteEncrptFile[i] = (byte) (byteEncrptFile[i] + keyForEncrypt);
        }
       }
       catch (NumberFormatException ex){
        System.out.println("Неверный ключ для шифрования " +ex);
    }
        try {
            System.out.println(file.getPath().replace(".class", ".encrpt_class"));
            Files.write(Paths.get(file.getPath().replace(".class", ".encrpt_class")), byteEncrptFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] decript(File file) {
        try {
            int keyForDecript = Integer.parseInt(key);


        byte[] bytesDecript= new byte[(int) file.length()];
        try {
            bytesDecript = Files.readAllBytes(file.toPath());
            for (int i = 0; i < bytesDecript.length; i++) {
                bytesDecript[i] = (byte)(bytesDecript[i]-keyForDecript);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return bytesDecript;
    }  catch (NumberFormatException ex){
        System.out.println("Неверный ключ для шифрования " +ex);
            return new byte[0];
        }
    }


}
