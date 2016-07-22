package ua.com.as.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.jaxb.Controller;
import ua.com.as.parsing.PackageScanner;
import ua.com.as.parsing.PackageScannerImpl;
import ua.com.as.parsing.XmlParser;
import ua.com.as.parsing.XmlParserImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;


/**
 * <p>Class represents 'server-controller-mapper'
 * which manages processes of parsing, scanning and getting required result.
 */
public class HandlerMapperImpl implements HandlerMapper {

    private static final Logger LOG = LoggerFactory.getLogger(HandlerMapperImpl.class);

    /**
     * <p>Parse xml file, scan specified packages and prepare entities-structure
     * for working with clients' controllers.
     *
     * @param configFile file to parse in xml parser
     */
    @Override
    public void configure(File configFile) {
        XmlParser parser = new XmlParserImpl(configFile);

        List<Controller> parsedControllers = parser.getControllers();
        List<String> packagesToScan = parser.getPackagesToScan();

        PackageScanner scanner = new PackageScannerImpl();
        List<Controller> scannedControllers = scanner.scan(packagesToScan);

        ConfigurationManager.INSTANCE.saveControllersConfiguration(scannedControllers);
        ConfigurationManager.INSTANCE.saveControllersConfiguration(parsedControllers);
    }

    /**
     * <p>Gets String response from invoked method with specified parameters.
     *
     * @param request        parameter of method to invoke
     * @param path           name of class which method will be invoked
     * @param httpMethodName method name to invoke
     * @return String result of invoking
     */
    @Override
    public String getRestResponse(HttpServletRequest request, String path, String httpMethodName) {
        LOG.debug("Rest response is requested");
        String response = ConfigurationManager.INSTANCE.getRestResponse(request, path, httpMethodName);
        LOG.debug("Rest response is received");
        return response;
    }
}
