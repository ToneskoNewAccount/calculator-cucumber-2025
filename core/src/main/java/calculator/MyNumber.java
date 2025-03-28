package calculator;

import visitor.Visitor;

/**
 * MyNumber is a concrete class that represents arithmetic numbers,
 * which are a special kind of Expressions, just like operations are.
 *
 * @see Expression
 * @see Operation
 */
public abstract class MyNumber implements Expression {

    /**
     * accept method to implement the visitor design pattern to traverse arithmetic expressions.
     * Each number will pass itself to the visitor object to get processed by the visitor.
     *
     * @param v The visitor object
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * Convert a number into a String to allow it to be printed.
     *
     * @return The String that is the result of the conversion.
     */
    @Override
    public abstract String toString();

    /**
     * Two MyNumber expressions are equal if the values they contain are equal
     * @param other The object to compare to
     * @return A boolean representing the result of the equality test
     */
    public abstract boolean equals(Object other);

    /**
     * Add this MyNumber with another one
     * @param other The other MyNumber object to perform the operation with
     * @return The result of the operation
     */
    public abstract MyNumber add(MyNumber other);

    /**
     * Subtract this MyNumber with another one
     * @param other The other MyNumber object to perform the operation with
     * @return The result of the operation
     */
    public abstract MyNumber minus(MyNumber other);

    /**
     * Multiply this MyNumber with another one
     * @param other The other MyNumber object to perform the operation with
     * @return The result of the operation
     */
    public abstract MyNumber multiply(MyNumber other);
    /**
     * Divide this MyNumber with another one
     * @param other The other MyNumber object to perform the operation with
     * @return The result of the operation
     */
    public abstract MyNumber divide(MyNumber other);

    /**
     * The method hashCode needs to be overridden it the equals method is overridden;
     * otherwise there may be problems when you use your object in hashed collections
     * such as HashMap, HashSet, LinkedHashSet.
     *
     * @return The result of computing the hash.
     */
    @Override
    public abstract int hashCode();

}
