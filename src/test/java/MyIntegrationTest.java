import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testHelloWorldEndpoint() {
        // Make an HTTP GET request to the /hello endpoint
        String response = restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);

        // Assert that the response is "Hello, World!"
        assertEquals("Hello, World!", response);
    }
}
