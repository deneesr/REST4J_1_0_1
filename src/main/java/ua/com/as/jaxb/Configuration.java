package ua.com.as.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Configuration objects that need to convert from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Configuration {

    @XmlElement(name = "auto-scan")
    private AutoScan autoScan;

    @XmlElement(name = "controller")
    private List<Controller> controllers;

    /**
     * @return return.
     */
    public AutoScan getAutoScan() {
        return autoScan;
    }

    /**
     * @param autoScan autoScan.
     */
    public void setAutoScan(AutoScan autoScan) {
        this.autoScan = autoScan;
    }

    /**
     * Getter.
     *
     * @return list of controller.
     */
    public List<Controller> getControllers() {
        return controllers;
    }

    /**
     * Setter.
     *
     * @param controllers set new parameter.
     */
    public void setControllers(List<Controller> controllers) {
        this.controllers = controllers;
    }
}
