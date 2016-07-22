package ua.com.as.service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;

/**
 * <p>Interface provides api for class that represents 'server-controller-mapper'
 * which manages processes of parsing, scanning and getting required result.
 */
public interface HandlerMapper extends Serializable {

    /**
     * <p>Parse xml file, scan specified packages and prepare entities-structure
     * for working with clients' controllers.
     *
     * @param configFile file to parse in xml parser
     */
    void configure(File configFile);

    /**
     * <p>Gets String response from invoked method with specified parameters.
     *
     * @param request        parameter of method to invoke
     * @param path           name of class which method will be invoked
     * @param httpMethodName method name to invoke
     * @return String result of invoking
     */
    String getRestResponse(HttpServletRequest request, String path, String httpMethodName);
}
