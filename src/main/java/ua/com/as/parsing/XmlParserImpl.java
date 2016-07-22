package ua.com.as.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.ParsingException;
import ua.com.as.jaxb.Configuration;
import ua.com.as.jaxb.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Class present method for parsing xml file.
 */
public class XmlParserImpl implements XmlParser {

    private static final Logger LOG = LoggerFactory.getLogger(XmlParserImpl.class);

    private File configFile;
    private Configuration configuration;

    /**
     * <p>Class constructor.
     *
     * @param file xml file to parse
     */
    public XmlParserImpl(File file) {
        configFile = file;
        LOG.debug("File configFile: {}", configFile);
        parseXml();
    }

    /**
     * <p>Prepare and parse xml file.
     */
    private void parseXml() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            LOG.debug("Parsing started");
            Configuration conf = (Configuration) jaxbUnmarshaller.unmarshal(configFile);
            if (conf != null) {
                this.configuration = conf;
                LOG.debug("Parsing finished successfully! AutoScan and Controllers is not empty\n");
            } else {
                LOG.error("Parsing finished with error: wrong format xml-file.");
            }

        } catch (JAXBException e) {
            LOG.error("Parsing finished with error!");
            throw new ParsingException("parsing error", e);
        }
    }

    /**
     * <p>Get controllers from xml file.
     *
     * @return list of controllers
     */
    @Override
    public List<Controller> getControllers() {
        if (configuration.getControllers() != null) {
            return configuration.getControllers();
        }
        LOG.error("List Controllers is empty!");
        return Collections.emptyList();
    }

    /**
     * <p>Get packages to scan from xml file.
     *
     * @return list of package names
     */
    @Override
    public List<String> getPackagesToScan() {
        if (configuration.getAutoScan() != null) {
            String[] strings = configuration.getAutoScan().getPackages().split(",");
            return Arrays.asList(strings);
        }
        LOG.error("List PackagesToScan is empty!");
        return Collections.emptyList();
    }
}
