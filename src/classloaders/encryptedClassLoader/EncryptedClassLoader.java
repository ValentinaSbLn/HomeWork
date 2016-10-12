package classloaders.encryptedClassLoader;

import java.io.File;

/**
 * Created by Valentina on 10.10.2016.
 */
public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;
    Encrypter encrypter;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {

        super(parent);
        this.key = key;
        this.dir = dir;
        encrypter = new Encrypter(key);
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(dir + "/" + name.substring(name.lastIndexOf(".")+1, name.length())+".encrpt_class");
        System.out.println("file "+file+" getPath "+file.getPath());
        byte[] byteDecript = encrypter.decript(file);

        System.out.println("byteDecript"+byteDecript.length);
        return defineClass(name, byteDecript, 0, byteDecript.length);
    }
}

