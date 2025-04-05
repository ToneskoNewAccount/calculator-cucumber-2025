package core.steps;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import core.Calculator;
import core.Expression;
import core.exception.IllegalConstruction;
import core.operation.AbsoluteValue;
import core.number.MyNumber;
import core.operation.Divides;
import core.operation.Minus;
import core.operation.BinaryOperation;
import core.operation.Plus;
import core.operation.Times;
import core.operation.UnaryOperation;
import core.visitor.Notation;
import core.visitor.Printer;
import java.util.ArrayList;

public class CommonSteps {
    protected static ArrayList<Expression> params;
    protected static BinaryOperation op;
    protected static UnaryOperation unaryOp;
    protected static Calculator c;

    @Before
    public void resetMemoryBeforeEachScenario() {
        params = null;
        op = null;
        unaryOp = null;
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
                case "abs" -> unaryOp = new AbsoluteValue(null);
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

            if (op != null) op.accept(p);
            else if (unaryOp != null) unaryOp.accept(p);
            else fail("The operation is undefined.");

            assertEquals(s, p.getResult());
        } else fail(notation + " is not a correct notation.");
    }

    public static void addParam(Expression e) {

        if (op != null) {
            params = new ArrayList<>();
            params.add(e);
            op.addMoreParams(params);
        } else if (unaryOp != null) {
            unaryOp.setArg(e);
        }
    }

    public static void operationEvaluation(MyNumber expected) {

        MyNumber actual = null;
        if (op != null)
            actual = c.eval(op);

        else if (unaryOp != null)
            actual = c.eval(unaryOp);

        else fail("The operation is undefined.");

        assertEquals(expected, actual);
    }
}
