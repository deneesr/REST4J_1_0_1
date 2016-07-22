package ua.com.as.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.ConfigurationException;
import ua.com.as.jaxb.Controller;
import ua.com.as.jaxb.Rest;
import ua.com.as.model.RestDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton for ConfigurationManager.
 */
enum ConfigurationManager {
    /**
     * name of singleton.
     */
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);
    private Map<String, Map<String, RestDefinition>> paths;

    ConfigurationManager() {
        paths = new HashMap<>();
    }

    /**
     * Method allows check if INSTANCE null and create INSTANCE of controllers.
     *
     * @param controllers data about controllers.
     */
    public void saveControllersConfiguration(List<Controller> controllers) {
        for (Controller controller : controllers) {
            for (Rest rest : controller.getRests()) {
                Map<String, RestDefinition> actionExecutionMap = paths.get(rest.getPath());
                if (actionExecutionMap == null) {
                    actionExecutionMap = new HashMap<>();
                    paths.put(rest.getPath(), actionExecutionMap);
                }
                RestDefinition restDefinition = new RestDefinition(controller.getFullClassName(),
                        rest.getMethod().getName());
                actionExecutionMap.put(rest.getAction(), restDefinition);
            }
        }
    }

    /**
     * <p>Gets String response from invoked method with specified parameters.
     *
     * @param request        parameter of method to invoke
     * @param path           name of class which method will be invoked
     * @param httpMethodName method name to invoke
     * @return String result of invoking
     */
    public String getRestResponse(HttpServletRequest request, String path, String httpMethodName) {
        RestDefinition restDefinition = getRestDefinition(path, httpMethodName);
        if (restDefinition.getRestResponse() == null) {
            Execution execution = new ExecutionImpl();
            String restResponse = execution
                    .getRestResponse(restDefinition.getFullClassName(), restDefinition.getMethodName(), request);
            LOG.debug("Response received");

            restDefinition.setRestResponse(restResponse);
            return restResponse;
        } else {
            String restResponse = restDefinition.getRestResponse();
            LOG.debug("Response received");
            return restResponse;
        }
    }

    /**
     * Method allows to get execution by path and httpMethodName.
     *
     * @param path           - rest;
     * @param httpMethodName - http method;
     * @return execution ;
     */
    private RestDefinition getRestDefinition(String path, String httpMethodName) {
        LOG.debug("Try to find restDefinition with path " + path + " and httpMethodName " + httpMethodName);
        Map<String, RestDefinition> restDefinitionMap = paths.get(path);
        if (restDefinitionMap == null) {
            throw new ConfigurationException("No actions for such path");
        }

        RestDefinition restDefinition = restDefinitionMap.get(httpMethodName);
        if (restDefinition == null) {
            throw new ConfigurationException("No executions for such path and httpMethodName");
        }

        LOG.debug("RestDefinition is found");
        return restDefinition;
    }
}
