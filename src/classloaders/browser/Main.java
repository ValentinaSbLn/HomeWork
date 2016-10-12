package classloaders.browser;

import java.net.MalformedURLException;

/**
 * Created by Valentina on 09.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        PluginManager plgMng = new PluginManager("E:/test");
        try {
            Plugin plg1=plgMng.load("plugin1", "MyPlugin");
            plg1.doUsefull();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Plugin plg2=plgMng.load("plugin2", "MyPlugin");

            plg2.doUsefull();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Plugin plg3=plgMng.load("", "classloaders.browser.MyPlugin");

            plg3.doUsefull();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
