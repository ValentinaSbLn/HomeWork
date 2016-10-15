package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static annotations.CacheType.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created by Valentina on 02.10.2016.
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface Cache {
    CacheType cacheType() default MEMORY;
}
