package calculator;

public class Matrix {
    private double[][] data;
    private int rows;
    private int cols;

    /**
     * Constructor that takes a 2D array of doubles.
     */
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length.");
            }
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    /**
     * Static method to parse a string of the form [[1,2],[3,4]]
     * and return a Matrix instance.
     */
    public static Matrix parse(String str) {
        // Remove any surrounding whitespace
        str = str.trim();

        // Example: "[[1,2],[3,4]]"
        // 1) remove the outer brackets
        if (str.startsWith("[[") && str.endsWith("]]")) {
            str = str.substring(2, str.length() - 2);
        } else {
            throw new IllegalArgumentException("Invalid format. Use [[a,b],[c,d]].");
        }

        // 2) split each row by "],["
        //    e.g. we get "1,2" and "3,4"
        String[] rowStrings = str.split("\\],\\[");

        double[][] parsedData = new double[rowStrings.length][];

        for (int i = 0; i < rowStrings.length; i++) {
            // For each row, split by comma
            String[] values = rowStrings[i].split(",");
            parsedData[i] = new double[values.length];
            for (int j = 0; j < values.length; j++) {
                parsedData[i][j] = Double.parseDouble(values[j]);
            }
        }

        return new Matrix(parsedData);
    }

    /**
     * Returns the number of rows.
     */
    public int getRowCount() {
        return rows;
    }

    /**
     * Returns the number of columns.
     */
    public int getColCount() {
        return cols;
    }

    /**
     * Adds this matrix to another (this + other).
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
     * Subtracts another matrix from this one (this - other).
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
     * Multiplies this matrix by another (this * other).
     */
    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Incompatible dimensions for multiplication.");
        }
        double[][] result = new double[this.rows][other.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    /**
     * Returns the transpose of this matrix (a new matrix).
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
     * Inverts a square matrix using Gauss-Jordan elimination.
     */
    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be square to be inverted.");
        }

        int n = rows;
        // Create the augmented matrix [A | I]
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = data[i][j];
            }
            for (int j = n; j < 2 * n; j++) {
                augmented[i][j] = (i == (j - n)) ? 1.0 : 0.0;
            }
        }

        // Apply Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Find the pivot with the greatest absolute value in column i
            int pivot = i;
            double max = Math.abs(augmented[i][i]);
            for (int j = i + 1; j < n; j++) {
                double value = Math.abs(augmented[j][i]);
                if (value > max) {
                    max = value;
                    pivot = j;
                }
            }
            if (Math.abs(augmented[pivot][i]) < 1e-12) {
                throw new ArithmeticException("Matrix is singular (pivot is zero).");
            }

            // Swap row i with the pivot row if necessary
            if (pivot != i) {
                double[] temp = augmented[i];
                augmented[i] = augmented[pivot];
                augmented[pivot] = temp;
            }

            // Normalize the pivot row
            double pivotVal = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivotVal;
            }

            // Eliminate column i in all other rows
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = augmented[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmented[j][k] -= factor * augmented[i][k];
                    }
                }
            }
        }

        // Extract the right side of the augmented matrix, which is the inverse
        double[][] inverseData = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmented[i], n, inverseData[i], 0, n);
        }

        return new Matrix(inverseData);
    }

    /**
     * toString() to represent the matrix as [[a,b],[c,d],...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);
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
