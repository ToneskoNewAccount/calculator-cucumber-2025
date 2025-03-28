package calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyInt {
    @Test
    void testGetValue() {
        MyInt num = new MyInt(10);
        assertEquals(10, num.getIntValue());
    }

    @Test
    void testToString() {
        MyInt num = new MyInt(5);
        assertEquals("5", num.toString());
    }

    @Test
    void testEqualsWithMyInt() {
        MyInt num1 = new MyInt(7);
        MyInt num2 = new MyInt(7);
        MyInt num3 = new MyInt(10);

        assertTrue(num1.equals(num2));
        assertFalse(num1.equals(num3));
    }

    @Test
    void testAdditionWithMyInt() {
        MyInt num1 = new MyInt(4);
        MyInt num2 = new MyInt(6);

        MyNumber result = num1.add(num2);
        assertTrue(result instanceof MyInt);
        assertEquals(10, ((MyInt) result).getIntValue());
    }

    @Test
    void testSubtractionWithMyInt() {
        MyInt num1 = new MyInt(10);
        MyInt num2 = new MyInt(4);

        MyNumber result = num1.minus(num2);
        assertTrue(result instanceof MyInt);
        assertEquals(6, ((MyInt) result).getIntValue());
    }

    @Test
    void testMultiplicationWithMyInt() {
        MyInt num1 = new MyInt(3);
        MyInt num2 = new MyInt(5);

        MyNumber result = num1.multiply(num2);
        assertTrue(result instanceof MyInt);
        assertEquals(15, ((MyInt) result).getIntValue());
    }

    @Test
    void testDivisionWithMyInt() {
        MyInt num1 = new MyInt(10);
        MyInt num2 = new MyInt(2);

        MyNumber result = num1.divide(num2);
        assertTrue(result instanceof MyInt); // The result should be an integer 5
        assertEquals(5, ((MyInt) result).getIntValue());

        num2 = new MyInt(3);

        result = num1.divide(num2);
        assertFalse(result instanceof MyInt); // The result shouldn't be an integer
        assertTrue(result instanceof MyRationalNumber); // The result should be a rational
        assertEquals("10/3", result.toString());
    }

    @Test
    void testEqualsWithMyRationalNumber() {
        MyInt num1 = new MyInt(3);
        MyInt num2 = (MyInt) MyRationalNumber.createRationalNumber(3, 1);
        MyRationalNumber num3 = (MyRationalNumber) MyRationalNumber.createRationalNumber(10, 3);

        assertTrue(num1.equals(num2));
        assertFalse(num1.equals(num3));
    }

    @Test
    void testMultiplyWithMyRationalNumber() {
        MyInt num1 = new MyInt(3);
        MyRationalNumber num2 = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 2);

        MyNumber result = num1.multiply(num2);
        assertTrue(result instanceof MyRationalNumber);
        assertEquals("9/2", result.toString());
    }

    @Test
    void testEqualsWithDoubleNaN() {
        MyInt num1 = new MyInt(3);
        MyDouble num2 = new MyDouble(Double.NaN);
        assertFalse(num1.equals(num2));
    }

    @Test
    void testDivideByZero() {
        MyInt num1 = new MyInt(3);
        MyDouble num2 = new MyDouble(0.0);
        MyNumber result = num1.divide(num2);
        assertTrue(result instanceof MyDouble);
        assertEquals(Double.NaN, ((MyDouble) result).getDoubleValue());
    }

    @Test
    void testHashCode() {
        MyInt num1 = new MyInt(42);
        MyInt num2 = new MyInt(42);
        assertEquals(num1.hashCode(), num2.hashCode());
    }
}
