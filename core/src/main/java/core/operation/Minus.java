package core.operation;

import java.util.List;

import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyNumber;

/**
 * This class represents the arithmetic operation "-".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 *
 * @see BinaryOperation
 * @see Plus
 * @see Times
 * @see Divides
 */
public final class Minus extends BinaryOperation {
    /**
     * Class constructor specifying a number of Expressions to subtract.
     *
     * @param elist The list of Expressions to subtract
     * @throws IllegalConstruction If an empty list of expressions if passed as parameter
     */
    public Minus(List<Expression> elist) throws IllegalConstruction {
        super(elist);
        symbol = "-";
        neutral = 0;
    }

    /**
     * The actual computation of the (binary) arithmetic subtraction of two doubles
     *
     * @param l The first double
     * @param r The second double that should be subtracted from the first
     * @return The double that is the result of the subtraction
     */
    @Override
    public MyNumber op(MyNumber l, MyNumber r) {
        return l.minus(r);
    }
}
