package ServerTest;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GetInstanceTest {
    @Test
    public void getInstance() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Object o = Class.forName("com.dza.dserver.server.bio.BioServer")
                .getConstructor(int.class)
                .newInstance(9090);
        System.out.println(o);

    }
}
