package ua.com.as.parsing;

import ua.com.as.jaxb.Controller;

import java.util.List;

/**
 * <p>Provides simple api to scan packages for <code>classes</code>
 * which have {@link ua.com.as.annotation.Controller} annotation,
 * find there <code>methods</code> which have {@link ua.com.as.annotation.Path}
 * annotation and group them into {@link Controller} entities.
 *
 * @see PackageScannerImpl
 * @see XmlParser
 */
public interface PackageScanner {
    /**
     * <p>Scans specified packages.
     *
     * @param packagesToScan packages to scan.
     * @return list of <code>Controllers</code> to update paths in ConfigurationManager
     */
    List<Controller> scan(List<String> packagesToScan);
}
