package utils;

import org.junit.Test;

public class PropertyUtilTest {

    @Test
    public void testPortRead(){
        final Integer port = PropertyUtil.getPort();
    }

    public static void main(String[] args) {
        final Integer port = PropertyUtil.getPort();

    }
}
