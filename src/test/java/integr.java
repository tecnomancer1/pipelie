import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class MyIntegrationTest {
    @Test
    public void testDatabaseConnection() {
        // Test database connection and assert true if successful
        assertTrue(Database.connect());
    }
}
