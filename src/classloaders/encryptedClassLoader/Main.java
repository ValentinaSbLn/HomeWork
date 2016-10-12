package classloaders.encryptedClassLoader;
import classloaders.browser.Plugin;

import java.io.File;

/**
 * Created by Valentina on 10.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        Encrypter  encrypter = new Encrypter("1");
        encrypter.encrypt(new File("E:/test/plugin2/MyPlugin.class"));
        try {
            EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader("1", new File("E:/test/plugin2/"), Main.class.getClassLoader());
            Plugin p = (Plugin) encryptedClassLoader.loadClass("MyPlugin").newInstance();
            p.doUsefull();
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

    }
}
