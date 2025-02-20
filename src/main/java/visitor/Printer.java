package visitor;

import calculator.MyNumber;
import calculator.Notation;
import calculator.Operation;

/**
 * Printer is a concrete visitor that serves to
 * convert an expression in string with a specific notation.
 */
public class Printer extends Visitor {

    /** The result of the printer will be stored in this private variable */
    private String computedResult;

    /** The notation to convert the expression in */
    private Notation notation;

    /** Creates a {@code Printer} with the specified notation.
     *
     * @param notation the {@link Notation} that determines the notation to convert the expression in
     */
    public Printer(Notation notation) {
        this.notation = notation;
    }

    /** getter method to obtain the result of the printer.
     *
     * @return a String object containing the result of the printer
     */
    public String getResult() { return computedResult; }

    /** setter method to set the notation of the printer.
     *
     * @param notation the {@link Notation} that determines the notation to convert the expression in
     */
    public void setNotation(Notation notation) {
        this.notation = notation;
    }

    /** Use the visitor design pattern to visit a number.
     *
     * @param n The number being visited
     */
    public void visit(MyNumber n) {
        computedResult = Double.toString(n.getValue());
    }

    /** 
     * Use the visitor design pattern to visit an operation.
     * 
     * @param o The operation being visited.
     */
    public void visit(Operation o) {
        String result = getStartNotation(o);

        for (int i = 0; i < o.args.size(); i++) {
            o.args.get(i).accept(this);
            result += computedResult;

            if (i < o.args.size() - 1) {
                result += getOperatorNotation(o);
            }
        }

        result += getEndNotation(o);
        computedResult = result;
    }

    /** 
     * Get the start of the notation of an operation.
     * 
     * @param o The operation being visited.
     * @return The start notation of the operation.
     */
    private String getStartNotation(Operation o) {
        switch (notation) {
            case PREFIX: return o.toString() + " (";
            case INFIX: return "( ";
            default: return "(";
        }
    }

    /** 
     * Get the end of the notation of an operation.
     * 
     * @param o The operation being visited.
     * @return The end notation of the operation.
     */
    private String getEndNotation(Operation o) {
        switch (notation) {
            case PREFIX: return ")";
            case INFIX: return " )";
            default: return ") " + o.toString();
        }
    }

    /** 
     * Get the operator notation of an operation.
     * 
     * @param o The operation being visited.
     * @return The operator notation of the operation.
     */
    private String getOperatorNotation(Operation o) {
        return notation == Notation.INFIX ? " " + o.toString() + " " : ", ";
    }
}
