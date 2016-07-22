package ua.com.as.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code Path} annotation to mark the methods of class of controller.
 */


/**
 * <p><code>Path</code> annotation indicates that an annotated method is a
 * method which will be invoked with from the outside.
 * <p>Methods with <code>Path</code> annotation may be found via PackageScanner.
 *
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Path {
    /**
     * <p>Url path value. Required parameter.
     *
     * @return url path
     */
    String value();

    /**
     * <p>Array of <code>Http methods</code> names. You may specify
     * these methods: <code>GET, POST, PUT, DELETE</code>.
     * <p>Not required. Default value is:
     * <code>{"GET", "POST", "PUT", "DELETE"}</code>
     *
     * @return HTTP method list
     */
    String[] methods() default {"GET", "POST", "PUT", "DELETE"};
}
