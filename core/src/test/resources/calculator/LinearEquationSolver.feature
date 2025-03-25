Feature: Linear Equation Solver
  As a user of the LinearEquationSolver
  I want to solve systems of linear equations accurately
  So that I obtain the correct solution or receive a meaningful error

  Background:
    Given I initialise a calculator

  #  Simple 2x2 system (unique solution)
  Scenario: Solve a simple 2x2 system
    Given the matrix A
      | 1 | 2 |
      | 3 | 4 |
    And the vector b
      | 5  |
      | 11 |
    When I solve the linear system
    Then the solution should be
      | 1 |
      | 2 |

  #  2x2 system with no solution
  Scenario: Detect no solution in inconsistent system
    Given the matrix A
      | 1 | 1 |
      | 2 | 2 |
    And the vector b
      | 3 |
      | 7 |
    When I solve the linear system
    Then I should get an error "No solutions"

  #  2x3 system with infinite solutions
  Scenario: Solve a system with infinite solutions
    Given the matrix A
      | 1 | 2 | 3 |
      | 2 | 4 | 6 |
    And the vector b
      | 4 |
      | 8 |
    When I solve the linear system
    Then I should get a valid solution

  #  Simple 3x3 system (unique solution – actually infinite solutions with free variable fixed to 0)
  Scenario: Solve a simple 3x3 system
    Given the matrix A
      | 1 | 2 | 3 |
      | 2 | 5 | 7 |
      | 1 | 1 | 2 |
    And the vector b
      | 14 |
      | 33 |
      | 9  |
    When I solve the linear system
    Then the solution should be
      | 4 |
      | 5 |
      | 0 |

  #  2x2 system with negative coefficients
  Scenario: Solve a 2x2 system with negative coefficients
    Given the matrix A
      | -1 | 2  |
      |  2 | -3 |
    And the vector b
      |  3 |
      | -4 |
    When I solve the linear system
    Then the solution should be
      | 1 |
      | 2 |

  #  System already in upper triangular form
  Scenario: Solve a system already in upper triangular form
    Given the matrix A
      | 1 | 2 | 3 |
      | 0 | 1 | 4 |
      | 0 | 0 | 2 |
    And the vector b
      | 11 |
      | 10 |
      |  4 |
    When I solve the linear system
    Then the solution should be
      | 1 |
      | 2 |
      | 2 |

  # 3x3 system with no solution
  Scenario: Detect no solution in a 3x3 inconsistent system
    Given the matrix A
      | 1 | 2 | 3 |
      | 2 | 4 | 6 |
      | 1 | 2 | 3 |
    And the vector b
      | 6  |
      | 12 |
      | 13 |
    When I solve the linear system
    Then I should get an error "No solutions"

