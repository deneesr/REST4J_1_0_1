package ua.com.as.service.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.InvocationException;

/**
 *
 */
public class InvocationExceptionTest {

    private static final String MESSAGE = "HELLO InvocationException";

    @Test
    public void testConstructsWithTwoParameter() {
        new InvocationException(MESSAGE, new Throwable());
    }
}
