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
<<<<<<< HEAD
<<<<<<< HEAD
       System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
=======
       System.setProperty("webdriver.chrome.driver", "///usr///local///bin///chromedriver");
>>>>>>> parent of fca32b9 (Update HelloWorldFunctional.java)
=======
       System.setProperty("webdriver.chrome.driver", "//Users//arkad//Downloads//chromedriver");
>>>>>>> parent of a1c5f81 (Update HelloWorldFunctional.java)
      // driver = new ChromeDriver();
       WebDriver driver= new ChromeDriver(); driver.get("https://www.google.com");
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
