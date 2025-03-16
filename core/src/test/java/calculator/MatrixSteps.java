package calculator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;
import visitor.Printer;


/**
 * MatrixSteps implements the step definitions for the MatrixOperations.
 */
public class MatrixSteps {


    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private Matrix result;
    private String operationType;
    private String errorMessage;
    private String printedResult;



    /**
     * Initializes a matrix calculator.
     * Note: Renamed to "I initialise a matrix calculator" to avoid duplicate steps.
     */
    @Given("I initialise a matrix calculator")
    public void i_initialise_a_matrix_calculator() {
        // No specific implementation needed.
    }

    /**
     * Indicates that a matrix addition operation will be performed.
     */
    @Given("a matrix addition operation")
    public void a_matrix_addition_operation() {
        operationType = "add";
    }

    /**
     * Indicates that a matrix subtraction operation will be performed.
     */
    @Given("a matrix subtraction operation")
    public void a_matrix_subtraction_operation() {
        operationType = "subtract";
    }

    /**
     * Indicates that a matrix multiplication operation will be performed.
     */
    @Given("a matrix multiplication operation")
    public void a_matrix_multiplication_operation() {
        operationType = "multiply";
    }

    /**
     * Indicates that a matrix transpose operation will be performed.
     */
    @Given("a matrix transpose operation")
    public void a_matrix_transpose_operation() {
        operationType = "transpose";
    }

    /**
     * Indicates that a matrix inversion operation will be performed.
     */
    @Given("a matrix inversion operation")
    public void a_matrix_inversion_operation() {
        operationType = "inverse";
    }


    /**
     * Creates the first matrix from a string (e.g. "[[1,2],[3,4]]").
     *
     * @param matrixString the string representing a matrix.
     */
    @When("I provide a first matrix {string}")
    public void i_provide_a_first_matrix(String matrixString) {
        firstMatrix = parseMatrix(matrixString);
    }

    /**
     * Creates the second matrix from a string (e.g. "[[5,6],[7,8]]").
     *
     * @param matrixString the string representing a matrix.
     */
    @When("I provide a second matrix {string}")
    public void i_provide_a_second_matrix(String matrixString) {
        secondMatrix = parseMatrix(matrixString);
    }

    /**
     * Creates a generic matrix from a string for unary operations.
     *
     * @param matrixString the string representing a matrix.
     */
    @When("I provide a matrix {string}")
    public void i_provide_a_matrix(String matrixString) {
        firstMatrix = parseMatrix(matrixString);
    }

    /**
     * Creates a square matrix from a string or throws an exception if the matrix is not square.
     *
     * @param matrixString the string representing a matrix.
     */
    @When("I provide a square matrix {string}")
    public void i_provide_a_square_matrix(String matrixString) {
        firstMatrix = parseMatrix(matrixString);
        if (firstMatrix.getRowCount() != firstMatrix.getColCount()) {
            throw new IllegalArgumentException("Matrix is not square!");
        }
    }

    /**
     * Creates a non-square matrix from a string or throws an exception if the matrix is square.
     *
     * @param matrixString the string representing a matrix.
     */
    @When("I provide a non-square matrix {string}")
    public void i_provide_a_non_square_matrix(String matrixString) {
        firstMatrix = parseMatrix(matrixString);
        if (firstMatrix.getRowCount() == firstMatrix.getColCount()) {
            throw new IllegalArgumentException("Matrix is square!");
        }
    }

    /**
     * Attempts to create a matrix from a 2D array with mismatched row lengths.
     * This step is used to check the exception "All rows must have the same length."
     */
    @Given("I create a matrix from a 2D array with mismatch row lengths")
    public void i_create_a_matrix_from_a_2d_array_with_mismatch_row_lengths() {
        double[][] invalidData = {
                {1, 2},
                {3, 4, 5}
        };
        try {
            firstMatrix = new Matrix(invalidData);
            fail("No exception was thrown, but we expected one for mismatch row lengths.");
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
            operationType = null; // No operation in progress.
        }
    }

