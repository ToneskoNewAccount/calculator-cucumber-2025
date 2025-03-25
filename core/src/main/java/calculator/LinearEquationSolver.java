package calculator;

/**
 *Utility class for solving a system of linear equations
 *by elimination of Gauss-Jordan followed by retro-substitution.
 */
public final class LinearEquationSolver {

    /**
     * Private builder to prevent instantiation.
     */
    private LinearEquationSolver() {
        throw new AssertionError("No instances allowed");
    }

    /**
     * Solves a system of linear equations defined by a matrix of coefficients and a constant vector.
     *
     * @param matrixA  matrix of coefficients.
     * @param vectorB constant vector.
     * @return An array of duplicates containing the solution.
     * @throws IllegalArgumentException if the matrix is not rectangular, if the dimensions do not match,
     * or if the system has no solution.
     *
     */
    public static double[] solve(double[][] matrixA, double[] vectorB) {
        checkRectangular(matrixA);
        checkDimension(matrixA, vectorB);

        final int rowCount = matrixA.length;
        final int colCount = matrixA[0].length;

        double[][] augmentedMatrix = buildAugmentedMatrix(matrixA, vectorB);

        // Forward elimination (Gauss-Jordan)
        int pivotRowIndex = 0;
        int colIndex = 0;
        while (colIndex < colCount && pivotRowIndex < rowCount) {
            int bestPivotRowIndex = findPivotRow(augmentedMatrix, pivotRowIndex, colIndex);
            if (bestPivotRowIndex == -1) {
                // No pivot in this column -> free variable
                colIndex++;
                continue;
            }
            if (bestPivotRowIndex != pivotRowIndex) {
                swapRows(augmentedMatrix, pivotRowIndex, bestPivotRowIndex);
            }
            normalizePivotRow(augmentedMatrix, pivotRowIndex, colIndex);
            eliminateBelow(augmentedMatrix, pivotRowIndex, colIndex);

            pivotRowIndex++;
            colIndex++;
        }

        // Check for no solution
        checkNoSolution(augmentedMatrix);

        // Retro-substitution: the retro-substitution phase is broken down into a dedicated method.
        return backSubstitution(augmentedMatrix, pivotRowIndex, colCount);
    }

    /**
     * Extracts the retro-substitution phase in a separate method.
     * From the augmented matrix obtained after forward elimination, this method
     * Calculates the solution vector starting from the last pivot line and moving upwards.
     *
     * @param augmentedMatrix the augmented matrix .
     * @param pivotRowIndex   The index of lines with a pivot (result of the elimination in front).
     * @param colCount        The number of columns in the coefficient matrix.
     * @return An array of duplicates containing the solution.
     */
    static double[] backSubstitution(double[][] augmentedMatrix, int pivotRowIndex, int colCount) {
        double[] solution = new double[colCount];
        //Runs from the last pivot line to the first.
        for (int rowIndex = pivotRowIndex - 1; rowIndex >= 0; rowIndex--) {
            int leadColumnIndex = findLeadingColumn(augmentedMatrix, rowIndex, colCount);
            if (leadColumnIndex == -1) {
                //Line without pivot: we move on to the next one.
                continue;
            }
            double tempValue = augmentedMatrix[rowIndex][colCount];
            for (int c = leadColumnIndex + 1; c < colCount; c++) {
                tempValue -= augmentedMatrix[rowIndex][c] * solution[c];
            }
            solution[leadColumnIndex] = tempValue;
        }
        return solution;
    }

    /**
     *Verifies that matrix A is not zero and has at least one row.
     *
     * @param matrixA The matrix to check.
     * @throws IllegalArgumentException if the matrix is zero or empty .
     */
    private static void checkRectangular(double[][] matrixA) {
        if (matrixA == null || matrixA.length == 0) {
            throw new IllegalArgumentException("Matrix A must not be empty.");
        }
        int length = matrixA[0].length;
        for (double[] rowData : matrixA) {
            if (rowData.length != length) {
                throw new IllegalArgumentException("Matrix A must be rectangular.");
            }
        }
    }

    /**
     * Check that vector b has the same number of rows as matrix A.
     *
     * @param matrixA  matrix of coefficients.
     * @param vectorB constant vector .
     * @throws IllegalArgumentException If the dimensions do not match.
     */
    private static void checkDimension(double[][] matrixA, double[] vectorB) {
        if (vectorB == null || matrixA.length != vectorB.length) {
            throw new IllegalArgumentException("Dimensions of A and b do not match.");
        }
    }

