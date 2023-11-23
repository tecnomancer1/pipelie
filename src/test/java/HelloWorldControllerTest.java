import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloWorldEndpoint() throws Exception {
        // Perform a GET request to the endpoint "/"
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                // Expect a status code of 200 (OK)
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Expect the response body to contain "Hello World!"
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    }
}
