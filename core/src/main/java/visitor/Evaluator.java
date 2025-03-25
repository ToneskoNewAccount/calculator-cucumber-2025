package visitor;

import calculator.Expression;
import calculator.LinearSystemExpression;
import calculator.MyNumber;
import calculator.Operation;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Evaluation is a concrete visitor that serves to
 * compute and evaluate the results of arithmetic expressions.
 */
public class Evaluator extends Visitor {

    /**
     * Default constructor of the class. Does not initialise anything.
     */
    public Evaluator() {
        // No initialization required.
    }

    /**
     * The result of the evaluation will be stored in this private variable
     */
    private double computedValue;
    /**
     * The result of the linear system is stored in this variable.
     */
    private double[] computedVector;
    private static final Logger logger = Logger.getLogger(Evaluator.class.getName());



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
     * Accessor method to obtain the solution vector of the evaluated system of equations.
     *
     * @return an array of doubles containing the solution to the linear system.
     */
    public double[] getResultVector() {
        return computedVector;
    }

    /**
     * Visits an expression representing a linear system.
     * Attempts to solve the system and stores the solution vector.
     * If the system is inconsistent (no solutions), the vector is set to null and a warning is logged.
     *
     * @param expr The linear system expression.
     */

    @Override
    public void visit(LinearSystemExpression expr) {
        try {
            computedVector = calculator.LinearEquationSolver.solve(expr.getMatrix(), expr.getVector());
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No solutions")) {
                computedVector = null;
                logger.warning("No solutions for this linear system.");
            } else {
                throw e;
            }
        }
    }

}
