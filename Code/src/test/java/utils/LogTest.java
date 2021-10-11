package utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogTest {
    public static Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public  void testLog() {
        logger.info("this is info");
        logger.debug("this is debug");
        logger.error("this is error");
    }
}
