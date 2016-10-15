package annotations;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Valentina on 15.10.2016.
 */
public class CacheKey implements Serializable{
    String methodName;
    Object[] args;

    public CacheKey(String methodName, Object[] args) {
        this.methodName = methodName;
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheKey cacheKey = (CacheKey) o;

        if (!methodName.equals(cacheKey.methodName)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, cacheKey.args);

    }

    @Override
    public int hashCode() {
        int result = methodName.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    public boolean isContains(String name){
        return (name.equals(methodName)) ? true : false;

    }
}

