package ua.com.as.service.jaxb;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.jaxb.AutoScan;
import ua.com.as.jaxb.Configuration;
import ua.com.as.jaxb.Controller;

import java.util.ArrayList;

/**
 *
 */
public class ConfigurationTest {

    @Test
    public void testSetAndGetAutoScan() {
        Configuration configuration = new Configuration();
        configuration.setAutoScan(new AutoScan());
        Assert.assertNotNull(configuration.getAutoScan());
    }

    @Test
    public void testSetAndGetControllers() {
        Configuration configuration = new Configuration();
        configuration.setControllers(new ArrayList<>());
        Assert.assertNotNull(configuration.getControllers());
    }
}
