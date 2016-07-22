package ua.com.as.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.as.model.RestDefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class RestDefinitionTest {

    private static final String CLASS_PACKAGE = "ua.com.as.service.ControllerClassTest";
    private static final String METHOD_NAME = "getTest";

    private static RestDefinition restDefinition;

    @BeforeClass
    public static void init() {
        restDefinition = new RestDefinition(CLASS_PACKAGE, METHOD_NAME);
    }

    @Test
    public void testGetClassPackage() {
        assertEquals(CLASS_PACKAGE, restDefinition.getFullClassName());
    }

    @Test
    public void testGetMethodName() {
        assertEquals(METHOD_NAME, restDefinition.getMethodName());
        Assert.assertEquals(METHOD_NAME, restDefinition.getMethodName());
    }

    @Test
    public void testEquals(){
        RestDefinition definition = new RestDefinition("2", "3");
        RestDefinition definition2 = new RestDefinition("2", "3");
        assertTrue(definition.hashCode() == definition2.hashCode());
        assertEquals(definition, definition2);
    }

}
