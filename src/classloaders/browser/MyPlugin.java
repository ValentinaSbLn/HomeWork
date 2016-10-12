package classloaders.browser;

/**
 * Created by Valentina on 10.10.2016.
 */
public class MyPlugin implements Plugin {
    @Override
    public void doUsefull() {
        System.out.println("Plugin_1 " + this.getClass().getClassLoader());

    }
}
