package calculator;

import java.util.List;

/**
 * This class represents the arithmetic multiplication operation "*".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 *
 * @see Operation
 * @see Minus
 * @see Plus
 * @see Divides
 */
public final class Times extends Operation {
    /**
     * Class constructor specifying a number of Expressions to multiply.
     *
     * @param elist The list of Expressions to multiply
     * @throws IllegalConstruction If an empty list of expressions if passed as parameter
     */
    public Times(List<Expression> elist) throws IllegalConstruction {
        super(elist);
        symbol = "*";
        neutral = 1;
    }

    /**
     * The actual computation of the (binary) arithmetic multiplication of two doubles
     *
     * @param l The first double
     * @param r The second double that should be multiplied with the first
     * @return The double that is the result of the multiplication
     */
    @Override
    public MyNumber op(MyNumber l, MyNumber r) {
        return r.multiply(l);
    }
}
