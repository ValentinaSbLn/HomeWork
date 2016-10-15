package annotations;

import java.lang.reflect.Proxy;

/**
 * Created by Valentina on 02.10.2016.
 */
public class Main {
    public static void main(String[] args) {

        MultiplyForTwo mult = new MultiplyForTwo();

        MultiplyFor proxyMulti=(MultiplyFor) Proxy.newProxyInstance(MultiplyForTwo.class.getClassLoader(),
                MultiplyForTwo.class.getInterfaces(),
                new CacheHandlerWithClass(mult));

        int a=proxyMulti.multiply(5);
        int b=proxyMulti.multiply(6);
        int d=proxyMulti.multiply(6);
        int n=proxyMulti.multiply(5);
        int m=proxyMulti.multiply(15);
        System.out.println("multiply: "+a+" "+b+" "+d+ " " +n+" "+m+"\n");

        int n1=proxyMulti.multiplyCount(5);
        int m1=proxyMulti.multiplyCount(15);
        int m2=proxyMulti.multiplyCount(0);

        System.out.println("multiplyCount: "+n1+" "+m1+" "+m2+"\n");

        int c=proxyMulti.powOfTwo(4);

        System.out.println("powOfTwo: "+c+"\n");
    }
}
