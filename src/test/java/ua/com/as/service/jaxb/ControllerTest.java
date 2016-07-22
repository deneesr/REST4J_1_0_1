package ua.com.as.service.jaxb;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.as.jaxb.Controller;

import java.util.ArrayList;

/**
 *
 */
public class ControllerTest {

    private static final String CLASS_PACKAGE = "ua.com.as.service.ControllerClassTest";

    private static Controller controller;

    @BeforeClass
    public static void init() {
        controller = new Controller();
    }

    @Test
    public void testSetAndGetClassPackage() {
        controller.setFullClassName(CLASS_PACKAGE);
        Assert.assertEquals(CLASS_PACKAGE, controller.getFullClassName());
    }

    @Test
    public void testSetAndGetRests() {
        controller.setRests(new ArrayList<>());
        Assert.assertNotNull(controller.getRests());
    }
}
