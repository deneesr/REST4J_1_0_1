package ua.com.as.parsing;

import ua.com.as.jaxb.Controller;

import java.util.List;

/**
 * <p><code>XmlParser</code> interface provides base api for parsing xml files.
 */
public interface XmlParser {

    /**
     * <p>Get controllers from xml file.
     *
     * @return list of controllers
     */
    List<Controller> getControllers();

    /**
     * <p>Get packages to scan from xml file.
     *
     * @return list of package names
     */
    List<String> getPackagesToScan();
}
