package visitor;

import calculator.Expression;
import calculator.MyNumber;
import calculator.Operation;

/**
 * Counter is a concrete visitor that serves to
 * count various aspects of arithmetic expression.
 * It can count:
 * <ul>
 *     <li>The depth of the expression tree</li>
 *     <li>The number of operations in the expression</li>
 *     <li>The number of numeric values in the expression</li>
 * </ul>
 * <p>
 * The specific metric to compute is determined using the {@link CounterMode} enum.
 * </p>
 */
public class Counter extends Visitor {

    /**
     * The result of the count will be stored in this private variable
     */
    private int computedResult;

    /**
     * The mode of the counter that will determine what aspect of the expression to count
     */
    private CounterMode mode;

    /**
     * Creates a {@code Counter} with the specified counting mode.
     *
     * @param mode the {@link CounterMode} that determines what aspect of the expression to count
     */
    public Counter(CounterMode mode) {
        this.mode = mode;
    }

    /**
     * Sets the mode of the counter.
     *
     * @param mode the {@link CounterMode} that determines what aspect of the expression to count
     */
    public void setMode(CounterMode mode) {
        this.mode = mode;
    }

    /**
     * getter method to obtain the result of the count.
     *
     * @return an Integer object containing the result of the count
     */
    public Integer getResult() {
        return computedResult;
    }

    /**
     * Use the visitor design pattern to visit a number.
     *
     * @param n The number being visited
     */
    public void visit(MyNumber n) {
        computedResult = (mode == CounterMode.DEPTH || mode == CounterMode.OPERATIONS) ? 0 : 1;
    }

    /**
     * Use the visitor design pattern to visit an operation
     *
     * @param o The operation being visited
     */
    public void visit(Operation o) {
        int result = 0;

        for (Expression e : o.args) {
            e.accept(this);

            if (mode == CounterMode.DEPTH)
                result = Math.max(result, computedResult);
            else
                result += computedResult;
        }

        if (mode == CounterMode.DEPTH || mode == CounterMode.OPERATIONS)
            result++;

        computedResult = result;
    }

    /**
     * Represents the different types of counts that can be measured in an arithmetic expression.
     */
    public enum CounterMode {
        /**
         * Counts the maximum depth of the expression tree.
         */
        DEPTH,

        /**
         * Counts the number of operations (e.g., +, -, *, /) in the expression.
         */
        OPERATIONS,

        /**
         * Counts the number of numeric values in the expression.
         */
        NUMBERS
    }
}
