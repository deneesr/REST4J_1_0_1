package ua.com.as.service.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.ConfigurationException;

/**
 *
 */
public class ConfigurationExceptionTest {

    private static final String MESSAGE = "HELLO ConfigurationException";

    @Test
    public void testConstructsWithOneParameter() {
        new ConfigurationException(MESSAGE);
    }

    @Test
    public void testConstructsWithTwoParameter() {
        new ConfigurationException(MESSAGE, new Throwable());
    }
}
