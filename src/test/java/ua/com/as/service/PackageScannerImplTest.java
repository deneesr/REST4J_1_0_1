package ua.com.as.service;

import org.junit.Before;
import org.junit.Test;
import ua.com.as.jaxb.Controller;
import ua.com.as.parsing.PackageScanner;
import ua.com.as.parsing.PackageScannerImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PackageScannerImplTest {

    private PackageScanner ps;
    private List<String> packagesToScan;

    @Before
    public void init() {
        ps = new PackageScannerImpl();
        packagesToScan = new ArrayList<>();
    }

    @Test
    public void testScan() {
        packagesToScan.add("ua.com.as.service.controller1");
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(1, paths.size());
    }

    @Test
    public void testScanTwoPackages() {
        packagesToScan.add("ua.com.as.service.controller1");
        packagesToScan.add("ua.com.as.service.controller2");
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(2, paths.size());
    }

    @Test
    public void testScanTwoPackagesWithSpaces() {
        packagesToScan.add("  ua.com.as.service.controller1 ");
        packagesToScan.add(" ua.com.as.service.controller2");
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(2, paths.size());
    }

    @Test
    public void testScanClassWithoutPathMethods() {
        packagesToScan.add("ua.com.as.service.controller3");
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(0, paths.size());
    }

    @Test
    public void testScanWithEmptyClass() {
        packagesToScan.add("ua.com.as.service.controller2");
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(1, paths.size());
    }

    @Test(expected = AssertionError.class)
    public void testScanWithNull() {
        packagesToScan.add(null);
        packagesToScan.add(null);
        packagesToScan.add(null);
        ps.scan(packagesToScan);
    }

    @Test(expected = AssertionError.class)
    public void testScanNonexistentPath() {
        packagesToScan.add("ua.com.as.service.controller4");
        ps.scan(packagesToScan);
    }

    @Test(expected = InvocationTargetException.class)
    public void testJarError() throws Exception {
        Class<?> aClass = Class.forName("ua.com.as.parsing.PackageScannerImpl");
        Method processJar = aClass.getDeclaredMethod("processJar", String.class);
        processJar.setAccessible(true);
        processJar.invoke(aClass.newInstance(),
                "C:/mrserfr/java/projects/alliance-systems/practice2rest/src/test/resources/testjar.jar");
    }
}
