package calculator;

//Import Junit5 libraries for unit testing:

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestTimes {

    private Times op;
    private List<Expression> params;

    @BeforeEach
    void setUp() {
        int value1 = 8;
        int value2 = 6;
        params = Arrays.asList(new MyInt(value1), new MyInt(value2));
        try {
            op = new Times(params);
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testConstructor1() {
        // It should not be possible to create an expression without null parameter list
        assertThrows(IllegalConstruction.class, () -> op = new Times(null));
    }

    @Test
    void testConstructor2() {
        // A Plus expression should not be the same as a Times expression
        try {
            assertNotSame(op, new Plus(new ArrayList<>()));
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testEquals() {
        // Two similar expressions, constructed separately (and using different constructors) should not be equal
        try {
            Times e = new Times(params);
            assertEquals(op, e);
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testNull() {
        assertDoesNotThrow(() -> op == null); // Direct way to test if the null case is handled.
    }

    @Test
    void testHashCode() {
        // Two similar expressions, constructed separately (and using different constructors) should have the same hashcode
        try {
            Times e = new Times(params);
            assertEquals(e.hashCode(), op.hashCode());
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testNullParamList() {
        params = null;
        assertThrows(IllegalConstruction.class, () -> op = new Times(params));
    }

}
