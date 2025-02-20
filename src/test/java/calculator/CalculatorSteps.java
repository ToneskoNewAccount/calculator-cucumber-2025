package calculator;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import visitor.Printer;

import java.util.ArrayList;
import java.util.List;

public class CalculatorSteps {
	private ArrayList<Expression> params;
	private Operation op;
	private Calculator c;

	@Before
	public void resetMemoryBeforeEachScenario() {
		params = null;
		op = null;
	}

	@Given("I initialise a calculator")
	public void givenIInitialiseACalculator() {
		c = new Calculator();
	}

	@Given("an double operation {string}")
	public void givenADoubleOperation(String s) {
		// Write code here that turns the phrase above into concrete actions
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

	// The following example shows how to use a DataTable provided as input.
	// The example looks slightly complex, since DataTables can take as input
	//  tables in two dimensions, i.e. rows and lines. This is why the input
	//  is a list of lists.
	@Given("the following list of double numbers")
	public void givenTheFollowingListOfNumbers(List<List<String>> numbers) {
		params = new ArrayList<>();
		// Since we only use one line of input, we use get(0) to take the first line of the list,
		// which is a list of strings, that we will manually convert to double:
		numbers.get(0).forEach(n -> params.add(new MyNumber(Double.parseDouble(n))));
		params.forEach(n -> System.out.println("value =" + n));
		op = null;
	}

	// The string in the Given annotation shows how to use regular expressions...
	// In this example, the notation d+ is used to represent numbers, i.e. nonempty sequences of digits
	@Given("the sum of two numbers {double} and {double}")
	public void givenTheSum(double n1, double n2) {
		try {
			params = new ArrayList<>();
			params.add(new MyNumber(n1));
			params.add(new MyNumber(n2));
			op = new Plus(params);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Then("^its (.*) notation is (.*)$")
	public void thenItsNotationIs(String notation, String s) {
		if (notation.equals("PREFIX") || notation.equals("POSTFIX") || notation.equals("INFIX")) {
			Printer p = new Printer(Notation.valueOf(notation));
			op.accept(p);
			assertEquals(s, p.getResult());
		} 
    else fail(notation + " is not a correct notation! ");
	}

	@When("^I provide a (.*) number (-?\\d+(?:\\.\\d+)?)$")
	public void whenIProvideANumber(String s, double val) {
		//add extra parameter to the operation
		params = new ArrayList<>();
		params.add(new MyNumber(val));
		op.addMoreParams(params);
	}

	@Then("^the (.*) is (-?\\d+(?:\\.\\d+)?)$")
	public void thenTheOperationIs(String s, double val) {
		try {
			switch (s) {
				case "sum" -> op = new Plus(params);
				case "product" -> op = new Times(params);
				case "quotient" -> op = new Divides(params);
				case "difference" -> op = new Minus(params);
				default -> fail();
			}
			assertEquals(val, c.eval(op));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Then("the operation evaluates to {double}")
	public void thenTheOperationEvaluatesTo(double val) {
		assertEquals(val, c.eval(op), 0.0001);
	}

	@Then("the operation evaluates to NaN")
	public void thenTheOperationEvaluatesToNan() {
		assertEquals(Double.NaN, c.eval(op), 0.0001);
	}
}


