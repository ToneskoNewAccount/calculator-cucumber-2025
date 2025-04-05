package core.operation;

import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyNumber;
import core.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * BinaryOperation is an abstract class that represents arithmetic binary operations.
 */
public abstract class BinaryOperation extends Operation {

    /**
     * The list of expressions passed as an argument to the arithmetic operation
     */
    public List<Expression> args;

    /**
     * The neutral element of the operation (e.g. 1 for *, 0 for +)
     */
    protected double neutral;

    /**
     * It is not allowed to construct a binary operation with a null list of expressions.
     * Note that it is allowed to have an EMPTY list of arguments.
     *
     * @param elist The list of expressions passed as argument to the arithmetic operation
     * @throws IllegalConstruction Exception thrown if a null list of expressions is passed as argument
     */
    protected BinaryOperation(List<Expression> elist)
            throws IllegalConstruction {
        if (elist == null) {
            throw new IllegalConstruction();
        } else {
            args = new ArrayList<>(elist);
        }
    }

    /**
     * getter method to return the number of arguments of an arithmetic binary operation.
     *
     * @return The number of arguments of the arithmetic operation.
     */
    public List<Expression> getArgs() {
        return args;
    }

    /**
     * Abstract method representing the actual binary arithmetic operation to compute
     *
     * @param l first argument of the binary operation
     * @param r second argument of the binary operation
     * @return result of computing the binary operation
     */
    public abstract MyNumber op(MyNumber l, MyNumber r);

    /**
     * Add more parameters to the existing list of parameters
     *
     * @param params The list of parameters to be added
     */
    public void addMoreParams(List<Expression> params) {
        args.addAll(params);
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
     * Method to compare the arguments of two binary operations.
     * This method assumes that the other operation is also a BinaryOperation.
     *
     * @param o The object to compare with
     * @return The result of the equality comparison
     */
    public boolean compareArgs(Operation o) {
        BinaryOperation other = (BinaryOperation) o;
        return this.args.equals(other.getArgs());
    }

    /**
     * The method hashCode needs to be overridden it the equals method is overridden;
     * otherwise there may be problems when you use your object in hashed collections
     * such as HashMap, HashSet, LinkedHashSet.
     *
     * @return The result of computing the hash.
     */
    @Override
    public int hashCode() {
        int result = 5, prime = 31;
        result = prime * result + (int) neutral;
        result = prime * result + symbol.hashCode();
        result = prime * result + args.hashCode();
        return result;
    }
}
