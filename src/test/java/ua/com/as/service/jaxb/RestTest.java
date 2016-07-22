package ua.com.as.service.jaxb;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.jaxb.Method;
import ua.com.as.jaxb.Rest;

/**
 *
 */
public class RestTest {

    private static final String REST_PATH = "/getTest";
    private static final String REST_ACTION = "GET";

    private static Rest rest;

    @BeforeClass
    public static void init() {
        rest = new Rest();
    }

    @Test
    public void testSetAndGetPath() {
        rest.setPath(REST_PATH);
        Assert.assertEquals(REST_PATH, rest.getPath());
    }

    @Test
    public void testSetAndGetAction() {
        rest.setAction(REST_ACTION);
        Assert.assertEquals(REST_ACTION, rest.getAction());
    }

    @Test
    public void testSetAndGetMethod() {
        rest.setMethod(new Method());
        Assert.assertNotNull(rest.getMethod());
    }
}
