package core.operation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import core.Expression;
import core.number.MyInt;

import java.util.Arrays;
import java.util.List;

class TestUnaryOperation {

    @Test
    void testEquals() throws Exception {
        List<Expression> params1 = Arrays.asList(new MyInt(3), new MyInt(4), new MyInt(5));
        Expression arg = new Divides(params1);

        UnaryOperation o = new AbsoluteValue(arg);
        UnaryOperation o2 = new AbsoluteValue(arg);

        assertEquals(o, o2);
        assertEquals(o, o);
        assertFalse(o.equals(null));
    }

    @Test
    void testGetArg() throws Exception {
        List<Expression> params1 = Arrays.asList(new MyInt(3), new MyInt(4), new MyInt(5));
        Expression arg = new Divides(params1);

        UnaryOperation o = new AbsoluteValue(arg);
        assertEquals(arg, o.getArg());
    }
}
