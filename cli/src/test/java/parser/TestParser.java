package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import calculator.Expression;
import calculator.Plus;
import calculator.Divides;
import calculator.Minus;
import calculator.Times;
import parser.exceptions.ParsingException;
import calculator.MyNumber;
import calculator.IllegalConstruction;

// There are already cucumber tests to test that the result of the evaluation of the parsed expression is correct.
// So, here we only test that the parser creates the expression correctly. For example, when we have "1 * 2 * 3",
// does it creates Times(1, 2, 3) or Times(Times(1, 2), 3)?
public class TestParser {

    private Parser parser = new Parser();

    @Test
    public void testParserCreatesCorrectExpression1() throws IllegalConstruction, ParsingException {
        Expression expr = new Plus(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)));
        assertEquals(expr, parser.parse("1 + 2 + 3"));

        expr = new Times(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)));
        assertEquals(expr, parser.parse("1 * 2 * 3"));

        expr = new Minus(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)));
        assertEquals(expr, parser.parse("1 - 2 - 3"));

        expr = new Divides(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)));
        assertEquals(expr, parser.parse("1 / 2 / 3"));
    }

    @Test
    public void testParserCreatesCorrectExpression2() throws IllegalConstruction, ParsingException {
        Expression expr1 = new Times(List.of(new MyNumber(3), new MyNumber(4), new MyNumber(5)));
        Expression expr2 = new Plus(List.of(new MyNumber(1), new MyNumber(2), expr1));
        assertEquals(expr2, parser.parse("1 + 2 + 3 * 4 * 5"));
    }

    @Test
    public void testParserCreatesCorrectExpression3() throws IllegalConstruction, ParsingException {
        Expression expr1 = new Times(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)));
        Expression expr2 = new Divides(List.of(expr1, new MyNumber(4), new MyNumber(5)));
        assertEquals(expr2, parser.parse("1 * 2 * 3 / 4 / 5"));
    }

    @Test
    public void testParserCreatesCorrectExpression4() throws IllegalConstruction, ParsingException {
        Expression expr1 = new Divides(List.of(new MyNumber(3), new MyNumber(4), new MyNumber(5)));
        Expression expr2 = new Times(List.of(new MyNumber(1), new MyNumber(2), expr1));
        assertEquals(expr2, parser.parse("1 * 2 * ( 3 / 4 / 5 )"));
    }
}
