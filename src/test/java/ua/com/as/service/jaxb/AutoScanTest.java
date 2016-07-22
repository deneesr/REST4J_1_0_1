package ua.com.as.service.jaxb;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.jaxb.AutoScan;

/**
 *
 */
public class AutoScanTest {

    private static final String PACKAGES = "ua.com.as.service.controller1, ua.com.as.service.controller2";

    @Test
    public void testSetAndGetPackages() {
        AutoScan autoScan = new AutoScan();
        autoScan.setPackages(PACKAGES);
        Assert.assertEquals(PACKAGES, autoScan.getPackages());
    }
}
