package core.number;

/**
 * MyRationalNumber is a concrete class that represents rational numbers,
 * which are a special kind of MyNumber, just like operations are.
 *
 * @see MyNumber
 */
public class MyRationalNumber extends MyDouble {

    /**
     * The numerator of the rational number
     */
    private final int numerator;

    /**
     * The denominator of the rational number
     */
    private final int denominator;

    public static MyNumber createRationalNumber(int num, int den) {
        if (den == 0) {
            return new MyDouble(Double.NaN);
        } else {
            int sign = den < 0 ? -1 : 1;
            int gcd = gcd(num,den);
            num = sign * num / gcd;
            den = sign * den / gcd;

            if (den == 1)
                return new MyInt(num);
            return new MyRationalNumber(num, den);
        }
    }

    /**
     * Constructor method
     *
     * @param num The numerator of the rational number
     * @param den The denominator of the rational number
     */
    private MyRationalNumber(int num, int den) {
        super((double) num / den);
        numerator = num;
        denominator = den;
    }

    /**
     * Creates an instances of MyRationalNumber with a denominator of 1
     *
     * @param num The numerator of the rational number
     */
    protected MyRationalNumber(int num) {
        this(num, 1);
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }

    /**
     * getter method to obtain the numerator of the rational number
     * @return The numerator of the rational number
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * getter method to obtain the denominator of the rational number
     * @return The denominator of the rational number
     */
    public int getDenominator() {
        return denominator;
    }

    /**

     * print the rational number in the form of a fraction
     * @return The String that is the result of the conversion.
     */
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MyRationalNumber otherRational))
            return super.equals(other);

        return this.numerator == otherRational.numerator && this.denominator == otherRational.denominator;
    }

    @Override
    public MyNumber add(MyNumber other) {
        if (!(other instanceof MyRationalNumber otherRational))
            return super.add(other);

        else if (denominator == otherRational.denominator)
            return createRationalNumber(numerator + otherRational.numerator, denominator);

        // Reduce to the same denominator and compute the new numerator.
        int newNum = numerator * otherRational.denominator + denominator * otherRational.numerator;

        return createRationalNumber(newNum, denominator * otherRational.denominator);
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (!(other instanceof MyRationalNumber otherRational))
            return super.minus(other);

        else if (denominator == otherRational.denominator)
            return createRationalNumber(numerator - otherRational.numerator, denominator);

        // Reduce to the same denominator and compute the new numerator.
        int newNum = numerator * otherRational.denominator - denominator * otherRational.numerator;

        return createRationalNumber(newNum, denominator * otherRational.denominator);
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (!(other instanceof MyRationalNumber otherRational))
            return super.divide(other);

        return createRationalNumber(numerator * otherRational.denominator, denominator * otherRational.getNumerator());
    }

    @Override
    public MyNumber multiply(MyNumber other) {
        if (!(other instanceof MyRationalNumber otherRational))
            return super.multiply(other);

        return createRationalNumber(numerator * otherRational.numerator, denominator * otherRational.denominator);
    }

    /**
     * @return The hashcode of the object
     */
    @Override
    public int hashCode() {
        return numerator * 31 + denominator;
    }

    @Override
    public MyNumber abs() {
        // We can do that since the simplified form of a rational number is unique.
        // So if there is a negative sign, it is in the numerator.
        return createRationalNumber(Math.abs(numerator), denominator);
    }
}
