package core.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyComplexNumber {
    @Test
    void testCreateComplexNumber() {
        MyComplexNumber num = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(10), new MyInt(5));
        assertEquals(10, num.getReal().getDoubleValue().intValue());
        assertEquals(5, num.getImag().getDoubleValue().intValue());
    }

    @Test
    void testToString() {
        MyNumber num = MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        assertEquals("(3 + 4i)", num.toString());
        num = MyComplexNumber.createComplexNumber(new MyInt(-3), new MyInt(-4));
        assertEquals("(-3 - 4i)", num.toString());
    }

    @Test
    void testEqualsWithMyComplexNumber() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyComplexNumber num2 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyComplexNumber num3 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(5), new MyInt(6));
        MyComplexNumber num4 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(6));


        assertEquals(num1, num2);
        assertEquals(num2, num1);
        assertEquals(num1.hashCode(), num2.hashCode());
        assertEquals(num2.hashCode(), num1.hashCode());
        assertEquals(num1, num1);
        assertEquals(num1.hashCode(), num1.hashCode());
        assertNotEquals(num1, num3);
        assertNotEquals(num1, num4);
    }

    @Test
    void testEqualsWithMyDouble() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num4 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyDouble(0.0));

        MyDouble num2 = new MyDouble(3.5);
        MyDouble num3 = new MyDouble(5.1);

        assertFalse(num1.equals(num2));
        assertFalse(num1.equals(num3));
        assertTrue(num2.equals(num4));
    }

    @Test
    void testAddWithMyDouble() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyDouble(0.0));
        MyComplexNumber num3 = (MyComplexNumber) num1.add(num2);
        assertTrue(num2 instanceof MyDouble);
        assertEquals(6.5, num3.getReal().getDoubleValue());
        assertEquals(4, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testAddWithMyInt() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(0));
        MyComplexNumber num3 = (MyComplexNumber) num1.add(num2);
        assertTrue(num2 instanceof MyInt);
        assertEquals(6, num3.getReal().getDoubleValue().intValue());
        assertEquals(4, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testMinusWithMyDouble() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyInt(0));
        MyComplexNumber num3 = (MyComplexNumber) num1.minus(num2);
        assertTrue(num2 instanceof MyDouble);
        assertEquals(-0.5, num3.getReal().getDoubleValue());
        assertEquals(4, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testMinusWithMyInt() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(0));
        MyComplexNumber num3 = (MyComplexNumber) num1.minus(num2);
        assertTrue(num2 instanceof MyInt);
        assertEquals(0, num3.getReal().getDoubleValue().intValue());
        assertEquals(4, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testDivideWithMyDouble() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyDouble(0.0));
        MyComplexNumber num3 = (MyComplexNumber) num1.divide(num2);
        assertTrue(num2 instanceof MyDouble);
        assertEquals(0.85714, num3.getReal().getDoubleValue(), 1e-5);
        assertEquals(1.14285, num3.getImag().getDoubleValue(), 1e-5);
    }

    @Test
    void testDivideWithMyInt() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(0));
        MyComplexNumber num3 = (MyComplexNumber) num1.divide(num2);
        assertTrue(num2 instanceof MyInt);
        assertEquals(1, num3.getReal().getDoubleValue().intValue());
        assertEquals(1.33333, num3.getImag().getDoubleValue(), 1e-5);
    }

    @Test
    void testMultiplyWithMyDouble() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyDouble(3.5), new MyDouble(0.0));
        MyComplexNumber num3 = (MyComplexNumber) num1.multiply(num2);
        assertTrue(num2 instanceof MyDouble);
        assertEquals(10.5, num3.getReal().getDoubleValue());
        assertEquals(14, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testMultiplyWithMyInt() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(0));
        MyComplexNumber num3 = (MyComplexNumber) num1.multiply(num2);
        assertTrue(num2 instanceof MyInt);
        assertEquals(9, num3.getReal().getDoubleValue().intValue());
        assertEquals(12, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testAddWithMyRationalNumber() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber((MyRationalNumber)MyRationalNumber.createRationalNumber(7, 2), (MyRationalNumber)MyRationalNumber.createRationalNumber(0, 1));
        MyComplexNumber num3 = (MyComplexNumber) num1.add(num2);
        assertTrue(num2 instanceof MyRationalNumber);
        assertEquals(6.5, num3.getReal().getDoubleValue());
        assertEquals(4, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testMinusWithMyRationalNumber() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber((MyRationalNumber)MyRationalNumber.createRationalNumber(7, 2), (MyRationalNumber)MyRationalNumber.createRationalNumber(0, 1));
        MyComplexNumber num3 = (MyComplexNumber) num1.minus(num2);
        assertTrue(num2 instanceof MyRationalNumber);
        assertEquals(-0.5, num3.getReal().getDoubleValue());
        assertEquals(4, num3.getImag().getDoubleValue().intValue());
    }

    @Test
    void testDivideWithMyRationalNumber() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber((MyRationalNumber)MyRationalNumber.createRationalNumber(7, 2), (MyRationalNumber)MyRationalNumber.createRationalNumber(0, 1));
        MyComplexNumber num3 = (MyComplexNumber) num1.divide(num2);
        assertTrue(num2 instanceof MyRationalNumber);
        assertEquals(0.85714, num3.getReal().getDoubleValue(), 1e-5);
        assertEquals(1.14285, num3.getImag().getDoubleValue(), 1e-5);
    }

    @Test
    void testMultiplyWithMyRationalNumber() {
        MyComplexNumber num1 = (MyComplexNumber) MyComplexNumber.createComplexNumber(new MyInt(3), new MyInt(4));
        MyNumber num2 = MyComplexNumber.createComplexNumber((MyRationalNumber)MyRationalNumber.createRationalNumber(7, 2), (MyRationalNumber)MyRationalNumber.createRationalNumber(0, 1));
        MyComplexNumber num3 = (MyComplexNumber) num1.multiply(num2);
        assertTrue(num2 instanceof MyRationalNumber);
        assertEquals(10.5, num3.getReal().getDoubleValue());
        assertEquals(14, num3.getImag().getDoubleValue().intValue());
    }
}
