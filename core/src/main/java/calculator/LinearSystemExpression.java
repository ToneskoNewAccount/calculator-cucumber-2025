package calculator;

import visitor.Visitor;

public class LinearSystemExpression implements Expression {

    private final double[][] matrix;
    private final double[] vector;

    public LinearSystemExpression(double[][] matrix, double[] vector) {
        this.matrix = matrix;
        this.vector = vector;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[] getVector() {
        return vector;
    }

    @Override
    public void accept(Visitor v) {
        // This will call the visit method for a linear system in the visitor.
        v.visit(this);
    }
}