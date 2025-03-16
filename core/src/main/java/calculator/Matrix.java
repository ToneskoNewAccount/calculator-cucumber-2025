package calculator;

import visitor.Visitor;

import java.util.Locale;

/**
 * Represents a 2D matrix of doubles, supporting addition, subtraction,
 * multiplication, transposition, and inversion using Gauss-Jordan elimination.
 * <p>
 * This class does not provide a parse method; you must supply the data
 * via the constructor.
 * </p>
 */
public class Matrix implements Expression {

    /**
     * The internal 2D array storing the matrix data.
     */
    private final double[][] data;

    /**
     * The number of rows in this matrix.
     */
    private final int rows;

    /**
     * The number of columns in this matrix.
     */
    private final int cols;

    /**
     * Constructs a Matrix from a 2D double array.
     * All rows must have the same length.
     *
     * @param inputData the 2D double array representing the matrix
     * @throws IllegalArgumentException if any row has a different length
     */
    public Matrix(double[][] inputData) {
        if (inputData == null || inputData.length == 0) {
            throw new IllegalArgumentException("Input data must be non-null and have at least one row.");
        }
        this.rows = inputData.length;
        this.cols = inputData[0].length;
        this.data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (inputData[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
            System.arraycopy(inputData[i], 0, this.data[i], 0, cols);
        }
    }

    /**
     * Returns the number of rows in this matrix.
     *
     * @return the row count
     */
    public int getRowCount() {
        return rows;
    }

    /**
     * Returns the number of columns in this matrix.
     *
     * @return the column count
     */
    public int getColCount() {
        return cols;
    }

    /**
     * Adds this matrix to another matrix of the same dimensions.
     *
     * @param other the matrix to add
     * @return a new Matrix representing the sum
     * @throws IllegalArgumentException if dimensions do not match
     */
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Incompatible dimensions for addition.");
        }
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = this.data[i][j] + other.data[i][j];
            }
        }
        return new Matrix(result);
    }

    /**
     * Subtracts another matrix from this matrix.
     *
     * @param other the matrix to subtract
     * @return a new Matrix representing the difference
     * @throws IllegalArgumentException if dimensions do not match
     */
    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Incompatible dimensions for subtraction.");
        }
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = this.data[i][j] - other.data[i][j];
            }
        }
        return new Matrix(result);
    }

    /**
     * Multiplies this matrix by another matrix.
     *
     * @param other the matrix to multiply
     * @return a new Matrix representing the product
     * @throws IllegalArgumentException if the number of columns of this matrix
     *                                  does not match the number of rows of the other
     */
    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Incompatible dimensions for multiplication.");
        }
        double[][] result = new double[rows][other.cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    /**
     * Returns the transpose of this matrix.
     *
     * @return a new Matrix representing the transpose
     */
    public Matrix transpose() {
        double[][] transposed = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = data[i][j];
            }
        }
        return new Matrix(transposed);
    }

    /**
     * Inverts this matrix using Gauss-Jordan elimination.
     * This matrix must be square and non-singular.
     *
     * @return a new Matrix representing the inverse
     * @throws IllegalArgumentException if the matrix is not square
     * @throws ArithmeticException      if the matrix is singular (non-invertible)
     */
    public Matrix inverse() {
        validateSquareMatrix();
        double[][] augmented = createAugmentedMatrix();
        applyGaussJordanElimination(augmented);
        double[][] inverseData = extractInverse(augmented);
        return new Matrix(inverseData);
    }

    private void validateSquareMatrix() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be square to be inverted.");
        }
    }

    private double[][] createAugmentedMatrix() {
        int n = rows;
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(data[i], 0, augmented[i], 0, n);
            augmented[i][n + i] = 1.0;
        }
        return augmented;
    }

    private void applyGaussJordanElimination(double[][] augmented) {
        int n = rows;
        for (int i = 0; i < n; i++) {
            int pivotRow = findPivotRow(augmented, i, n);
            swapRowsIfNeeded(augmented, i, pivotRow);
            normalizePivotRow(augmented, i);
            eliminateOtherRows(augmented, i, n);
        }
    }

    private int findPivotRow(double[][] augmented, int startRow, int n) {
        int pivot = startRow;
        double max = Math.abs(augmented[startRow][startRow]);
        for (int j = startRow + 1; j < n; j++) {
            double value = Math.abs(augmented[j][startRow]);
            if (value > max) {
                max = value;
                pivot = j;
            }
        }
        if (Math.abs(augmented[pivot][startRow]) < 1e-12) {
            throw new ArithmeticException("Matrix is singular (pivot is zero).");
        }
        return pivot;
    }

    private void swapRowsIfNeeded(double[][] augmented, int currentRow, int pivotRow) {
        if (pivotRow != currentRow) {
            double[] temp = augmented[currentRow];
            augmented[currentRow] = augmented[pivotRow];
            augmented[pivotRow] = temp;
        }
    }

    private void normalizePivotRow(double[][] augmented, int pivotIndex) {
        int n = rows;
        double pivotVal = augmented[pivotIndex][pivotIndex];
        for (int j = 0; j < 2 * n; j++) {
            augmented[pivotIndex][j] /= pivotVal;
        }
    }

    private void eliminateOtherRows(double[][] augmented, int pivotIndex, int n) {
        for (int row = 0; row < n; row++) {
            if (row != pivotIndex) {
                double factor = augmented[row][pivotIndex];
                for (int col = 0; col < 2 * n; col++) {
                    augmented[row][col] -= factor * augmented[pivotIndex][col];
                }
            }
        }
    }

    private double[][] extractInverse(double[][] augmented) {
        int n = rows;
        double[][] inverseData = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmented[i], n, inverseData[i], 0, n);
        }
        return inverseData;
    }

    /**
     * Accepts a Visitor for the visitor design pattern.
     *
     * @param v the Visitor
     */
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * Returns a string representation of the matrix using Locale.US for consistent decimal points.
     *
     * @return the string representation of this matrix
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format(Locale.US, "%.1f", data[i][j]));
                if (j < cols - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
            if (i < rows - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
