package core.operation;

//Import Junit5 libraries for unit testing:

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestPlus {

    private Plus op;
    private List<Expression> params;

    @BeforeEach
    void setUp() {
        int value1 = 8;
        int value2 = 6;
        params = new ArrayList<>(Arrays.asList(new MyInt(value1), new MyInt(value2)));
        try {
            op = new Plus(params);
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testConstructor1() {
        // It should not be possible to create a Plus expression without null parameter list
        assertThrows(IllegalConstruction.class, () -> op = new Plus(null));
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void testConstructor2() {
        // A Times expression should not be the same as a Plus expression
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
            Plus e = new Plus(params);
            assertEquals(op, e);
            assertEquals(e, e);
            assertNotEquals(e, new Plus(new ArrayList<>(Arrays.asList(new MyInt(5), new MyInt(4)))));
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testNull() {
        assertDoesNotThrow(() -> op == null); // Direct way to test if the null case is handled.
    }

    @Test
    void testHashCode() {
        // Two similar expressions, constructed separately (and using different constructors) should have the same hashcode
        try {
            Plus e = new Plus(params);
            assertEquals(e.hashCode(), op.hashCode());
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testNullParamList() {
        params = null;
        assertThrows(IllegalConstruction.class, () -> op = new Plus(params));
    }


}
