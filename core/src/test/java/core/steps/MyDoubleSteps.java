package core.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import core.number.MyDouble;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyDoubleSteps {

    @When("I provide the following list of double numbers")
    public void givenTheFollowingListOfNumbers(List<List<String>> numbers) {
        CommonSteps.params = new ArrayList<>();
        numbers.get(0).forEach(n -> CommonSteps.params.add(new MyDouble(Double.parseDouble(n))));
        CommonSteps.op = null;
    }

    @When("I provide a double number {double}")
    public void whenIProvideADoubleNumber(double val) {
        CommonSteps.addParam(new MyDouble(val));
    }

    @Then("the operation evaluates to a double {double}")
    public void thenTheOperationEvaluatesTo(double val) {
        MyDouble other = (MyDouble) CommonSteps.c.eval(CommonSteps.op);
        assertEquals(val, other.getDoubleValue(), 1e-9);
    }

    @Then("the operation evaluates to a double NaN")
    public void thenTheOperationEvaluatesToNan() {
        MyDouble other = (MyDouble) CommonSteps.c.eval(CommonSteps.op);
        assertEquals(Double.NaN, other.getDoubleValue());
    }
}
