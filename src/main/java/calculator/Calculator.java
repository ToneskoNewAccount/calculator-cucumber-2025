package calculator;

import visitor.Evaluator;
import visitor.Printer;
import visitor.Counter;
import visitor.Counter.CounterMode;

/**
 * This class represents the core logic of a Calculator.
 * It can be used to print and evaluate artihmetic expressions.
 *
 * @author tommens
 */
public class Calculator {

    /**
     * Default constructor of the class.
     * Does nothing since the class does not have any variables that need to be initialised.
     */
    public Calculator() {}

    /*
     For the moment the calculator only contains a print method and an eval method
     It would be useful to complete this with a read method, so that we would be able
     to implement a full REPL cycle (Read-Eval-Print loop) such as in Scheme, Python, R and other languages.
     To do so would require to implement a method with the following signature, converting an input string
     into an arithmetic expression:
     public Expression read(String s)
    */

    /**
     * Prints an arithmetic expression provided as input parameter with the infix notation.
     * @param e the arithmetic Expression to be printed
     * @see #print(Expression, Notation)
     * @see #printExpressionDetails(Expression) 
     */
    public void print(Expression e) {
        print(e, Notation.INFIX);
    }

    /**
     * Prints an arithmetic expression provided as input parameter with a specific notation.
     * @param e the arithmetic Expression to be printed
     * @see #print(Expression)
     * @see #printExpressionDetails(Expression) 
     */
    public void print(Expression e, Notation n) {
        Printer p = new Printer(n);
        e.accept(p);
        System.out.println("The result of evaluating expression " + p.getResult());
        System.out.println("is: " + eval(e) + ".");
        System.out.println();
    }

    /**
     * Prints verbose details of an arithmetic expression provided as input parameter with the infix notation.
     * @param e the arithmetic Expression to be printed
     * @see #printExpressionDetails(Expression, Notation)
     */
    public void printExpressionDetails(Expression e) {
        printExpressionDetails(e, Notation.INFIX);
    }

    /**
     * Prints verbose details of an arithmetic expression provided as input parameter with a specific notation.
     * @param e the arithmetic Expression to be printed
     * @see #print(Expression, Notation)
     */
    public void printExpressionDetails(Expression e, Notation n) {
        print(e, n);

        Counter c = new Counter(CounterMode.DEPTH);
        e.accept(c);
        System.out.print("It contains " + c.getResult() + " levels of nested expressions, ");

        c.setMode(CounterMode.OPERATIONS);
        e.accept(c);
        System.out.print(c.getResult() + " operations");

        c.setMode(CounterMode.NUMBERS);
        e.accept(c);
        System.out.println(" and " + c.getResult() + " numbers.");

        System.out.println();
    }

    /**
     * Evaluates an arithmetic expression and returns its result
     * @param e the arithmetic Expression to be evaluated
     * @return The result of the evaluation
     */
    public double eval(Expression e) {
        // create a new visitor to evaluate expressions
        Evaluator v = new Evaluator();
        // and ask the expression to accept this visitor to start the evaluation process
        e.accept(v);
        // and return the result of the evaluation at the end of the process
        return v.getResult();
    }

    /*
     We could also have other methods, e.g. to verify whether an expression is syntactically correct
     public Boolean validate(Expression e)
     or to simplify some expression
     public Expression simplify(Expression e)
    */
}
