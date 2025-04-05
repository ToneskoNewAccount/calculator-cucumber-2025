package core.number;

/**
 * A class representing an integer number
 */
public class MyInt extends MyRationalNumber {
    /**
     * The value of the object
     */
    private final Integer value;

    /**
     *
     * @return The value of the object
     */
    public Integer getIntValue() {
        return value;
    }

    /**
     * Constructor method
     *
     * @param v The double value to be contained in the object
     */
    public MyInt(Integer v) {
        super(v);
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
        if (!(other instanceof MyInt otherInt))
            return super.equals(other);

        return value.equals(otherInt.value);
    }

    @Override
    public MyNumber add(MyNumber other) {
        if (!(other instanceof MyInt otherInt))
            return super.add(other);

        return new MyInt(value + otherInt.value);
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (!(other instanceof MyInt otherInt))
            return super.minus(other);

        return new MyInt(this.value - otherInt.value);
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (!(other instanceof MyInt otherInt))
            return super.divide(other);

       return MyRationalNumber.createRationalNumber(value, otherInt.value);
    }

    @Override
    public MyNumber multiply(MyNumber other) {
        if (!(other instanceof MyInt otherInt))
            return super.multiply(other);

        return new MyInt(this.value * otherInt.value);
    }

    /**
     * @return The hashcode of the object
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
