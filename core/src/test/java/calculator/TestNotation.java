package calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import visitor.Printer;

import java.util.Arrays;
import java.util.List;


class TestNotation {

    /* This is an auxilary method to avoid code duplication.
     */
	void testNotation(String s,Operation o,Notation n) {
		Printer p = new Printer(n);
		o.accept(p);
		assertEquals(s, p.getResult());
	}

	/* This is an auxilary method to avoid code duplication.
     */
	void testNotations(String symbol,double value1,double value2,Operation op) {
		//prefix notation:
		testNotation(symbol +" (" + value1 + ", " + value2 + ")", op, Notation.PREFIX);
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
		Operation op = null;
		//List<Expression> params = new ArrayList<>(Arrays.asList(new MyNumber(value1),new MyNumber(value2)));
		List<Expression> params = Arrays.asList(new MyNumber(value1),new MyNumber(value2));
		try {
			//construct another type of operation depending on the input value
			//of the parameterised test
			switch (symbol) {
				case "+"	->	op = new Plus(params);
				case "-"	->	op = new Minus(params);
				case "*"	->	op = new Times(params);
				case "/"	->	op = new Divides(params);
				default		->	fail();
			}
		} catch (IllegalConstruction e) {
			fail();
		}
		testNotations(symbol, value1, value2, op);
	}

	@Test
	void testComplexNotations() {
		Operation o = null;
		try {
			List<Expression> params1 = Arrays.asList(new MyNumber(3), new MyNumber(4), new MyNumber(5));
			List<Expression> params2 = Arrays.asList(new MyNumber(5), new MyNumber(4));
			List<Expression> params3 = Arrays.asList(new Plus(params1), new Minus(params2), new MyNumber(7));
			o = new Divides(params3);
		} catch (IllegalConstruction e) {
			fail();
		}

		// Use printer to test the output of the complex expression
		String s = "( ( 3.0 + 4.0 + 5.0 ) / ( 5.0 - 4.0 ) / 7.0 )";
		Notation n = Notation.INFIX;

		Printer p = new Printer(n);
		o.accept(p);
		assertEquals(s, p.getResult());

		// Same but with postfix notation
		s = "((3.0, 4.0, 5.0) +, (5.0, 4.0) -, 7.0) /";
		n = Notation.POSTFIX;
		p.setNotation(n);
		o.accept(p);
		assertEquals(s, p.getResult());

		// Same but with prefix notation
		s = "/ (+ (3.0, 4.0, 5.0), - (5.0, 4.0), 7.0)";
		n = Notation.PREFIX;
		p.setNotation(n);
		o.accept(p);
		assertEquals(s, p.getResult());
	}
}
