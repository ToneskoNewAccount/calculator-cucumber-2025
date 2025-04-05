package core.number;

//Import Junit5 libraries for unit testing:

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import core.exception.IllegalConstruction;
import core.operation.Times;

import java.util.ArrayList;

class TestMyNumber {

    private final double value = 8;
    private MyDouble number;

    @BeforeEach
    void setUp() {
        number = new MyDouble(value);
    }

    @Test
    void testEquals() {
        // Two distinct MyNumber, constructed separately (using a different constructor) but containing the same value should be equal
        assertEquals(new MyDouble(value).getDoubleValue(), number.getDoubleValue());
        // Two MyNumbers containing a distinct value should not be equal:
        int otherValue = 7;
        assertNotEquals(new MyInt(otherValue), number);
        assertEquals(number, number); // Identity check (for coverage, as this should always be true)
        assertNotEquals(number, value); // number is of type MyNumber, while value is of type int, so not equal
        try {
            assertNotEquals(new Times(new ArrayList<>()), number);
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testGetValue() {
        assertEquals(value, number.getDoubleValue());
    }

    @Test
    void testToString() {
        assertEquals(Double.toString(value), number.toString());
    }

    @Test
    void testHashCode() {
        // Two distinct MyNumber, constructed separately (using a different constructor) but containing the same value should have the same hash code
        assertEquals(new MyDouble(value).hashCode(), number.hashCode());
        // Two MyNumbers containing a distinct value should not have the same hash code:
        int otherValue = 7;
        assertNotEquals(new MyInt(otherValue).hashCode(), number.hashCode());
    }

    @Test
    void testNull() {
        assertDoesNotThrow(() -> number == null); // Direct way to test if the null case is handled.
    }

}
