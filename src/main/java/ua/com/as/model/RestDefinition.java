package ua.com.as.model;

import java.util.Objects;

/**
 * <p><code>RestDefinition</code> represents an entity with specified fields for invoking required method
 * via <code>path</code> and <code>http method name</code>.
 */
public class RestDefinition {
    private final String fullClassName;
    private final String methodName;
    private String restResponse;

    /**
     * <p>Class constructor.
     *
     * @param fullClassName - name of package;
     * @param methodName   - name of method;
     */
    public RestDefinition(String fullClassName, String methodName) {
        this.fullClassName = fullClassName;
        this.methodName = methodName;
    }

    /**
     * Getter.
     *
     * @return class package;
     */
    public String getFullClassName() {
        return fullClassName;
    }

    /**
     * Getter.
     *
     * @return method name;
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Getter.
     *
     * @return rest response;
     */
    public String getRestResponse() {
        return restResponse;
    }

    /**
     * Setter.
     *
     * @param restResponse rest response;
     */
    public void setRestResponse(String restResponse) {
        this.restResponse = restResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestDefinition that = (RestDefinition) o;
        return Objects.equals(fullClassName, that.fullClassName) &&
                Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullClassName, methodName);
    }
}
