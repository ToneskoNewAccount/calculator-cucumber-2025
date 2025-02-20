package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import visitor.Counter;
import visitor.Counter.CounterMode;

import java.util.Arrays;
import java.util.List;

class TestOperation {

	private Operation o;
	private Operation o2;

	@BeforeEach
	void setUp() throws Exception {
		List<Expression> params1 = Arrays.asList(new MyNumber(3), new MyNumber(4), new MyNumber(5));
		List<Expression> params2 = Arrays.asList(new MyNumber(5), new MyNumber(4));
		List<Expression> params3 = Arrays.asList(new Plus(params1), new Minus(params2), new MyNumber(7));
		o = new Divides(params3);
		o2 = new Divides(params3);
	}

	@Test
	void testEquals() {
		assertEquals(o,o2);
	}

	@Test
	void testCountDepth() {
		Counter counter = new Counter(CounterMode.DEPTH);
		o.accept(counter);

		assertEquals(2, counter.getResult());
	}

	@Test
	void testCountOps() {
		Counter counter = new Counter(CounterMode.OPERATIONS);
		o.accept(counter);

		assertEquals(3, counter.getResult());
	}

	@Test
	void testCountNbs() {
		Counter counter = new Counter(CounterMode.NUMBERS);
		o.accept(counter);

		assertEquals(6, counter.getResult());
	}

}
