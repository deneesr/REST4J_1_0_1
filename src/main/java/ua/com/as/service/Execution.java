package ua.com.as.service;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@code Execution} interface provides for getting String result from method in client's controller.
 */
interface Execution {

    /**
     * <p>Gets String response from invoked method with specified parameters.
     *
     * @param controllerName name of class which method will be invoked
     * @param methodName     method name to invoke
     * @param request        parameter of method to invoke
     * @return String result of invoking
     */
    String getRestResponse(String controllerName, String methodName, HttpServletRequest request);
}
