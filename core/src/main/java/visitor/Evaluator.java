package visitor;

import calculator.Expression;
import calculator.Matrix;
import calculator.MyNumber;
import calculator.Operation;

import java.util.ArrayList;

/**
 * Evaluation is a concrete visitor that serves to
 * compute and evaluate the results of arithmetic expressions.
 */
public class Evaluator extends Visitor {

    /**
     * Default constructor of the class. Does not initialise anything.
     */
    public Evaluator() {
    }

    /**
     * The result of the evaluation will be stored in this private variable
     */
    private double computedValue;

    /**
     * The result of the evaluation for matrix expressions will be stored in this variable.
     */
    private Matrix computedMatrix;

    /**
     * getter method to obtain the result of the evaluation
     *
     * @return an Double object containing the result of the evaluation
     */
    public double getResult() {
        return computedValue;
    }

    /**
     * Use the visitor design pattern to visit a number.
     *
     * @param n The number being visited
     */
    public void visit(MyNumber n) {
        computedValue = n.getValue();
    }

    /**
     * Use the visitor design pattern to visit an operation
     *
     * @param o The operation being visited
     */
    public void visit(Operation o) {
        ArrayList<Double> evaluatedArgs = new ArrayList<>();
        //first loop to recursively evaluate each subexpression
        for (Expression a : o.args) {
            a.accept(this);
            evaluatedArgs.add(computedValue);
        }
        //second loop to accumulate all the evaluated subresults
        double temp = evaluatedArgs.get(0);
        int max = evaluatedArgs.size();
        for (int counter = 1; counter < max; counter++) {
            temp = o.op(temp, evaluatedArgs.get(counter));
        }
        // store the accumulated result
        computedValue = temp;
    }

    /**
     * Getter method to obtain the matrix result of the evaluation.
     *
     * @return a Matrix containing the matrix result of the evaluation.
     */
    public Matrix getMatrixResult() {
        return computedMatrix;
    }

    /**
     * Use the visitor design pattern to visit a matrix.
     * This method stores the visited matrix as the computed matrix result.
     * @param m The matrix being visited.
     */
    @Override
    public void visit(Matrix m) {
        computedMatrix = m;
    }

}
