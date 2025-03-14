package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import calculator.Calculator;
import calculator.Expression;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import parser.Parser;
import parser.exceptions.ParsingException;

public class ParserSteps {
    private String input;
    private Parser parser;
    private Calculator calculator;
    private Expression expression;
    private Exception exception;

    @Before
    public void resetMemoryBeforeEachScenario() {
        expression = null;
        input = null;
        exception = null;
    }

    @Given("I initialise a parser")
    public void givenIInitialiseAParser() {
        parser = new Parser();
    }

    @Given("I initialise a calculator")
    public void givenIInitialiseACalculator() {
        calculator = new Calculator();
    }

    @Given("an expression {string}")
    public void givenAnExpression(String input) {
        this.input = input;
    }

    // This is required since Cucumber does not parse the string with the escape characters.
    @Given("an expression with whitespaces {string}")
    public void givenAnExpressionWithWhitespaces(String input) {
        this.input = input.replace("\\n", "\n")
            .replace("\\t", "\t")
            .replace("\\r", "\r");
    }

    @When("I parse the expression")
    public void whenIParseTheExpression() {
        try {
            expression = parser.parse(input);
        } catch (ParsingException e) {
            exception = e;
        }
    }

    @Then("the result should be {double}")
    public void thenTheResultShouldBe(double expected) {
        double result = calculator.eval(expression);
        assertEquals(expected, result, 0.0001);
    }

    @Then("the result should be an error")
    public void thenTheResultShouldBeAnError() {
        assertNotNull(exception, "Expected an error, but no exception was thrown");
    }
}
