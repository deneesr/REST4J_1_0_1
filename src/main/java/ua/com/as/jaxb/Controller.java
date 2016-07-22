package ua.com.as.jaxb;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Controller object that need to convert from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Controller {

    @XmlAttribute
    private String fullClassName;

    @XmlElement(name = "rest")
    private List<Rest> rests;

    /**
     * Getter.
     * @return fullClassName.
     */
    public String getFullClassName() {
        return fullClassName;
    }

    /**
     *  Setter.
     * @param fullClassName set new fullClassName.
     */
    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    /**
     *Getter.
     * @return list of rest methods.
     */
    public List<Rest> getRests() {
        return rests;
    }

    /**
     * Setter.
     * @param rests set new list.
     */
    public void setRests(List<Rest> rests) {
        this.rests = rests;
    }
}
