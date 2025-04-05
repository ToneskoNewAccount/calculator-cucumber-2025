package core.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import core.number.MyRationalNumber;

public class RationalSteps {

    @When("^I provide a rational number (-?\\d+)/(-?\\d+)$")
    public void when_a_rational_number(int numerator, int denominator) {
        CommonSteps.addParam(MyRationalNumber.createRationalNumber(numerator, denominator));
    }

    @Then("^the operation evaluates to a rational (-?\\d+)/(-?\\d+)")
    public void theResultShouldBe(int res_num, int res_den) {
        CommonSteps.operationEvaluation(MyRationalNumber.createRationalNumber(res_num, res_den));
    }
}
