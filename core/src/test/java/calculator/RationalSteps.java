package calculator;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RationalSteps {
    private Operation op;
    private ArrayList<Expression> params;
    private Calculator c;


    @Before
    public void resetMemoryBeforeEachScenario() {
        params = null;
        op = null;
    }

    @Given("A calculator")
    public void givenACal() {
        c = new Calculator();
    }

    @Given("a rational operation {string}")
    public void givenAnOp(String s) {
        //Write code here that turns the phrase above into concrete actions
        params = new ArrayList<>(); // create an empty set of parameters to be filled in
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

    @When("^I provide a rational number (-?\\d+)/(-?\\d+)$")
    public void when_a_rational_number(int numerator, int denominator) {
        params = new ArrayList<>();
        params.add(MyRationalNumber.createRationalNumber(numerator, denominator));
        op.addMoreParams(params);
    }

    @When("I provide an integer number {int}")
    public void whenAnInt(int val) {
        params = new ArrayList<>();
        params.add(new MyInt(val));
        op.addMoreParams(params);
    }

    @When("I provide a double number {double}")
    public void whenADouble(double val) {
        params = new ArrayList<>();
        params.add(new MyDouble(val));
        op.addMoreParams(params);
    }

    @Then("^The result should be (-?\\d+)/(-?\\d+)")
    public void theResultShouldBe(int res_num, int res_den) {
        MyRationalNumber res_rat = (MyRationalNumber) c.eval(op);
        assertEquals(res_num, res_rat.getNumerator());
        assertEquals( res_den, res_rat.getDenominator());
    }

    @Then("The result should be a double {double}")
    public void theResultShouldBe(double exp_res) {
        MyDouble res = (MyDouble) c.eval(op);
        assertEquals(exp_res, res.getDoubleValue(), 1e-9);
    }

    @Then("The result should be integer {int}")
    public void theResultShouldBe(int exp_res) {
        MyInt res = (MyInt) c.eval(op);
        assertEquals(exp_res, res.getIntValue());
    }

    @Then("A Double NaN should be construct")
    public void aDoubleNanShouldBeConstruct() {
        MyDouble res = (MyDouble) c.eval(op);
        assert res.getDoubleValue().isNaN();
    }

}

