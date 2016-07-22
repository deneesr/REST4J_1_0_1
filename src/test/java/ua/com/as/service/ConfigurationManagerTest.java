package ua.com.as.service;

import org.junit.Before;
import org.junit.Test;
import ua.com.as.exception.ConfigurationException;
import ua.com.as.jaxb.Controller;
import ua.com.as.jaxb.Method;
import ua.com.as.jaxb.Rest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * mrserfr.
 * 16.06.2016
 */
public class ConfigurationManagerTest {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_NAME = "getTest";
    private static final String RESPONSE_GET_METHOD = "ControllerClassTest, method: getTest";

    @Before
    public void before() throws Exception {
        List<Controller> controllers = new ArrayList<>();
        Controller controller1 = new Controller();
        controller1.setFullClassName("ua.com.as.service.ControllerClassTest");

        Rest rest = new Rest();
        Method method = new Method();
        method.setName(METHOD_NAME);
        rest.setMethod(method);
        rest.setAction(METHOD_GET);
        rest.setPath("test");

        List<Rest> rests = new ArrayList<>();
        rests.add(rest);
        controller1.setRests(rests);
        controllers.add(controller1);

        ConfigurationManager.INSTANCE.saveControllersConfiguration(controllers);
    }

    @Test(expected = ConfigurationException.class)
    public void getRestResponseWrongPath() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ConfigurationManager.INSTANCE.getRestResponse(request, "123", "123");
    }

    @Test(expected = ConfigurationException.class)
    public void getRestResponseWrongHttpMethod() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ConfigurationManager.INSTANCE.getRestResponse(request, "test", "test");
    }

    @Test
    public void getRestResponseNullRestResponse() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String restResponse = ConfigurationManager.INSTANCE.getRestResponse(request, "test", METHOD_GET);
        assertEquals(RESPONSE_GET_METHOD, restResponse);
    }

    @Test
    public void getRestResponseNotNullRestResponse() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ConfigurationManager.INSTANCE.getRestResponse(request, "test", METHOD_GET);
        String restResponse = ConfigurationManager.INSTANCE.getRestResponse(request, "test", METHOD_GET);
        assertEquals(RESPONSE_GET_METHOD, restResponse);
    }
}