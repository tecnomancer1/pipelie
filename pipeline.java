import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class pipeline {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }
}

@RestController
class CalculatorController {

    @GetMapping("/add")
    public String add(@RequestParam int num1, @RequestParam int num2) {
        return String.valueOf(num1 + num2);
    }

    @GetMapping("/subtract")
    public String subtract(@RequestParam int num1, @RequestParam int num2) {
        return String.valueOf(num1 - num2);
    }

    @GetMapping("/multiply")
    public String multiply(@RequestParam int num1, @RequestParam int num2) {
        return String.valueOf(num1 * num2);
    }

    @GetMapping("/divide")
    public String divide(@RequestParam int num1, @RequestParam int num2) {
        if (num2 != 0) {
            return String.valueOf((double) num1 / num2);
        } else {
            return "Cannot divide by zero!";
        }
    }
}
