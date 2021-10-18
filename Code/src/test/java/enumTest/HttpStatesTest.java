package enumTest;

import com.dza.dserver.reponse.HttpStates;
import org.junit.Test;

public class HttpStatesTest {

    @Test
    public void testValue(){
        System.out.println(HttpStates.BAD_REQUEST.getName());
    }
}
