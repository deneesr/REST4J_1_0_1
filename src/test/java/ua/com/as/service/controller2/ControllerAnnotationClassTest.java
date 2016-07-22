package ua.com.as.service.controller2;

import ua.com.as.annotation.Controller;
import ua.com.as.annotation.Path;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class ControllerAnnotationClassTest {

    @Path(value = "/postTest", methods = {"GET", "POST"})
    public String postTest(HttpServletRequest request) {
        return "Hello GET POST";
    }
}
