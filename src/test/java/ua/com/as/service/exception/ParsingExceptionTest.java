package ua.com.as.service.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.exception.ParsingException;

/**
 *
 */
public class ParsingExceptionTest {

    private static final String MESSAGE = "HELLO ParsingException";

    @Test
    public void testConstructsWithOneParameter() {
        new ParsingException(MESSAGE);
    }

    @Test
    public void testConstructsWithTwoParameter() {
        new ParsingException(MESSAGE, new Throwable());
    }
}
