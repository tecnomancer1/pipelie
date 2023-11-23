import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class pipelineTest {

    @Test
    public void helloWorldEndpointShouldReturnHelloWorld() {
        pipeline application = new pipeline();
        HelloWorldController controller = new HelloWorldController();
        String result = controller.helloWorld();
        assertEquals("Hello World!", result);
    }
}
