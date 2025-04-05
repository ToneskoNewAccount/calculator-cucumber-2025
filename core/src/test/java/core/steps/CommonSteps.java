package core.steps;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import core.Calculator;
import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyDouble;
import core.number.MyInt;
import core.number.MyNumber;
import core.number.MyRationalNumber;
import core.operation.Divides;
import core.operation.Minus;
import core.operation.Operation;
import core.operation.Plus;
import core.operation.Times;
import core.visitor.Notation;
import core.visitor.Printer;

import java.util.ArrayList;

public class CommonSteps {
    protected static ArrayList<Expression> params;
    protected static Operation op;
    protected static Calculator c;

    @Before
    public void resetMemoryBeforeEachScenario() {
        params = null;
        op = null;
    }

    @Given("I initialize a calculator")
    public void givenIInitializeACalculator() {
        c = new Calculator();
    }

    @Given("an operation {string}")
    public void givenAnOperation(String s) {
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

    @Then("^its (.*) notation is (.*)$")
    public void thenItsNotationIs(String notation, String s) {
        if (notation.equals("PREFIX") || notation.equals("POSTFIX") || notation.equals("INFIX")) {
            Printer p = new Printer(Notation.valueOf(notation));
            op.accept(p);
            assertEquals(s, p.getResult());
        } else fail(notation + " is not a correct notation! ");
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
            MyNumber other = c.eval(op);
            if (other instanceof MyInt other_int)
                    assertEquals(val, (double) other_int.getIntValue());
            else if (other instanceof  MyRationalNumber other_rat)
                assertEquals(val, other_rat.getDoubleValue());
            else if (other instanceof MyDouble other_double)
                assertEquals(val, other_double.getDoubleValue());
            else
                fail();
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    public static void addParam(Expression e) {
        params = new ArrayList<>();
        params.add(e);
        op.addMoreParams(params);
    }
}
