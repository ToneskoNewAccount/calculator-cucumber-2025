package core.visitor;

import core.number.MyNumber;
import core.operation.BinaryOperation;
import core.operation.UnaryOperation;

/**
 * Printer is a concrete visitor that serves to
 * convert an expression in string with a specific notation.
 */
public class Printer extends Visitor {

    /**
     * The result of the printer will be stored in this private variable
     */
    private String computedResult;

    /**
     * The notation to convert the expression in
     */
    private Notation notation;

    /**
     * Creates a {@code Printer} with the specified notation.
     *
     * @param notation the {@link Notation} that determines the notation to convert the expression in
     */
    public Printer(Notation notation) {
        this.notation = notation;
    }

    /**
     * getter method to obtain the result of the printer.
     *
     * @return a String object containing the result of the printer
     */
    public String getResult() {
        return computedResult;
    }

    /**
     * setter method to set the notation of the printer.
     *
     * @param notation the {@link Notation} that determines the notation to convert the expression in
     */
    public void setNotation(Notation notation) {
        this.notation = notation;
    }

    /**
     * Use the visitor design pattern to visit a number.
     *
     * @param n The number being visited
     */
    public void visit(MyNumber n) {
        computedResult = n.toString();
    }

    /**
     * Use the visitor design pattern to visit an operation.
     *
     * @param o The operation being visited.
     */
    public void visit(BinaryOperation o) {
        String result = getStartNotation(o.toString());

        for (int i = 0; i < o.args.size(); i++) {
            o.args.get(i).accept(this);
            result += computedResult;

            if (i < o.args.size() - 1) {
                result += getOperatorNotation(o.toString());
            }
        }

        result += getEndNotation(o.toString());
        computedResult = result;
    }

    /**
     * Get the start of the notation of an operation.
     *
     * @param symbol The symbol of the operation.
     * @return The start notation of the operation.
     */
    private String getStartNotation(String symbol) {
        switch (notation) {
            case PREFIX:
                return symbol + " (";
            case INFIX:
                return "( ";
            default:
                return "(";
        }
    }

    /**
     * Get the end of the notation of an operation.
     *
     * @param symbol The symbol of the operation.
     * @return The end notation of the operation.
     */
    private String getEndNotation(String symbol) {
        switch (notation) {
            case PREFIX:
                return ")";
            case INFIX:
                return " )";
            default:
                return ") " + symbol;
        }
    }

    /**
     * Get the operator notation of an operation.
     *
     * @param symbol The symbol of the operation.
     * @return The operator notation of the operation.
     */
    private String getOperatorNotation(String symbol) {
        return notation == Notation.INFIX ? " " + symbol + " " : ", ";
    }


    /**
     * Use the visitor design pattern to visit an operation.
     *
     * @param o The operation being visited.
     */
    public void visit(UnaryOperation o) {

        String result;
        if (notation == Notation.INFIX) {
            result = o.toString() + " ";

            if (!(o.arg instanceof BinaryOperation)) {
                result += "( ";
            }

        } else {
            result = getStartNotation(o.toString());
        }

        o.arg.accept(this);
        result += computedResult;


        if (notation == Notation.INFIX) {
            if (!(o.arg instanceof BinaryOperation)) {
                result += " )";
            }

        } else {
            result += getEndNotation(o.toString());
        }

        computedResult = result;
    }
}