    /**
     * Prints the matrix using the Printer visitor with the chosen notation (INFIX, PREFIX, POSTFIX).
     *
     * @param notationStr the notation to use.
     */
    @When("I print the matrix in {word} notation")
    public void i_print_the_matrix_in_notation(String notationStr) {
        Notation notation = Notation.valueOf(notationStr.toUpperCase());
        Printer printer = new Printer(notation);
        firstMatrix.accept(printer);
        printedResult = printer.getResult();
    }


    /**
     * Verifies that the operation (add, subtract, multiply, transpose, inverse) produces the expected matrix.
     *
     * @param expectedMatrixString the expected matrix as a string.
     */
    @Then("the operation evaluates to {string}")
    public void the_operation_evaluates_to(String expectedMatrixString) {
        Matrix expectedMatrix = parseMatrix(expectedMatrixString);
        try {
            switch (operationType) {
                case "add":
                    if (secondMatrix == null) {
                        fail("No second matrix provided for addition!");
                    }
                    result = firstMatrix.add(secondMatrix);
                    break;
                case "subtract":
                    if (secondMatrix == null) {
                        fail("No second matrix provided for subtraction!");
                    }
                    result = firstMatrix.subtract(secondMatrix);
                    break;
                case "multiply":
                    if (secondMatrix == null) {
                        fail("No second matrix provided for multiplication!");
                    }
                    result = firstMatrix.multiply(secondMatrix);
                    break;
                case "transpose":
                    result = firstMatrix.transpose();
                    break;
                case "inverse":
                    result = firstMatrix.inverse();
                    break;
                default:
                    fail("Unknown operation type: " + operationType);
                    break;
            }
            assertEquals(expectedMatrix.toString(), result.toString(),
                    "The resulting matrix does not match the expected one.");
        } catch (Exception e) {
            fail("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Verifies that an error is raised with the expected message.
     *
     * @param expectedMessage the expected error message.
     */
    @Then("an error is raised with message {string}")
    public void an_error_is_raised_with_message(String expectedMessage) {
        if (operationType == null) {
            // Constructor error case (e.g., mismatch row lengths).
            assertEquals(expectedMessage, errorMessage,
                    "The error message does not match the expected one.");
            return;
        }
        try {
            switch (operationType) {
                case "add":
                    result = firstMatrix.add(secondMatrix);
                    break;
                case "subtract":
                    result = firstMatrix.subtract(secondMatrix);
                    break;
                case "multiply":
                    result = firstMatrix.multiply(secondMatrix);
                    break;
                case "inverse":
                    result = firstMatrix.inverse();
                    break;
                default:
                    fail("No error expected for operation: " + operationType);
                    break;
            }
            fail("No error was raised, but we expected one with message: " + expectedMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            assertEquals(expectedMessage, errorMessage,
                    "The error message does not match the expected one.");
        }
    }

    /**
     * Verifies that the matrix printed via the Printer matches the expected string.
     *
     * @param expectedPrintedMatrix the expected printed matrix string.
     */
    @Then("the printed matrix is {string}")
    public void the_printed_matrix_is(String expectedPrintedMatrix) {
        assertEquals(expectedPrintedMatrix, printedResult,
                "The printed matrix does not match the expected result.");
    }


    /**
     * Parses a string of the form "[[1,2],[3,4]]" into a Matrix instance.
     * This helper method replaces the static parse method in the Matrix class.
     *
     * @param str the string representing the matrix.
     * @return a new Matrix instance.
     * @throws IllegalArgumentException if the format is invalid or numeric conversion fails.
     */
    private Matrix parseMatrix(String str) {
        String trimmed = str.trim();
        if (!trimmed.startsWith("[[") || !trimmed.endsWith("]]")) {
            throw new IllegalArgumentException("Invalid matrix format: " + str);
        }
        String inner = trimmed.substring(2, trimmed.length() - 2);
        String[] rowStrings = inner.split("],\\[");
        double[][] data;
        try {
            data = new double[rowStrings.length][];
            for (int i = 0; i < rowStrings.length; i++) {
                String[] values = rowStrings[i].split(",");
                data[i] = new double[values.length];
                for (int j = 0; j < values.length; j++) {
                    data[i][j] = Double.parseDouble(values[j]);
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value in matrix: " + str, e);
        }
        try {
            return new Matrix(data);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
            operationType = null;
            throw e;
        }
    }
}
