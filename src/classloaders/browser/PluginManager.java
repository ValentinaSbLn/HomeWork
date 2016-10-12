package classloaders.browser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Valentina on 09.10.2016.
 */
public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL path = new URL("file:" + pluginRootDirectory +"/"+ pluginName + "/");

        PluginClassLoader loader = new PluginClassLoader(new URL[]{path});

        return  (Plugin) loader.loadClass(pluginClassName).newInstance();
    }
}


