package core.visitor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyDouble;
import core.number.MyInt;
import core.operation.Divides;
import core.operation.Minus;
import core.operation.BinaryOperation;
import core.operation.Plus;
import core.operation.Times;
import core.operation.AbsoluteValue;

import java.util.Arrays;
import java.util.List;


class TestNotation {

    /* This is an auxiliary method to avoid code duplication.
     */
    void testNotation(String s, BinaryOperation o, Notation n) {
        Printer p = new Printer(n);
        o.accept(p);
        assertEquals(s, p.getResult());
    }

    /* This is an auxiliary method to avoid code duplication.
     */
    void testNotations(String symbol, double value1, double value2, BinaryOperation op) {
        //prefix notation:
        testNotation(symbol + " (" + value1 + ", " + value2 + ")", op, Notation.PREFIX);
        //infix notation:
        testNotation("( " + value1 + " " + symbol + " " + value2 + " )", op, Notation.INFIX);
        //postfix notation:
        testNotation("(" + value1 + ", " + value2 + ") " + symbol, op, Notation.POSTFIX);
    }

    @ParameterizedTest
    @ValueSource(strings = {"*", "+", "/", "-"})
    void testOutput(String symbol) {
        double value1 = 8;
        double value2 = 6;
        BinaryOperation op = null;
        //List<Expression> params = new ArrayList<>(Arrays.asList(new MyNumber(value1),new MyNumber(value2)));
        List<Expression> params = Arrays.asList(new MyDouble(value1), new MyDouble(value2));
        try {
            //construct another type of operation depending on the input value
            //of the parameterised test
            switch (symbol) {
                case "+" -> op = new Plus(params);
                case "-" -> op = new Minus(params);
                case "*" -> op = new Times(params);
                case "/" -> op = new Divides(params);
                default -> fail();
            }
        } catch (IllegalConstruction e) {
            fail();
        }
        testNotations(symbol, value1, value2, op);
    }

    @Test
    void testComplexNotations() {
        BinaryOperation o = null;
        try {
            List<Expression> params1 = Arrays.asList(new MyInt(3), new MyInt(4), new MyInt(5));
            List<Expression> params2 = Arrays.asList(new MyInt(5), new MyInt(4));
            List<Expression> params3 = Arrays.asList(new Plus(params1), new Minus(params2), new MyInt(7));
            o = new Divides(params3);
        } catch (IllegalConstruction e) {
            fail();
        }

        // Use printer to test the output of the complex expression
        String s = "( ( 3 + 4 + 5 ) / ( 5 - 4 ) / 7 )";
        Notation n = Notation.INFIX;

        Printer p = new Printer(n);
        o.accept(p);
        assertEquals(s, p.getResult());

        // Same but with postfix notation
        s = "((3, 4, 5) +, (5, 4) -, 7) /";
        n = Notation.POSTFIX;
        p.setNotation(n);
        o.accept(p);
        assertEquals(s, p.getResult());

        // Same but with prefix notation
        s = "/ (+ (3, 4, 5), - (5, 4), 7)";
        n = Notation.PREFIX;
        p.setNotation(n);
        o.accept(p);
        assertEquals(s, p.getResult());
    }

    @Test
    void testNotationsWithUnaryOperations() {
        BinaryOperation o = null;
        try {
            List<Expression> params1 = Arrays.asList(new MyInt(3), new MyInt(4), new MyInt(5));
            List<Expression> params2 = Arrays.asList(new MyInt(5), new MyInt(4));
            List<Expression> params3 = Arrays.asList(new Plus(params1), new AbsoluteValue(new AbsoluteValue(new Minus(params2))), new MyInt(7));
            o = new Divides(params3);
        } catch (IllegalConstruction e) {
            fail();
        }

        // Use printer to test the output of the complex expression
        String s = "( ( 3 + 4 + 5 ) / abs ( abs ( 5 - 4 ) ) / 7 )";
        Notation n = Notation.INFIX;

        Printer p = new Printer(n);
        o.accept(p);
        assertEquals(s, p.getResult());

        // Same but with postfix notation
        s = "((3, 4, 5) +, (((5, 4) -) abs) abs, 7) /";
        n = Notation.POSTFIX;
        p.setNotation(n);
        o.accept(p);
        assertEquals(s, p.getResult());

        // Same but with prefix notation
        s = "/ (+ (3, 4, 5), abs (abs (- (5, 4))), 7)";
        n = Notation.PREFIX;
        p.setNotation(n);
        o.accept(p);
        assertEquals(s, p.getResult());
    }
}
