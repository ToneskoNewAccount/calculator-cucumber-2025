package core.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import core.number.MyRationalNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RationalSteps {

    @When("^I provide a rational number (-?\\d+)/(-?\\d+)$")
    public void when_a_rational_number(int numerator, int denominator) {
        CommonSteps.addParam(MyRationalNumber.createRationalNumber(numerator, denominator));
    }

    @Then("^the operation evaluates to a rational (-?\\d+)/(-?\\d+)")
    public void theResultShouldBe(int res_num, int res_den) {
        MyRationalNumber res_rat = (MyRationalNumber) CommonSteps.c.eval(CommonSteps.op);
        assertEquals(res_num, res_rat.getNumerator());
        assertEquals( res_den, res_rat.getDenominator());
    }
}
