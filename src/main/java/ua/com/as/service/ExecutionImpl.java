package ua.com.as.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.util.ReflectionHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * The {@code ExecutionImpl} class invoke required method in client's controller.
 */
class ExecutionImpl implements Execution {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutionImpl.class);

    /**
     * <p>Gets String response from invoked method with specified parameters.
     *
     * @param controllerName name of class which method will be invoked
     * @param methodName     method name to invoke
     * @param request        parameter of method to invoke
     * @return String result of invoking
     */
    @Override
    public String getRestResponse(String controllerName, String methodName, HttpServletRequest request) {
        Class controllerClass = ReflectionHelper.createClass(controllerName, getClass().getClassLoader());
        LOG.debug("Controller created: {}", controllerClass.getCanonicalName());

        Method methodToInvoke = ReflectionHelper
                .createMethod(controllerClass, methodName, HttpServletRequest.class);
        LOG.debug("Method created: {}", methodName);

        return ReflectionHelper.invoke(controllerClass, methodToInvoke, request);
    }
}
