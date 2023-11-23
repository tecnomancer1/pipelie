import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloWorldControllerTest {

    @Test
    public void helloWorldEndpointShouldReturnHelloWorld() {
        HelloWorldController controller = new HelloWorldController();
        String result = controller.helloWorld();
        assertEquals("Hello World!", result);
    }
}
