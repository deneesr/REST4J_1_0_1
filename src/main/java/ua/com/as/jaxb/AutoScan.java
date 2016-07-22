package ua.com.as.jaxb;

import javax.xml.bind.annotation.*;

/**
 * Configuration object that need to be converted from xml file
 * to get list of packages to scan.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoScan {

    @XmlAttribute
    private String packages;

    /**
     * @return packages to scan.
     */
    public String getPackages() {
        return packages;
    }

    /**
     * @param packages set packages to scan.
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
}
