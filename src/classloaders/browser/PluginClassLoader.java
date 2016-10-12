package classloaders.browser;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by Valentina on 10.10.2016.
 */
public class PluginClassLoader extends URLClassLoader {

    public PluginClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String s) throws ClassNotFoundException {
        if(s.startsWith("classloaders.browser"))
            return super.loadClass(s);
        else return findClass(s);
    }
}
