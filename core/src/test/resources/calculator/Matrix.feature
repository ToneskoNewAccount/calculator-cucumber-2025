Feature: Matrix Operations
  This feature provides a range of scenarios corresponding to the intended external behavior
  of matrix operations such as addition, subtraction, multiplication, transposition and inversion.


  # This is just a comment.
  # You can start with a Background: that will be run before executing each scenario.

  Background:
    Given I initialise a calculator

  # Each scenario can be seen as a test that can be executed with JUnit,
  # provided that each of the steps (Given, When, And and Then) are
  # implemented in a Java mapping file (MatrixSteps.Java)


  Scenario: Adding two matrix021
    Given a matrix addition operation
    When I provide a first matrix "[[1,2],[3,4]]"
    And I provide a second matrix "[[5,6],[7,8]]"
    Then the operation evaluates to "[[6,8],[10,12]]"

  Scenario: Subtracting two matrix
    Given a matrix subtraction operation
    When I provide a first matrix "[[5,6],[7,8]]"
    And I provide a second matrix "[[1,2],[3,4]]"
    Then the operation evaluates to "[[4,4],[4,4]]"

  Scenario: Multiplying two matrix
    Given a matrix multiplication operation
    When I provide a first matrix "[[1,2],[3,4]]"
    And I provide a second matrix "[[2,0],[1,2]]"
    Then the operation evaluates to "[[4,4],[10,8]]"

  Scenario: Transposing a matrix
    Given a matrix transpose operation
    When I provide a matrix "[[1,2,3],[4,5,6]]"
    Then the operation evaluates to "[[1,4],[2,5],[3,6]]"

  Scenario: Inverting a square matrix
    Given a matrix inversion operation
    When I provide a square matrix "[[4,7],[2,6]]"
    Then the operation evaluates to "[[0.6,-0.7],[-0.2,0.4]]"

  Scenario: Inverting a non-square matrix
    Given a matrix inversion operation
    When I provide a non-square matrix "[[1,2,3],[4,5,6]]"
    Then an error is raised with message "Matrix must be square to be inverted."

  Scenario: Inverting a matrix that requires pivot row swapping
    Given a matrix inversion operation
    When I provide a square matrix "[[0,1],[2,3]]"
    Then the operation evaluates to "[[-1.5,0.5],[1.0,0.0]]"


  Scenario: Adding two incompatible matrices
    Given a matrix addition operation
    When I provide a first matrix "[[1,2],[3,4]]"
    And I provide a second matrix "[[1,2,3],[4,5,6]]"
    Then an error is raised with message "Incompatible dimensions for addition."

  Scenario: Inverting a singular matrix
    Given a matrix inversion operation
    When I provide a square matrix "[[2,4],[1,2]]"
    Then an error is raised with message "Matrix is singular (pivot is zero)."

  Scenario: Inverting a 1x1 matrix
    Given a matrix inversion operation
    When I provide a square matrix "[[5]]"
    Then the operation evaluates to "[[0.2]]"

  Scenario: Printing a matrix in INFIX notation
    Given I initialise a matrix calculator
    And I provide a matrix "[[1,2],[3,4]]"
    When I print the matrix in INFIX notation
    Then the printed matrix is "[[1.0,2.0],[3.0,4.0]]"

  Scenario: Creating a matrix with mismatch row lengths
    Given I create a matrix from a 2D array with mismatch row lengths
    Then an error is raised with message "All rows must have the same length."
