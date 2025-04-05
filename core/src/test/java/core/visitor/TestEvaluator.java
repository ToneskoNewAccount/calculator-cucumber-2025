package core.visitor;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import core.Calculator;
import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyDouble;
import core.operation.Divides;
import core.operation.Minus;
import core.operation.Plus;
import core.operation.Times;

import java.util.Arrays;
import java.util.List;

class TestEvaluator {

    private Calculator calc;
    private double value1, value2;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        value1 = 8;
        value2 = 6;
    }

    @Test
    void testEvaluatorMyNumber() {
        assertEquals( value1, ((MyDouble) calc.eval(new MyDouble(value1))).getDoubleValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"*", "+", "/", "-"})
    void testEvaluateOperations(String symbol) {
        List<Expression> params = Arrays.asList(new MyDouble(value1),new MyDouble(value2));
        try {
            //construct another type of operation depending on the input value
            //of the parameterised test
            switch (symbol) {
                case "+" -> assertEquals( value1 + value2, ((MyDouble) calc.eval(new Plus(params))).getDoubleValue());
                case "-" -> assertEquals( value1 - value2, ((MyDouble) calc.eval(new Minus(params))).getDoubleValue());
                case "*" -> assertEquals( value1 * value2, ((MyDouble) calc.eval(new Times(params))).getDoubleValue());
                case "/" -> assertEquals( value1 / value2, ((MyDouble) calc.eval(new Divides(params))).getDoubleValue());
                default  -> fail();
            }
        } catch (IllegalConstruction e) {
            fail();
        }
    }

}
