package calculator;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import visitor.Evaluator;

import java.util.List;

public class LinearEquationSolverSteps {

    private double[][] matrixA;
    private double[] vectorB;
    private double[] solution;           // Used by "When I solve the linear system"
    private double[] evaluatedSolution;  // Used by "When I evaluate this expression with an Evaluator"
    private Exception caughtException;


    // Steps for directly solving a linear system


    @Given("the matrix A")
    public void theMatrixA(DataTable dataTable) {
        matrixA = convertDataTableToMatrix(dataTable);
        System.out.println("Matrix A:");
        printMatrix(matrixA);
    }

    @Given("the vector b")
    public void theVectorB(DataTable dataTable) {
        vectorB = convertDataTableToVector(dataTable);
        System.out.println("Vector b:");
        printVector(vectorB);
    }

    @When("I solve the linear system")
    public void iSolveTheLinearSystem() {
        try {
            solution = LinearEquationSolver.solve(matrixA, vectorB);
            caughtException = null;
            System.out.println("Solution computed (direct solve):");
            printVector(solution);
        } catch (Exception e) {
            caughtException = e;
            solution = null;
            System.out.println("An exception was thrown: " + e.getMessage());
        }
    }

    @Then("the solution should be")
    public void theSolutionShouldBe(DataTable expectedTable) {
        assertNull(caughtException,
                "An exception was thrown: " + (caughtException != null ? caughtException.getMessage() : ""));
        double[] expected = convertDataTableToVector(expectedTable);
        System.out.println("Expected solution:");
        printVector(expected);

        assertEquals(expected.length, solution.length, "Solution length mismatch");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], solution[i], 1e-9, "Mismatch at index " + i);
        }
    }

    @Then("I should get an error {string}")
    public void iShouldGetAnError(String expectedMessage) {
        assertNotNull(caughtException, "Expected an exception but none was thrown.");
        assertTrue(caughtException.getMessage().contains(expectedMessage),
                "Expected error message to contain \"" + expectedMessage +
                        "\", but got: " + caughtException.getMessage());
    }

    @Then("I should get a valid solution")
    public void iShouldGetAValidSolution() {
        assertNull(caughtException,
                "An exception was thrown: " + (caughtException != null ? caughtException.getMessage() : ""));
        assertNotNull(solution, "Solution should not be null.");
    }


    // Steps for the scenario: "Evaluate a linear system expression"


    @Given("I have a linear system expression")
    public void iHaveALinearSystemExpression(DataTable dataTable) {
        // Parse the matrix as usual, but do not solve it directly here.
        matrixA = convertDataTableToMatrix(dataTable);
        System.out.println("Matrix A for linear system expression:");
        printMatrix(matrixA);
    }

    @When("I evaluate this expression with an Evaluator")
    public void iEvaluateThisExpressionWithAnEvaluator() {
        //  Create the expression
        LinearSystemExpression expr = new LinearSystemExpression(matrixA, vectorB);

        // Create an Evaluator (Visitor)
        Evaluator evaluator = new Evaluator();

        //  Call expr.accept(evaluator) => will invoke evaluator.visit(LinearSystemExpression)
        expr.accept(evaluator);

        //  Retrieve the solution computed by the Evaluator
        //  Make sure your Evaluator has a method like getLinearSystemSolution()
        evaluatedSolution = evaluator.getResultVector(); // or getLinearSystemSolution(), depending on your code
        System.out.println("Evaluator computed solution:");
        printVector(evaluatedSolution);
    }
    @Then("I should see the solution {string} \\(or something similar\\)")
    public void iShouldSeeTheSolution(String expectedString) {

        String[] parts = expectedString.split("\\s+");
        double[] expected = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            expected[i] = Double.parseDouble(parts[i]);
        }

        assertEquals(expected.length, evaluatedSolution.length,
                "Mismatch in evaluated solution length");
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], evaluatedSolution[i], 1e-9,
                    "Mismatch at index " + i + " in evaluated solution");
        }
    }


    // Utility methods to parse the matrix / vector from DataTable


    private double[][] convertDataTableToMatrix(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists();
        int rowCount = rows.size();
        int colCount = rows.get(0).size();
        double[][] matrix = new double[rowCount][colCount];
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                matrix[r][c] = Double.parseDouble(rows.get(r).get(c).trim());
            }
        }
        return matrix;
    }

    private double[] convertDataTableToVector(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists();
        // Case 1: multiple rows, single column
        if (rows.get(0).size() == 1) {
            double[] vector = new double[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                vector[i] = Double.parseDouble(rows.get(i).get(0).trim());
            }
            return vector;
        }
        // Case 2: single row, multiple columns
        else if (rows.size() == 1) {
            List<String> row = rows.get(0);
            double[] vector = new double[row.size()];
            for (int i = 0; i < row.size(); i++) {
                vector[i] = Double.parseDouble(row.get(i).trim());
            }
            return vector;
        } else {
            throw new IllegalArgumentException(
                    "DataTable format not recognized for vector. Must be a single row or a single column."
            );
        }
    }

    private void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }

    private void printVector(double[] vector) {
        if (vector == null) {
            System.out.println("(null vector)");
            return;
        }
        for (double val : vector) {
            System.out.print(val + "\t");
        }
        System.out.println();
    }
}
