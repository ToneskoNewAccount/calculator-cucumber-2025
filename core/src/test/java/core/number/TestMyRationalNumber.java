package core.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMyRationalNumber {
    @Test
    void testCreateRationalNumber_AllBranches() {
        // Branch 1: den == 0 (should return NaN as MyDouble)
        MyNumber result1 = MyRationalNumber.createRationalNumber(5, 0);
        assertTrue(result1 instanceof MyDouble);
        assertEquals(Double.NaN, ((MyDouble) result1).getDoubleValue());

        // Branch 2: num is reduced to an integer (denominator == 1)
        MyNumber result2 = MyRationalNumber.createRationalNumber(6, 3); // (6/3 -> 2/1 -> MyInt)
        assertTrue(result2 instanceof MyInt);
        assertEquals(2, ((MyInt) result2).getIntValue());

        // Branch 3: num is already in simplest form (MyRationalNumber expected)
        MyNumber result3 = MyRationalNumber.createRationalNumber(3, 4);
        assertTrue(result3 instanceof MyRationalNumber);
        assertEquals("3/4", result3.toString());

        // Branch 4: den < 0 (sign should be flipped)
        MyNumber result4 = MyRationalNumber.createRationalNumber(-3, -4); // (-3/-4 -> 3/4)
        assertTrue(result4 instanceof MyRationalNumber);
        assertEquals("3/4", result4.toString());

        // Branch 5: num < 0, den > 0 (negative rational number)
        MyNumber result5 = MyRationalNumber.createRationalNumber(-3, 4);
        assertTrue(result5 instanceof MyRationalNumber);
        assertEquals("-3/4", result5.toString());

        // Branch 6: num > 0, den < 0 (should flip sign)
        MyNumber result6 = MyRationalNumber.createRationalNumber(3, -4);
        assertTrue(result6 instanceof MyRationalNumber);
        assertEquals("-3/4", result6.toString());

        // Branch 7: num == 0 (should return MyInt(0))
        MyNumber result7 = MyRationalNumber.createRationalNumber(0, 5);
        assertTrue(result7 instanceof MyInt);
        assertEquals(0, ((MyInt) result7).getIntValue());

        // Branch 8: num is already simplified (e.g., 5/7 remains 5/7)
        MyNumber result8 = MyRationalNumber.createRationalNumber(5, 7);
        assertTrue(result8 instanceof MyRationalNumber);
        assertEquals("5/7", result8.toString());
    }


    @Test
    void testGetValue() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 4);
        assertEquals(0.75, num.getDoubleValue());
    }

    @Test
    void testToString() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(5, 8);
        assertEquals("5/8", num.toString());
    }

    @Test
    void testEquals() {
        MyRationalNumber num1 = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 4);
        MyRationalNumber num2 = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 4);
        MyRationalNumber num3 = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 5);
        MyRationalNumber num4 = (MyRationalNumber) MyRationalNumber.createRationalNumber(2, 4);

        assertTrue(num1.equals(num2));
        assertFalse(num1.equals(num3));
        assertFalse(num1.equals(num4));
    }

    @Test
    void testAdditionWithMyInt() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 4);
        MyNumber result = num.add(new MyInt(2));

        assertTrue(result instanceof MyRationalNumber);
        assertEquals("11/4", result.toString());
    }

    @Test
    void testAdditionWithMyRationalNumber() {
        MyRationalNumber num1 = (MyRationalNumber) MyRationalNumber.createRationalNumber(1, 3);
        MyRationalNumber num2 = (MyRationalNumber) MyRationalNumber.createRationalNumber(2, 5);

        MyNumber result = num1.add(num2);
        assertTrue(result instanceof MyRationalNumber);
        assertEquals("11/15", result.toString());
    }

    @Test
    void testSubtractionWithMyInt() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(7, 3);
        MyNumber result = num.minus(new MyInt(2));

        assertTrue(result instanceof MyRationalNumber);
        assertEquals("1/3", result.toString());
    }

    @Test
    void testMultiplicationWithMyInt() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 5);
        MyNumber result = num.multiply(new MyInt(2));

        assertTrue(result instanceof MyRationalNumber);
        assertEquals("6/5", result.toString());
    }

    @Test
    void testMultiplicationWithMyRationalNumber() {
        MyRationalNumber num1 = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 4);
        MyRationalNumber num2 = (MyRationalNumber) MyRationalNumber.createRationalNumber(2, 5);

        MyNumber result = num1.multiply(num2);
        assertTrue(result instanceof MyRationalNumber);
        assertEquals("3/10", result.toString()); // It should be automatically reduced
    }

    @Test
    void testDivisionWithMyInt() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(5, 6);
        MyNumber result = num.divide(new MyInt(2));

        assertTrue(result instanceof MyRationalNumber);
        assertEquals("5/12", result.toString());
    }

    @Test
    void testDivisionByZero() {
        MyRationalNumber num = (MyRationalNumber) MyRationalNumber.createRationalNumber(5, 6);
        MyInt zero = new MyInt(0);

        MyNumber result = num.divide(zero);
        assertTrue(result instanceof MyDouble);
        assertEquals(Double.NaN, ((MyDouble) result).getDoubleValue());
    }

    @Test
    void testHashCode() {
        MyRationalNumber num1 = (MyRationalNumber) MyRationalNumber.createRationalNumber(4, 7);
        MyRationalNumber num2 = (MyRationalNumber) MyRationalNumber.createRationalNumber(4, 7);

        assertEquals(num1.hashCode(), num2.hashCode());
    }

    @Test
    public void testRationalEqualsWithDouble() {
        MyRationalNumber num1 = (MyRationalNumber) MyRationalNumber.createRationalNumber(1, 2);
        MyDouble num2 = new MyDouble(0.5);
        assertEquals(num2, num1);
    }

    @Test
    void testDivideByZero() {
        MyRationalNumber num1 = (MyRationalNumber) MyRationalNumber.createRationalNumber(3, 4);
        MyNumber num2 = MyRationalNumber.createRationalNumber(0, 2);
        MyDouble num3 = new MyDouble(0.0);
        MyNumber result = num1.divide(num2);
        MyDouble res = (MyDouble) num1.divide(num3);
        assertTrue(result instanceof MyDouble);
        assertTrue(num2 instanceof MyInt);
        assertEquals(Double.NaN, ((MyDouble) result).getDoubleValue());
        assertEquals(Double.NaN,  res.getDoubleValue());

    }
}
