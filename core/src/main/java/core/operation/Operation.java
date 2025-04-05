package core.operation;

import core.Expression;
import core.number.MyNumber;

/**
 * Operation is an abstract class that represents arithmetic operations,
 * which are a special kind of Expressions, just like numbers are.
 *
 * @see Expression
 * @see MyNumber
 */
public abstract class Operation implements Expression {

    /**
     * The character used to represent the arithmetic operation (e.g. "+", "*")
     */
    protected String symbol;

    /**
     * Convert the arithmetic operation into a String to allow it to be printed.
     *
     * @return The String that is the result of the conversion.
     */
    @Override
    public final String toString() {
        return symbol;
    }

    /**
     * Two operation objects are equal if their  arguments are equal and they correspond to the same operation.
     *
     * @param o The object to compare with
     * @return The result of the equality comparison
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false; // No object should be equal to null

        if (this == o) return true; // If it's the same object, they're obviously equal

        if (getClass() != o.getClass())
            return false; // getClass() instead of instanceof() because an addition is not the same as a multiplication

        return compareArgs((Operation) o); // Compare the arguments of the operations
    }

    /**
     * Abstract method to compare the arguments of two operations.
     * This method is implemented in the subclasses of Operation.
     *
     * @param o The operation to compare with
     * @return The result of the comparison
     */
    protected abstract boolean compareArgs(Operation o);
}
