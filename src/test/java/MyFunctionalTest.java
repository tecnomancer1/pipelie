import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyFunctionalTest {
    @Test
    public void testLoginPage() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/login");
        driver.findElement(By.id("username")).sendKeys("testuser");
        driver.findElement(By.id("password")).sendKeys("testpassword");
        driver.findElement(By.id("loginButton")).click();
        // Add assertions based on expected behavior
        driver.quit();
    }
}
