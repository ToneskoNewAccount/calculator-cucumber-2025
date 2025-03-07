package calculator;

//Import Junit5 libraries for unit testing:

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import visitor.Counter;
import visitor.Counter.CounterMode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


class TestCounting {
    private Operation o;
    private int value1, value2;
    private Expression e;

    @BeforeEach
    void setUp() throws Exception {
        List<Expression> params1 = Arrays.asList(new MyNumber(3), new MyNumber(4), new MyNumber(5));
        List<Expression> params2 = Arrays.asList(new MyNumber(5), new MyNumber(4));
        List<Expression> params3 = Arrays.asList(new Plus(params1), new Minus(params2), new MyNumber(7));
        o = new Divides(params3);
        value1 = 8;
        value2 = 6;
        e = null;
    }

    @Test
    void testNumberCounting() {
        e = new MyNumber(value1);

        //test whether a number has zero depth (i.e. no nested expressions)
        Counter counter = new Counter(CounterMode.DEPTH);
        e.accept(counter);
        assertEquals(0, counter.getResult());

        //test whether a number contains zero operations
        counter.setMode(CounterMode.OPERATIONS);
        e.accept(counter);
        assertEquals(0, counter.getResult());

        //test whether a number contains 1 number
        counter.setMode(CounterMode.NUMBERS);
        e.accept(counter);
        assertEquals(1, counter.getResult());
    }

    @ParameterizedTest
    @ValueSource(strings = {"*", "+", "/", "-"})
    void testOperationCounting(String symbol) {
        List<Expression> params = Arrays.asList(new MyNumber(value1), new MyNumber(value2));

        try {
            //construct another type of operation depending on the input value
            //of the parameterised test
            switch (symbol) {
                case "+" -> e = new Plus(params);
                case "-" -> e = new Minus(params);
                case "*" -> e = new Times(params);
                case "/" -> e = new Divides(params);
                default -> fail();
            }
        } catch (IllegalConstruction e) {
            fail();
        }
        //test whether a binary operation has depth 1
        Counter counter = new Counter(CounterMode.DEPTH);
        e.accept(counter);
        assertEquals(1, counter.getResult(), "counting depth of an Operation");

        //test whether a binary operation contains 1 operation
        counter.setMode(CounterMode.OPERATIONS);
        e.accept(counter);
        assertEquals(1, counter.getResult());

        //test whether a binary operation contains 2 numbers
        counter.setMode(CounterMode.NUMBERS);
        e.accept(counter);
        assertEquals(2, counter.getResult());
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
