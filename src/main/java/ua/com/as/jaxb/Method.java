package ua.com.as.jaxb;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Method object that need to convert from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Method {

    @XmlAttribute
    private String name;

    /**
     * Getter.
     * @return name of method.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     * @param name set new parameter.
     */
    public void setName(String name) {
        this.name = name;
    }
}
