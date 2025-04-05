package core.steps;

import java.util.ArrayList;
import java.util.List;

import core.number.MyInt;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyIntSteps {

    @When("I provide the following list of int numbers")
    public void givenTheFollowingListOfNumbers(List<List<String>> numbers) {
        CommonSteps.params = new ArrayList<>();
        numbers.get(0).forEach(n -> CommonSteps.params.add(new MyInt(Integer.parseInt(n))));
        CommonSteps.op = null;
    }

    @When("I provide an integer number {int}")
    public void whenIProvideAnIntegerNumber(int val) {
        CommonSteps.addParam(new MyInt(val));
    }

    @Then("the operation evaluates to an integer {int}")
    public void thenTheOperationEvaluatesTo(int val) {
        CommonSteps.operationEvaluation(new MyInt(val));
    }
}
