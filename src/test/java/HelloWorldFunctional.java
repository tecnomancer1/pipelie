import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldFunctional {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Set up WebDriver (assuming ChromeDriver)
       System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
       ChromeOptions options = new ChromeOptions();
       options.addArguments("--no-sandbox");
       driver = new ChromeDriver(options);  // Assign to the class variable instead of creating a new local variable
    }

    @Test
    public void testHelloWorld() {
        // Navigate to the application URL
        driver.get("http://localhost:8080/");

        // Perform assertions
        String message = driver.findElement(By.tagName("body")).getText();
        assertEquals("Hello World!", message);
    }

    @AfterAll
    public static void tearDown() {
        // Close the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }
}
