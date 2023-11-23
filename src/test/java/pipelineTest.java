import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;

public class pipelineTest {

    @Test
    public void testHelloWorldEndpoint() throws Exception {
        HelloWorldController helloWorldController = new HelloWorldController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andReturn().getResponse();

        assertEquals("Hello World!", response.getContentAsString());
        assertEquals(200, response.getStatus());
    }
}
