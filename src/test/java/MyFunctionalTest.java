import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldFunctionalTest {

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testHelloWorldEndpoint() {
        // Make an HTTP GET request to the root endpoint
        String response = restTemplate.getForObject(getBaseUrl() + "/", String.class);

        // Assert that the response is "Hello World!"
        assertEquals("Hello World!", response);
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + contextPath;
    }
}
