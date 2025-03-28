package calculator;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ComplexSteps {
    private Operation op;
    private ArrayList<Expression> params;
    private Calculator c;

    @Before
    public void resetMemoryBeforeEachScenario() {
        params = null;
        op = null;
    }

    @Given("A complex calculator")
    public void givenACal() {
        c = new Calculator();
    }

    @Given("a complex operation {string}")
    public void givenAnOp(String s) {
        params = new ArrayList<>();
        try {
            switch (s) {
                case "+" -> op = new Plus(params);
                case "-" -> op = new Minus(params);
                case "*" -> op = new Times(params);
                case "/" -> op = new Divides(params);
                default -> fail();
            }
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @When("^I provide a complex number \\(([-\\d.]+);([-\\d.]+)i\\)$")
    public void whenAComplexNumber(double real, double imaginary) {
        params = new ArrayList<>();
        params.add(MyComplexNumber.createComplexNumber(new MyDouble(real), new MyDouble(imaginary)));
        op.addMoreParams(params);
    }

    @When("I provide a real number {double}")
    public void whenARealNumber(double val) {
        params = new ArrayList<>();
        params.add(new MyDouble(val));
        op.addMoreParams(params);
    }

    @Then("^The result should be \\(([-\\d.]+);([-\\d.]+)i\\)$")
    public void theResultShouldBe(double res_real, double res_imag) {
        MyComplexNumber res = (MyComplexNumber) c.eval(op);
        assertEquals(res_real, res.getReal().getDoubleValue(), 1e-5);
        assertEquals(res_imag, res.getImag().getDoubleValue(), 1e-5);
    }
}