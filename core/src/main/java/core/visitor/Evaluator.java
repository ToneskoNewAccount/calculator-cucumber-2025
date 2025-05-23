package core.visitor;

import java.util.ArrayList;

import core.Expression;
import core.number.MyNumber;
import core.operation.BinaryOperation;
import core.operation.UnaryOperation;

/** Evaluation is a concrete visitor that serves to
 * compute and evaluate the results of arithmetic expressions.
 */
public class Evaluator extends Visitor {

    /**
     * Default constructor of the class. Does not initialise anything.
     */
    public Evaluator() {}

    /** The result of the evaluation will be stored in this private variable */
    private MyNumber computedValue;

    /** getter method to obtain the result of the evaluation
     *
     * @return an Double object containing the result of the evaluation
     */
    public MyNumber getResult() { return computedValue; }

    /** Use the visitor design pattern to visit a number.
     *
     * @param n The number being visited
     */
    public void visit(MyNumber n) {
        computedValue = n;
    }

    /** Use the visitor design pattern to visit an operation
     *
     * @param o The operation being visited
     */
    public void visit(BinaryOperation o) {
        ArrayList<MyNumber> evaluatedArgs = new ArrayList<>();
        //first loop to recursively evaluate each subexpression
        for(Expression a:o.args) {
            a.accept(this);
            evaluatedArgs.add(computedValue);
        }

        //second loop to accumulate all the evaluated subresults
        MyNumber temp = evaluatedArgs.get(0);
        int max = evaluatedArgs.size();
        for(int counter=1; counter<max; counter++) {
            temp = o.op(temp ,evaluatedArgs.get(counter));
        }
        // store the accumulated result
        computedValue = temp;

    }

    /** Use the visitor design pattern to visit an unary operation
     *
     * @param o The operation being visited
     */
    public void visit(UnaryOperation o) {
        o.arg.accept(this);
        computedValue = o.op(computedValue);
    }
}
