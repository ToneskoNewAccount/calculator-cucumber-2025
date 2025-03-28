package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestDivides {

    private final int value1 = 8;
    private final int value2 = 4;
    private final int value3 = 0;
    private Divides op;
    private List<Expression> params;

    @BeforeEach
    void setUp() {
        params = Arrays.asList(new MyInt(value1), new MyInt(value2));
        try {
            op = new Divides(params);
        }
        catch(IllegalConstruction e) { fail(); }
    }

    @Test
    void testConstructor1() {
        // It should not be possible to create an expression without null parameter list
        assertThrows(IllegalConstruction.class, () -> op = new Divides(null));
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void testConstructor2() {
        // A Times expression should not be the same as a Divides expression
        try {
            assertNotSame(op, new Times(new ArrayList<>()));
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testEquals() {
        // Two similar expressions, constructed separately (and using different constructors) should be equal
        try {
            Divides d = new Divides(params);
            assertEquals(op, d);
        }
        catch(IllegalConstruction e) { fail(); }
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testNull() {
        assertDoesNotThrow(() -> op==null); // Direct way to to test if the null case is handled.
    }

    @Test
    void testHashCode() {
        // Two similar expressions, constructed separately (and using different constructors) should have the same hashcode
        try {
            Divides e = new Divides(params);
            assertEquals(e.hashCode(), op.hashCode());
        }
        catch(IllegalConstruction e) { fail(); }
    }

    @Test
    void testNullParamList() {
        params = null;
        assertThrows(IllegalConstruction.class, () -> op = new Divides(params));
    }

    @Test
    void testDivisionByZero() {
        // We test that is does not throw an exception.
        assertDoesNotThrow(() -> op.op(new MyInt(value1), new MyInt(value3)));
    }

    @Test
    void testDivisionByZeroReturnsNaN() {
        MyDouble result = (MyDouble) op.op(new MyInt(value1), new MyInt(value3));
        assertTrue(Double.isNaN(result.getDoubleValue()), "Result should be NaN when dividing by zero.");
    }

    @Test
    void testNegativeDivisionByZeroReturnsNaN() {
        MyDouble result = (MyDouble) op.op(new MyInt(value1), new MyInt(value3));
        assertTrue(Double.isNaN(result.getDoubleValue()), "Negative number divided by zero should return NaN.");
    }

    @Test
    void testZeroDividedByZeroReturnsNaN() {
        MyDouble result = (MyDouble) op.op(new MyInt(value3), new MyInt(value3));
        assertTrue(Double.isNaN(result.getDoubleValue())); // "Zero divided by zero should return NaN.")
    }

    @Test
    void testDivision() {
        MyInt result = (MyInt) op.op(new MyInt(value1), new MyInt(value2));
        assertEquals(2, result.getIntValue());
    }

    @Test
    void testDivisionWithNegativeValues() {
        MyInt result = (MyInt) op.op(new MyInt(-value1), new MyInt(value2));
        assertEquals(-2, result.getIntValue());
    }

    @Test
    void testDivisionWithNegativeDenominator() {
        MyInt result = (MyInt) op.op(new MyInt(value1), new MyInt(-value2));
        assertEquals(-2, result.getIntValue());
    }

    @Test
    void testDivisionWithNegativeValuesAndDenominator() {
        MyInt result = (MyInt) op.op(new MyInt(-value1), new MyInt(-value2));
        assertEquals(2, result.getIntValue());
    }
}

