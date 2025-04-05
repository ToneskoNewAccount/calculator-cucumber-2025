package core.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMyDouble {
    @Test
    void testGetValue() {
        MyDouble num = new MyDouble((double) 10);
        assertEquals(10.0, num.getDoubleValue());
    }

    @Test
    void testToString() {
        MyDouble num = new MyDouble((double) 5);
        assertEquals("5.0", num.toString());
    }

    @Test
    void testEqualsWithMyDouble() {
        MyDouble num1 = new MyDouble((double) 7);
        MyDouble num2 = new MyDouble((double) 7);
        MyDouble num3 = new MyDouble((double) 10);

        assertTrue(num1.equals(num2));
        assertFalse(num1.equals(num3));
    }

    @Test
    void testEqualsWithMyComplexNumber() {
        MyDouble num1 = new MyDouble((double) 3.5);
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyInt(0));
        assertTrue(num1.equals(num2));

        num2 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyInt(1));
        assertFalse(num1.equals(num2));

        num2 = MyComplexNumber.createComplexNumber(new MyDouble(0.0), new MyDouble(3.5));
        assertFalse(num1.equals(num2));
    }

    @Test
    public void testEqualsWithMyInt() {
        MyDouble num1 = new MyDouble((double) 7);
        MyInt num2 = new MyInt(7);
        MyInt num3 = new MyInt(10);

        assertTrue(num1.equals(num2));
        assertFalse(num1.equals(num3));
    }

    @Test
    void testDivideByZero() {
        MyDouble num1 = new MyDouble(3.0);
        MyDouble num2 = new MyDouble(0.0);
        MyNumber result = num1.divide(num2);
        assertTrue(result instanceof MyDouble);
        assertEquals(Double.NaN, ((MyDouble) result).getDoubleValue());
    }

    @Test
    public void testEqualsWithMyRationalNumber() {
        MyDouble num1 = new MyDouble((double) 3.5);
        MyRationalNumber num2 = (MyRationalNumber) MyRationalNumber.createRationalNumber(7, 2);
        MyRationalNumber num3 = (MyRationalNumber) MyRationalNumber.createRationalNumber(10, 3);

        assertTrue(num1.equals(num2));
        assertFalse(num1.equals(num3));
    }

    @Test
    void testHashCode() {
        MyDouble num1 = new MyDouble((double) 42);
        MyDouble num2 = new MyDouble((double) 42);
        assertEquals(num1.hashCode(), num2.hashCode());
    }

    @Test
    void testDivisionByNaN() {
        MyDouble num1 = new MyDouble(3.0);
        MyDouble num2 = new MyDouble(Double.NaN);
        MyNumber result = num1.divide(num2);
        assertTrue(result instanceof MyDouble);
        assertEquals(Double.NaN, ((MyDouble) result).getDoubleValue());
    }
}
