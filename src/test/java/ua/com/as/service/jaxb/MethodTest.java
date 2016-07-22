package ua.com.as.service.jaxb;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.jaxb.Method;

/**
 *
 */
public class MethodTest {

    private static final String METHOD_NAME = "getTest";

    @Test
    public void testSetAndGetName() {
        Method method = new Method();
        method.setName(METHOD_NAME);
        Assert.assertEquals(METHOD_NAME, method.getName());
    }
}
