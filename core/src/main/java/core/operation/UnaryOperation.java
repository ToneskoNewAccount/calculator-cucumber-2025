package core.operation;

import core.Expression;
import core.number.MyNumber;
import core.visitor.Visitor;

/**
 * UnaryOperation is an abstract class that represents unary operations,
 * which are a special kind of Expressions, just like numbers are.
 *
 * @see Expression
 * @see MyNumber
 */
public abstract class UnaryOperation extends Operation {

    /**
     * An expression passed as an argument to the unary operation.
     */
    public Expression arg;

    /**
     * Constructor for the UnaryOperation class.
     *
     * @param arg The argument of the unary operation
     */
    protected UnaryOperation(Expression arg) {
        this.arg = arg;
    }

    /**
     * getter method to return the argument of an unary operation.
     *
     * @return The argument of the unary operation.
     */
    public Expression getArg() {
        return arg;
    }

    /**
     * Abstract method representing the actual unary operation to compute
     *
     * @param arg The argument of the unary operation
     * @return The result of computing the unary operation
     */
    public abstract MyNumber op(MyNumber arg);

    /**
     * setter method to set the argument of an unary operation.
     *
     * @param arg The argument of the unary operation.
     */
    public void setArg(Expression arg) {
        this.arg = arg;
    }

    /**
     * Accept method to implement the visitor design pattern to traverse arithmetic expressions.
     * It passes itself to the visitor object to get processed by the visitor object.
     *
     * @param v The visitor object
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * Method to compare the arguments of two unary operations.
     * This method assumes that the other operation is also a UnaryOperation.
     *
     * @param o The object to compare with
     * @return The result of the equality comparison
     */
    public boolean compareArgs(Operation o) {
        UnaryOperation other = (UnaryOperation) o;
        return arg.equals(other.arg);
    }
}
