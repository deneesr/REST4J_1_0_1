package ua.com.as.jaxb;


import javax.xml.bind.annotation.*;

/**
 * Rest object that need to convert from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rest {

    @XmlAttribute
    private String path;
    @XmlAttribute
    private String action;
    @XmlElement
    private Method method;

    /**
     * Getter.
     * @return path of path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter.
     * @param path set new parameter.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter.
     * @return action of path.
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter.
     * @param action set new parameter.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter.
     * @return method.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Setter.
     * @param method set new parameter.
     */
    public void setMethod(Method method) {
        this.method = method;
    }
}
