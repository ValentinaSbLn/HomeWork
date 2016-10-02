package annotations;

import java.lang.reflect.Proxy;

/**
 * Created by Valentina on 02.10.2016.
 */
public class Test {
    public static void main(String[] args) {

        MultiplyForTwo mult = new MultiplyForTwo();

        MultiplyFor proxyMulti=(MultiplyFor) Proxy.newProxyInstance(MultiplyForTwo.class.getClassLoader(),
                MultiplyForTwo.class.getInterfaces(),
                new LogHandler(mult));

        int a=proxyMulti.multiply(5);
        int b=proxyMulti.multiply(6);
        int d=proxyMulti.multiply(6);
        int n=proxyMulti.multiply(5);
        int m=proxyMulti.multiply(15);
        System.out.println(a+" "+b+" "+d+ " " +n+" "+m);

        int c=proxyMulti.powOfTwo(4);
        System.out.println(c);
    }
}
