package core.operation;

import core.Expression;
import core.number.MyNumber;

/**
 * This class represents the absolute value operation.
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 */
public final class AbsoluteValue extends UnaryOperation {

    /**
     * Class constructor specifying the expression to take the absolute value of.
     *
     * @param arg The Expression to take the absolute value of
     */
    public AbsoluteValue(Expression arg) {
        super(arg);
        symbol = "abs";
    }

    /**
     * This method returns the absolute value of the argument.
     *
     * @param arg The argument of the operation
     * @return The absolute value of the argument
     */
    @Override
    public MyNumber op(MyNumber arg) {
        return arg.abs();
    }
}
