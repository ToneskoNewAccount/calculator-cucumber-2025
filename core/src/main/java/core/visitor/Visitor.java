package core.visitor;

import core.operation.UnaryOperation;
import core.number.MyNumber;
import core.operation.BinaryOperation;

/**
 * Visitor design pattern
 */
public abstract class Visitor {

    /**
     * The Visitor can traverse a number (a subtype of Expression)
     *
     * @param n The number being visited
     */
    public abstract void visit(MyNumber n);

    /**
     * The Visitor can traverse an operation (a subtype of Expression)
     *
     * @param o The operation being visited
     */
    public abstract void visit(BinaryOperation o);

    /**
     * The Visitor can traverse an unary operation (a subtype of Expression)
     *
     * @param o The operation being visited
     */
    public abstract void visit(UnaryOperation o);
}
