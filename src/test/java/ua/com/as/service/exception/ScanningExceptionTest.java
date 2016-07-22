package ua.com.as.service.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.ScanningException;

/**
 *
 */
public class ScanningExceptionTest {

    private static final String MESSAGE = "HELLO ScanningException";

    @Test
    public void testConstructsWithOneParameter() {
        new ScanningException(MESSAGE);
    }

    @Test
    public void testConstructsWithTwoParameter() {
        new ScanningException(MESSAGE, new Throwable());
    }
}
