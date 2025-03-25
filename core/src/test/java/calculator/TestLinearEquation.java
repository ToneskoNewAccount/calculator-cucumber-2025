package calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import visitor.Evaluator;
import visitor.Printer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A comprehensive test class for LinearEquationSolver and its integration
 * with Evaluator for linear systems.
 *
 * <p>Note: This class is package-private (no 'public' keyword)
 * to avoid Sonar warnings about "Remove this 'public' modifier".</p>
 */
class TestLinearEquation {

    /**
     * Tests that an exception is thrown if matrixA is null.
     */
    @Test
    void testNullMatrix() {
        assertThrows(IllegalArgumentException.class, () -> {
            LinearEquationSolver.solve(null, new double[]{1, 2});
        }, "Expected an exception for null matrixA");
    }

    /**
     * Tests that an exception is thrown if matrixA is empty (zero rows).
     */
    @Test
    void testEmptyMatrix() {
        double[][] empty = new double[0][];
        assertThrows(IllegalArgumentException.class, () -> {
            LinearEquationSolver.solve(empty, new double[]{});
        }, "Expected an exception for empty matrixA");
    }

    /**
     * Tests that an exception is thrown if matrixA is non-rectangular
     * (i.e., rows have different lengths).
     */
    @Test
    void testNonRectangularMatrix() {
        double[][] nonRectangular = {
                {1, 2},
                {3, 4, 5} // 3 columns instead of 2
        };
        double[] b = {1, 2};

        assertThrows(IllegalArgumentException.class, () -> {
            LinearEquationSolver.solve(nonRectangular, b);
        }, "Expected an exception for non-rectangular matrixA");
    }

    /**
     * Tests a valid 2x2 system with a unique solution.
     * <p>
     * The system:
     * <pre>
     *   [1  2]   [x]   [ 5]
     *   [3  4] * [y] = [11]
     * </pre>
     * has the unique solution [1, 2].
     * </p>
     */
    @Test
    void testValidMatrix() {
        double[][] valid = {
                {1, 2},
                {3, 4}
        };
        double[] b = {5, 11};

        double[] sol = assertDoesNotThrow(() ->
                LinearEquationSolver.solve(valid, b)
        );
        // The expected solution for this system is [1, 2].
        assertArrayEquals(new double[]{1, 2}, sol, 1e-9,
                "Solution for a valid 2x2 system should be [1, 2]");
    }

