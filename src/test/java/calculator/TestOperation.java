package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import visitor.Counter;
import visitor.Counter.CounterMode;

import java.util.Arrays;
import java.util.List;

class TestOperation {

	@Test
	void testEquals() throws Exception{
		List<Expression> params1 = Arrays.asList(new MyNumber(3), new MyNumber(4), new MyNumber(5)),
				params2 = Arrays.asList(new MyNumber(5), new MyNumber(4)),
				params3 = Arrays.asList(new Plus(params1), new Minus(params2), new MyNumber(7));

		Operation o = new Divides(params3),
			o2 = new Divides(params3);

		assertEquals(o,o2);
	}

}
