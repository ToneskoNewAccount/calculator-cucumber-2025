package core.operation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import core.Expression;
import core.number.MyInt;

import java.util.Arrays;
import java.util.List;

class TestBinaryOperation {

    @Test
    void testEquals() throws Exception {
        List<Expression> params1 = Arrays.asList(new MyInt(3), new MyInt(4), new MyInt(5)),
                params2 = Arrays.asList(new MyInt(5), new MyInt(4)),
                params3 = Arrays.asList(new Plus(params1), new Minus(params2), new MyInt(7));

        BinaryOperation o = new Divides(params3),
                o2 = new Divides(params3);


        assertEquals(o, o2);
        assertFalse(o.equals(null));
    }

}
