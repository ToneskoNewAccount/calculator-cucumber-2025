Feature: Rational Number Arithmetic
  This feature performs arithmetic operations on rational numbers
  So that it can verify the correctness of the calculator

  Background: Initializing a calculator
    Given A calculator

  Scenario: Addition of two rational numbers
    Given a rational operation '+'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then The result should be 5/6

  Scenario: Subtraction of two rational numbers
    Given a rational operation '-'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then The result should be 1/6

  Scenario: Multiplication of two rational numbers
    Given a rational operation '*'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then The result should be 1/6

  Scenario: Division of two rational numbers
    Given a rational operation '/'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then The result should be 3/2

  Scenario: Addition resulting in an integer
    Given a rational operation '+'
    When I provide a rational number 1/2
    And I provide a rational number 1/2
    Then The result should be integer 1

  Scenario: Multiplication resulting in zero
    Given a rational operation '*'
    When I provide a rational number 0/1
    And I provide a rational number 3/4
    Then The result should be integer 0

  Scenario: Division by zero should fail
    Given a rational operation '/'
    When I provide a rational number 1/2
    And I provide a rational number 0/1
    Then A Double NaN should be construct

  Scenario: Handling negative rational numbers
    Given a rational operation '+'
    When I provide a rational number -1/2
    And I provide a rational number 1/3
    Then The result should be -1/6

  Scenario: Reducing fractions to simplest form
    Given a rational operation '+'
    When I provide a rational number 2/4
    And I provide a rational number 3/6
    Then The result should be integer 1

  Scenario Outline: Performing arithmetic operations on rational numbers
    Given a rational operation '<operation>'
    When I provide a rational number <num1>/<den1>
    And I provide a rational number <num2>/<den2>
    Then The result should be <res_num>/<res_den>

    Examples:
      | num1 | den1 | num2 | den2 | operation | res_num | res_den |
      | 1    | 2    | 1    | 3    | +         | 5       | 6       |
      | 3    | 4    | 1    | 4    | -         | 1       | 2       |
      | 2    | 3    | 3    | 4    | *         | 1       | 2       |
      | 2    | 5    | 1    | 2    | /         | 4       | 5       |

  Scenario Outline: Performing arithmetic operations between an integer and a rational number
    Given a rational operation '<operation>'
    When I provide an integer number <integer>
    And I provide a rational number <num>/<den>
    Then The result should be <res_num>/<res_den>

    Examples:
      | integer | num | den | operation | res_num | res_den |
      | 2       | 1   | 3   | +         | 7       | 3       |
      | 5       | 2   | 5   | -         | 23      | 5       |
      | 5       | 3   | 2   | *         | 15      | 2       |
      | 7       | 2   | 3   | /         | 21      | 2       |

  Scenario Outline: Performing arithmetic operations between a double and a rational number
    Given a rational operation '<operation>'
    When I provide a double number <double>
    And I provide a rational number <num>/<den>
    Then The result should be a double <expected_result>

    Examples:
      | double | num | den | operation | expected_result |
      | 2.5    | 1   | 2   | +         | 3.0             |
      | 4.75   | 2   | 5   | -         | 4.35            |
      | 3.2    | 5   | 4   | *         | 4.0             |
      | 6.0    | 2   | 3   | /         | 9.0             |

  Scenario Outline: Performing arithmetic operations between and a rational number and a double
    Given a rational operation '<operation>'
    When I provide a rational number <num>/<den>
    And I provide a double number <double>
    Then The result should be a double <expected_result>

    Examples:
      | num | den | double | operation | expected_result |
      | 1   | 2   | 2.5    | +         | 3.0             |
      | 2   | 5   | 4.75   | -         | -4.35           |
      | 5   | 4   | 3.2    | *         | 4.0             |
      | 2   | 3   | 6.0    | /         | 0.1111111111111 |