    /**
     * Constructs the augmented matrix by concatenating the matrix A and the vector b.
     *
     * @param matrixA  matrix of coefficients.
     * @param vectorB constant vector.
     * @return the augmented matrix .
     */
    private static double[][] buildAugmentedMatrix(double[][] matrixA, double[] vectorB) {
        int rowCount = matrixA.length;
        int colCount = matrixA[0].length;
        double[][] augmentedMatrix = new double[rowCount][colCount + 1];
        for (int i = 0; i < rowCount; i++) {
            System.arraycopy(matrixA[i], 0, augmentedMatrix[i], 0, colCount);
            augmentedMatrix[i][colCount] = vectorB[i];
        }
        return augmentedMatrix;
    }

    /**
     * Finds the best pivot line in the given column, based on the startRowIndex.
     *
     * @param augmentedMatrix the augmented matrix.
     * @param startRowIndex   The starting point for research.
     * @param colIndex        The index of the column to be examined.
     * @return The pivot line index, or -1 if no pivot is found.
     */
    private static int findPivotRow(double[][] augmentedMatrix, int startRowIndex, int colIndex) {
        int pivotIndex = -1;
        double maxAbs = 1e-10;
        for (int r = startRowIndex; r < augmentedMatrix.length; r++) {
            double val = Math.abs(augmentedMatrix[r][colIndex]);
            if (val > maxAbs) {
                maxAbs = val;
                pivotIndex = r;
            }
        }
        return pivotIndex;
    }

    /**
     *Swaps two lines in the augmented matrix .
     *
     * @param augmentedMatrix the augmented matrix.
     * @param rowIndex1       The index of the first line.
     * @param rowIndex2       The index of the second line.
     */
    private static void swapRows(double[][] augmentedMatrix, int rowIndex1, int rowIndex2) {
        double[] temp = augmentedMatrix[rowIndex1];
        augmentedMatrix[rowIndex1] = augmentedMatrix[rowIndex2];
        augmentedMatrix[rowIndex2] = temp;
    }

    /**
     *Normalizes the pivot line by dividing all its elements by the value of the pivot.
     *
     * @param augmentedMatrix the augmented matrix.
     * @param pivotRowIndex   The Pivot Line Index.
     * @param colIndex        The pivot column.
     */
    private static void normalizePivotRow(double[][] augmentedMatrix, int pivotRowIndex, int colIndex) {
        double pivotValue = augmentedMatrix[pivotRowIndex][colIndex];
        int totalCols = augmentedMatrix[0].length;
        for (int c = colIndex; c < totalCols; c++) {
            augmentedMatrix[pivotRowIndex][c] /= pivotValue;
        }
    }

    /**
     * Eliminates coefficients below the pivot by subtracting a multiple from the pivot line.
     *
     * @param augmentedMatrix the augmented matrix.
     * @param pivotRowIndex   The Pivot Line Index.
     * @param colIndex        The pivot column.
     */
    private static void eliminateBelow(double[][] augmentedMatrix, int pivotRowIndex, int colIndex) {
        int rowCount = augmentedMatrix.length;
        int totalCols = augmentedMatrix[0].length;
        for (int r = pivotRowIndex + 1; r < rowCount; r++) {
            double factor = augmentedMatrix[r][colIndex];
            for (int c = colIndex; c < totalCols; c++) {
                augmentedMatrix[r][c] -= factor * augmentedMatrix[pivotRowIndex][c];
            }
        }
    }

    /**
     * Verifies that the system is not inconsistent by detecting a line
     *  with all zero coefficients and a non-zero constant.
     *
     * @param augmentedMatrix the augmented matrix.
     * @throws IllegalArgumentException if the system is inconsistent.
     */
    private static void checkNoSolution(double[][] augmentedMatrix) {
        int rowCount = augmentedMatrix.length;
        int colCount = augmentedMatrix[0].length - 1;
        for (int r = 0; r < rowCount; r++) {
            boolean allZero = true;
            for (int c = 0; c < colCount; c++) {
                if (Math.abs(augmentedMatrix[r][c]) > 1e-10) {
                    allZero = false;
                    break;
                }
            }
            if (allZero && Math.abs(augmentedMatrix[r][colCount]) > 1e-10) {
                throw new IllegalArgumentException("No solutions");
            }
        }
    }

    /**
     * Finds the first non-zero (pivot) column in the given row.
     *
     * @param augmentedMatrix the augmented matrix.
     * @param rowIndex       The index of the line to be examined.
     * @param numCols         The number of columns in the coefficient matrix.
     * @return The index of the pivot column, or -1 if there is none.
     */
    private static int findLeadingColumn(double[][] augmentedMatrix, int rowIndex, int numCols) {
        for (int c = 0; c < numCols; c++) {
            if (Math.abs(augmentedMatrix[rowIndex][c]) > 1e-10) {
                return c;
            }
        }
        return -1;
    }
}
