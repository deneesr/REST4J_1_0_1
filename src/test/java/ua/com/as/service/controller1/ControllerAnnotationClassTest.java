package ua.com.as.service.controller1;

import ua.com.as.annotation.Controller;
import ua.com.as.annotation.Path;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class ControllerAnnotationClassTest {

    @Path(value = "/getTest", methods = {"GET"})
    public String getTest(HttpServletRequest request) {
        return "Hello GET";
    }
}
