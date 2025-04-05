package core.number;

/**
 * Class that represents a real number
 */
public class MyDouble extends MyNumber {

    /**
     * The double value
     */
    private final Double value;

    /**
     * The epsilon value to compare two doubles
     */
    private static final Double EPSILON = 1e-9;

    public Double getDoubleValue() {
        return value;
    }

    /**
     * Constructor method
     *
     * @param v The double value to be contained in the object
     */
    public MyDouble(Double v) {
        value = v;
    }

    /**
     * @return The value of the object
     */
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {

        if (other instanceof MyComplexNumber) {
            return getComplexValue().equals(other);
        }

        if (!(other instanceof MyDouble otherDouble))
            return false;

        if (value.isNaN())
            return otherDouble.value.isNaN();

        return Math.abs(value - otherDouble.value) < EPSILON;
    }

    @Override
    public MyNumber add(MyNumber other) {

        if (other instanceof MyComplexNumber) {
            return getComplexValue().add(other);
        }

        if (!(other instanceof MyDouble otherDouble))
            throw new ArithmeticException("Unknown type number");

        return new MyDouble(value + otherDouble.value);
    }

    @Override
    public MyNumber minus(MyNumber other) {

        if (other instanceof MyComplexNumber) {
            return getComplexValue().minus(other);
        }

        if (!(other instanceof MyDouble otherDouble))
            throw new ArithmeticException("Unknown type number");

        return new MyDouble(value - otherDouble.value);
    }

    @Override
    public MyNumber divide(MyNumber other) {

        if (other instanceof MyComplexNumber) {
            return getComplexValue().divide(other);
        }

        if (!(other instanceof MyDouble otherDouble))
            throw new ArithmeticException("Unknown type number");

        else if (otherDouble.value == 0)
            // Return NaN if division by zero
            return new MyDouble(Double.NaN);

        return new MyDouble(value / otherDouble.value);
    }

    @Override
    public MyNumber multiply(MyNumber other) {

        if (other instanceof MyComplexNumber) {
            return getComplexValue().multiply(other);
        }

        if (!(other instanceof MyDouble otherDouble))
            throw new ArithmeticException("Unknown type number");

        return new MyDouble(value * otherDouble.value);
    }

    /**
     * @return The hashcode of the object
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public MyComplexNumber getComplexValue() {
        return new MyComplexNumber(this, new MyInt(0));
    }

    @Override
    public MyNumber abs() {
        return new MyDouble(Math.abs(value));
    }
}
