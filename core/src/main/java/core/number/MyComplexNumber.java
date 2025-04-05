package core.number;

/**
 * MyComplexNumber is a concrete class that represents complex numbers,
 */
public class MyComplexNumber extends MyNumber {
    /**
     * The real part of the complex number
     */
    private final MyDouble real;

    /**
     * The imaginary part of the complex number
     */
    private final MyDouble imag;

    /**
     * The exception message
     */
    private static final String EXCEPT_MESSAGE = "Unknown type number";

    /**
     * Create a complex number from a real and imaginary part; can be the real number
     * @param real the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     * @return the complex number or the real number if the imaginary part is 0
     */
    public static MyNumber createComplexNumber(MyDouble real, MyDouble imaginary) {
        if (imaginary.getDoubleValue().doubleValue() == 0.0)
            return real;
        return new MyComplexNumber(real, imaginary);
    }

    /**
     * Constructor method
     * @param real the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     */
    public MyComplexNumber(MyDouble real, MyDouble imaginary) {
        this.real = real;
        this.imag = imaginary;
    }

    /**
     * @return The real part of the complex number
     */
    public MyDouble getReal() {
        return real;
    }

    /**
     * @return The imaginary part of the complex number
     */
    public MyDouble getImag() {
        return imag;
    }

    /**
     * @return the string representation of the complex number
     */
    @Override
    public String toString() {

        // If the imaginary part is negative, we want to print it like (a - bi), and not (a + -bi).
        if (Math.signum(imag.getDoubleValue()) == -1)
            return "(" + real.toString() + " - " + imag.toString().substring(1) + "i" + ")";

        return "(" + real.toString() + " + " + imag.toString() + "i" + ")";
    }

    /**
     * @param other Complex number to compare
     * @return true if the complex numbers are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MyComplexNumber otherComplex))
            return false; // Due to createComplexNumber, we can not have a MyComplexNumber if the imaginary part is 0
        return real.equals(otherComplex.real) && imag.equals(otherComplex.imag);
    }

    /**
     * @param other Complex number to add
     * @return the sum of the complex numbers
     */
    @Override
    public MyNumber add(MyNumber other) {
        if (other instanceof MyDouble otherDouble)
            return this.add(otherDouble.getComplexValue());
        if (!(other instanceof MyComplexNumber otherComplex))
            throw new ArithmeticException(EXCEPT_MESSAGE);

        return createComplexNumber((MyDouble) real.add(otherComplex.real), (MyDouble) imag.add(otherComplex.imag));
    }

    /**
     * @param other Complex number to subtract
     * @return the difference of the complex numbers
     */
    @Override
    public MyNumber minus(MyNumber other) {
        if (other instanceof MyDouble otherDouble)
            return this.minus(otherDouble.getComplexValue());
        if (!(other instanceof MyComplexNumber otherComplex))
            throw new ArithmeticException(EXCEPT_MESSAGE);

        return createComplexNumber((MyDouble) real.minus(otherComplex.real), (MyDouble) imag.minus(otherComplex.imag));
    }

    /**
     * @param other Complex number to multiply
     * @return the product of the complex numbers
     */
    @Override
    public MyNumber multiply(MyNumber other) {
        if (other instanceof MyDouble otherDouble)
            return this.multiply(otherDouble.getComplexValue());
        if (!(other instanceof MyComplexNumber otherComplex))
            throw new ArithmeticException(EXCEPT_MESSAGE);

        MyDouble newReal = (MyDouble) real.multiply(otherComplex.real).minus(imag.multiply(otherComplex.imag));
        MyDouble newImaginary = (MyDouble) real.multiply(otherComplex.imag).add(imag.multiply(otherComplex.real));

        return createComplexNumber(newReal, newImaginary);
    }

    /**
     * @param other  Complex number to divide
     * @return the quotient of the complex numbers
     */
    @Override
    public MyNumber divide(MyNumber other) {
        if (other instanceof MyDouble otherDouble)
            return this.divide(otherDouble.getComplexValue());
        if (!(other instanceof MyComplexNumber otherComplex))
            throw new ArithmeticException(EXCEPT_MESSAGE);

        MyNumber denominator = otherComplex.real.multiply(otherComplex.real).add(otherComplex.imag.multiply(otherComplex.imag));
        MyNumber newReal = real.multiply(otherComplex.real).add(imag.multiply(otherComplex.imag)).divide(denominator);
        MyNumber newImaginary = imag.multiply(otherComplex.real).minus(real.multiply(otherComplex.imag)).divide(denominator);

        return createComplexNumber((MyDouble) newReal, (MyDouble) newImaginary);
    }

    /**
     * @return the hash code of the complex number
     */
    @Override
    public int hashCode() {
        return 31 * real.getDoubleValue().intValue() + imag.getDoubleValue().intValue();
    }

    @Override
    public MyNumber abs() {
        // We can cast to MyDouble since the real and imaginary parts are MyDouble
        // and since the we multiply and add them together it will be a MyDouble.
        // TODO: Use the MyNumber.sqrt() method that will be added in the future.
        return new MyDouble(Math.sqrt(((MyDouble) real.multiply(real).add(imag.multiply(imag))).getDoubleValue()));
    }
}
