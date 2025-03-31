package controller;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.MyNumber;
import calculator.Plus;
import controller.request.PlusRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api") // Base path for the controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot is running!";
    }

    @PostMapping("/plus")
    public double plus(@RequestBody PlusRequest plusRequest) {
        // Compare strings using equals rather than "=="
        if ("sum".equals(plusRequest.getOperation())) {
            double[] numbers = plusRequest.getNumbers();
            // Check that the array is not empty
            if (numbers == null || numbers.length == 0) {
                return 0;
            }
            // If only one number is provided, return it directly
            if (numbers.length == 1) {
                return numbers[0];
            }

            // Iterative addition: start with the first number and add the subsequent numbers
            double result = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                try {
                    // For each addition, create a list of two expressions: the current sum and the next number
                    List<Expression> params = new ArrayList<>();
                    params.add(new MyNumber(result));
                    params.add(new MyNumber(numbers[i]));
                    Plus op = new Plus(params);
                    result = op.op(result, numbers[i]);
                } catch (IllegalConstruction e) {
                    // In case of error during construction, return 0 (you can adjust error handling if necessary)
                    return 0;
                }
            }
            return result;
        }
        // Default value if the operation does not match
        return 0;
    }
}
