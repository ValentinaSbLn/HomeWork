package annotations;

/**
 * Created by Valentina on 02.10.2016.
 */
public class MultiplyForTwo implements MultiplyFor {

    private final int two = 2;

    @Override
    @Cache
    public int multiply(int d) {
        return two * d;
    }

    @Override
    public int powOfTwo(int d) {
        int mult = 1;
        for (int i = 1; i <= d; i++) {
            mult = mult * two;
        }
        return mult;
    }

}