    /**
     * Tests that an exception is thrown if the vector length does not match
     * the matrix's number of rows (dimension mismatch).
     */
    @Test
    void testSolveDimensionMismatch() {
        double[][] matrixA = {
                {1, 2},
                {3, 4}
        };
        // vectorB has 3 elements, but the matrix has 2 rows => mismatch
        double[] vectorB = {5, 11, 7};

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                LinearEquationSolver.solve(matrixA, vectorB)
        );
        assertTrue(ex.getMessage().contains("Dimensions of A and b do not match"),
                "Expected dimension mismatch error");
    }

    /**
     * Tests an inconsistent system (no solutions).
     * <p>
     * For example, the system:
     * <pre>
     *   1x + 1y = 3
     *   2x + 2y = 7
     * </pre>
     * is inconsistent.
     * </p>
     */
    @Test
    void testSolveNoSolution() {
        double[][] matrixA = {
                {1, 1},
                {2, 2}
        };
        double[] vectorB = {3, 7};

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                LinearEquationSolver.solve(matrixA, vectorB)
        );
        assertTrue(ex.getMessage().contains("No solutions"),
                "Expected 'No solutions' error for inconsistent system");
    }

    /**
     * Tests a rank-deficient system (infinite solutions).
     * <p>
     * Example:
     * <pre>
     *   1x + 2y + 3z = 4
     *   2x + 4y + 6z = 8
     * </pre>
     * This system has infinitely many solutions.
     * </p>
     */
    @Test
    void testSolveInfiniteSolutions() {
        double[][] matrixA = {
                {1, 2, 3},
                {2, 4, 6}
        };
        double[] vectorB = {4, 8};

        // Should not throw "No solutions". We expect a valid solution
        // (though there are infinitely many).
        double[] solution = LinearEquationSolver.solve(matrixA, vectorB);
        assertNotNull(solution, "Solution should not be null for infinite solutions");
        assertEquals(3, solution.length,
                "Solution length should match the number of columns in the matrix (3)");
    }

    /**
     * Tests visiting a LinearSystemExpression with an Evaluator,
     * covering visit(LinearSystemExpression).
     */
    @Test
    void testVisitLinearSystemExpression() {
        double[][] matrixA = {
                {1, 2},
                {3, 4}
        };
        double[] vectorB = {5, 11};

        // Create a LinearSystemExpression
        LinearSystemExpression expr = new LinearSystemExpression(matrixA, vectorB);

        // Create an Evaluator
        Evaluator evaluator = new Evaluator();

        // Accept the visitor => calls evaluator.visit(LinearSystemExpression)
        expr.accept(evaluator);

        // Retrieve the result
        double[] result = evaluator.getResultVector();
        assertNotNull(result, "Evaluator should produce a non-null result");
        assertEquals(2, result.length, "Result should have length 2");
        assertEquals(1.0, result[0], 1e-9);
        assertEquals(2.0, result[1], 1e-9);
    }

    /**
     * Tests that instantiating the private constructor of LinearEquationSolver
     * throws an AssertionError via an InvocationTargetException.
     *
     * @throws Exception if reflection fails unexpectedly.
     */
    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<LinearEquationSolver> constructor =
                LinearEquationSolver.class.getDeclaredConstructor();
        // Make it accessible
        constructor.setAccessible(true);

        // Now try to instantiate using a method reference
        InvocationTargetException thrown = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance,
                "Expected an InvocationTargetException when instantiating the private constructor"
        );

        // The real cause should be the AssertionError
        assertTrue(thrown.getCause() instanceof AssertionError,
                "Expected cause to be AssertionError");
        assertEquals("No instances allowed", thrown.getCause().getMessage(),
                "Expected the AssertionError message");
    }

    /**
     * Tests the backSubstitution method with a row that does not contain a pivot.
     * <p>
     * We create an augmented matrix (3 rows, 3 columns) where:
     *   - Row 0 is entirely zeros (including the constant term).
     *   - Row 1 has a pivot in column 0.
     *   - Row 2 has a pivot in column 1.
     * This simulates a situation where the first row has no pivot.
     * </p>
     */
    @Test
    void testBackSubstitutionWithRowWithoutPivot() {
        // Build an artificial augmented matrix with 3 rows and 3 columns:
        //   - 2 unknowns (colCount = 2)
        //   - The last column is the constant term
        double[][] augmentedMatrix = new double[3][3];

        // Row 0: pivotless row (all coefficients = 0, constant = 0)
        augmentedMatrix[0][0] = 0;
        augmentedMatrix[0][1] = 0;
        augmentedMatrix[0][2] = 0;

        // Row 1: pivot at column 0
        augmentedMatrix[1][0] = 1;   // pivot
        augmentedMatrix[1][1] = 0;
        augmentedMatrix[1][2] = 3;   // constant

        // Row 2: pivot at column 1
        augmentedMatrix[2][0] = 0;
        augmentedMatrix[2][1] = 1;   // pivot
        augmentedMatrix[2][2] = 4;   // constant

        // Simulate that the forward elimination found pivotRowIndex = 3
        // (all rows considered, even if row 0 has no pivot)
        int pivotRowIndex = 3;
        int colCount = 2; // 2 unknowns

        // Direct call to backSubstitution
        double[] solution = LinearEquationSolver.backSubstitution(augmentedMatrix, pivotRowIndex, colCount);

        // We expect:
        // - Row 0 is ignored (no pivot).
        // - From row 2 => solution[1] = 4
        // - From row 1 => solution[0] = 3
        assertNotNull(solution, "The solution must not be null");
        assertEquals(2, solution.length, "The solution must have 2 elements");
        assertEquals(3.0, solution[0], 1e-9, "solution[0] should be 3.0");
        assertEquals(4.0, solution[1], 1e-9, "solution[1] should be 4.0");
    }

    /**
     * Tests that Evaluator correctly computes the solution of a 2x2 linear system with a unique solution.
     * <p>
     * The system under test is:
     * <pre>
     *   1x + 2y = 5
     *   3x + 4y = 11
     * </pre>
     * which has the unique solution: x = 1, y = 2.
     * </p>
     */
    @Test
    void testVisitLinearSystemExpressionUniqueSolution() {
        // Define a 2x2 system with a unique solution
        double[][] matrix = { {1, 2}, {3, 4} };
        double[] vector = {5, 11};
        LinearSystemExpression expr = new LinearSystemExpression(matrix, vector);

        // Create an Evaluator instance and evaluate the expression
        Evaluator evaluator = new Evaluator();
        expr.accept(evaluator);

        // Retrieve the computed solution vector from the evaluator
        double[] solution = evaluator.getResultVector();
        assertNotNull(solution, "The solution vector should not be null.");
        assertEquals(2, solution.length, "The solution vector should have two elements.");
        assertEquals(1.0, solution[0], 1e-9, "The first variable (x) should be 1.0.");
        assertEquals(2.0, solution[1], 1e-9, "The second variable (y) should be 2.0.");
    }

    /**
     * Tests that Evaluator correctly handles an inconsistent linear system (no solutions).
     * <p>
     * For example:
     * <pre>
     *   1x + 1y = 3
     *   2x + 2y = 7
     * </pre>
     * is inconsistent, so the Evaluator should set the computed solution vector to null.
     * </p>
     */
    @Test
    void testVisitLinearSystemExpressionNoSolution() {
        // Define an inconsistent 2x2 system
        double[][] matrix = { {1, 1}, {2, 2} };
        double[] vector = {3, 7};
        LinearSystemExpression expr = new LinearSystemExpression(matrix, vector);

        // Create an Evaluator instance and evaluate the expression
        Evaluator evaluator = new Evaluator();
        expr.accept(evaluator);

        // For an inconsistent system, the Evaluator should set computedVector to null.
        double[] solution = evaluator.getResultVector();
        assertNull(solution, "The solution vector should be null for an inconsistent system.");
    }

    /**
     * Tests that the Printer correctly solves a 2x2 linear system and
     * converts the solution to a string.
     * <p>
     * The tested system is:
     * <pre>
     *   1x + 2y = 5
     *   3x + 4y = 11
     * </pre>
     * which has the unique solution [1.0, 2.0].
     * The expected printed result is "[1.0, 2.0]".
     * </p>
     */
    @Test
    void testVisitLinearSystemExpressionWithPrinter() {
        // Define a 2x2 system with a unique solution: x = 1.0 and y = 2.0
        double[][] matrix = { {1, 2}, {3, 4} };
        double[] vector = {5, 11};
        LinearSystemExpression expr = new LinearSystemExpression(matrix, vector);

        // Create a Printer with a specified notation (e.g., INFIX)
        Printer printer = new Printer(Notation.INFIX);

        // Visit the linear system expression with the printer
        expr.accept(printer);

        // Retrieve the computed result from the printer
        String printedResult = printer.getResult();
        assertNotNull(printedResult, "The computed result should not be null.");

        // Verify that the printed result matches the expected string "[1.0, 2.0]"
        assertEquals("[1.0, 2.0]", printedResult,
                "The printed result should be '[1.0, 2.0]' for the given system.");
    }
}
