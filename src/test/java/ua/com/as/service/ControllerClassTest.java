package ua.com.as.service;

import javax.servlet.http.HttpServletRequest;

public class ControllerClassTest {
    public String getTest(HttpServletRequest request) {
        return "ControllerClassTest, method: getTest";
    }

    public String postTest(HttpServletRequest request) {
        return "ControllerClassTest, method: postTest";
    }

    public String putTest(HttpServletRequest request) {
        return "ControllerClassTest, method: putTest";
    }

    public String deleteTest(HttpServletRequest request) {
        return "ControllerClassTest, method: deleteTest";
    }
}