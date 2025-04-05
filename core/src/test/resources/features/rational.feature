Feature: Rational Number Arithmetic
  This feature performs arithmetic operations on rational numbers
  So that it can verify the correctness of the calculator

  Background: Initializing a calculator
    Given I initialize a calculator

  Scenario: Addition of two rational numbers
    Given an operation '+'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then the operation evaluates to a rational 5/6

  Scenario: Subtraction of two rational numbers
    Given an operation '-'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then the operation evaluates to a rational 1/6

  Scenario: Multiplication of two rational numbers
    Given an operation '*'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then the operation evaluates to a rational 1/6

  Scenario: Division of two rational numbers
    Given an operation '/'
    When I provide a rational number 1/2
    And I provide a rational number 1/3
    Then the operation evaluates to a rational 3/2

  Scenario: Addition resulting in an integer
    Given an operation '+'
    When I provide a rational number 1/2
    And I provide a rational number 1/2
    Then the operation evaluates to an integer 1

  Scenario: Multiplication resulting in zero
    Given an operation '*'
    When I provide a rational number 0/1
    And I provide a rational number 3/4
    Then the operation evaluates to an integer 0

  Scenario: Division by zero should fail
    Given an operation '/'
    When I provide a rational number 1/2
    And I provide a rational number 0/1
    Then the operation evaluates to a double NaN

  Scenario: Handling negative rational numbers
    Given an operation '+'
    When I provide a rational number -1/2
    And I provide a rational number 1/3
    Then the operation evaluates to a rational -1/6

  Scenario: Reducing fractions to simplest form
    Given an operation '+'
    When I provide a rational number 2/4
    And I provide a rational number 3/6
    Then the operation evaluates to an integer 1

  Scenario Outline: Performing arithmetic operations on rational numbers
    Given an operation '<operation>'
    When I provide a rational number <num1>/<den1>
    And I provide a rational number <num2>/<den2>
    Then the operation evaluates to a rational <res_num>/<res_den>

    Examples:
      | num1 | den1 | num2 | den2 | operation | res_num | res_den |
      | 1    | 2    | 1    | 3    | +         | 5       | 6       |
      | 3    | 4    | 1    | 4    | -         | 1       | 2       |
      | 2    | 3    | 3    | 4    | *         | 1       | 2       |
      | 2    | 5    | 1    | 2    | /         | 4       | 5       |

  Scenario Outline: Performing arithmetic operations between an integer and a rational number
    Given an operation '<operation>'
    When I provide an integer number <integer>
    And I provide a rational number <num>/<den>
    Then the operation evaluates to a rational <res_num>/<res_den>

    Examples:
      | integer | num | den | operation | res_num | res_den |
      | 2       | 1   | 3   | +         | 7       | 3       |
      | 5       | 2   | 5   | -         | 23      | 5       |
      | 5       | 3   | 2   | *         | 15      | 2       |
      | 7       | 2   | 3   | /         | 21      | 2       |

  Scenario Outline: Performing arithmetic operations between a double and a rational number
    Given an operation '<operation>'
    When I provide a double number <double>
    And I provide a rational number <num>/<den>
    Then the operation evaluates to a double <expected_result>

    Examples:
      | double | num | den | operation | expected_result |
      | 2.5    | 1   | 2   | +         | 3.0             |
      | 4.75   | 2   | 5   | -         | 4.35            |
      | 3.2    | 5   | 4   | *         | 4.0             |
      | 6.0    | 2   | 3   | /         | 9.0             |

  Scenario Outline: Performing arithmetic operations between and a rational number and a double
    Given an operation '<operation>'
    When I provide a rational number <num>/<den>
    And I provide a double number <double>
    Then the operation evaluates to a double <expected_result>

    Examples:
      | num | den | double | operation | expected_result |
      | 1   | 2   | 2.5    | +         | 3.0             |
      | 2   | 5   | 4.75   | -         | -4.35           |
      | 5   | 4   | 3.2    | *         | 4.0             |
      | 2   | 3   | 6.0    | /         | 0.1111111111111 |

  Scenario Outline: Integer divided by a rational number (simplified)
      Given an operation '/'
      When I provide an integer number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to an integer <result>

      Examples:
          | n1  | n2  | result |
          | 6   | 1/2 |  12   |
          | 9   | 1/3 |  27   |

  Scenario Outline: Integer divided by a rational number (resulting in double)
      Given an operation '/'
      When I provide an integer number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2  | result |
          | 7   | 3/4 |  9.333333333 |
          | 9   | 2/3 |  13.5   |
          | 6   | 5/2 |  2.4    |

  Scenario Outline: Rational number divided by an integer (simplified)
      Given an operation '/'
      When I provide a rational number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1    | n2 | result  |
          | 3/4   |  2 |  3/8    |
          | 5/3   |  2 |  5/6    |
          | 7/4   |  3 |  7/12   |

  Scenario Outline: Rational number divided by an integer (resulting in double)
      Given an operation '/'
      When I provide a rational number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1    | n2 | result |
          | 5/2   |  3 |  0.833333333 |
          | 7/3   |  2 |  1.166666667 |
          | 9/5   |  4 |  0.45   |

  Scenario Outline: Rational number divided by a rational number (simplified)
      Given an operation '/'
      When I provide a rational number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1   | n2   | result  |
          | 3/4  | 1/2  |  3/2   |
          | 7/4  | 2/5  |  35/8  |

  Scenario Outline: Rational number divided by a rational number (resulting in double)
      Given an operation '/'
      When I provide a rational number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1   | n2   | result |
          | 5/3  | 2/5  |  4.166666667 |
          | 7/3  | 3/5  |  3.888888889 |
          | 2/3  | 5/2  |  0.266666666 |

  Scenario Outline: Integer multiplied by a rational number (simplified)
      Given an operation '*'
      When I provide an integer number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to an integer <result>

      Examples:
          | n1  | n2  | result |
          | 6   | 1/2 |  3    |
          | 9   | 1/3 |  3    |

  Scenario Outline: Integer multiplied by a rational number (resulting in rational)
      Given an operation '*'
      When I provide an integer number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1  | n2  | result |
          | 9   | 2/5 |  18/5 |
          | 7   | 5/3 |  35/3 |

  Scenario Outline: Rational number multiplied by an integer (simplified)
      Given an operation '*'
      When I provide a rational number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1   | n2 | result |
          | 3/4  | 2  |  3/2  |
          | 5/3  | 2  |  10/3 |
          | 7/4  | 3  |  21/4 |

  Scenario Outline: Rational number multiplied by an integer (resulting in rational)
      Given an operation '*'
      When I provide a rational number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1   | n2 | result |
          | 3/4  | 3  |  9/4  |
          | 5/6  | 4  |  10/3 |

  Scenario Outline: Rational number multiplied by a rational number (simplified)
      Given an operation '*'
      When I provide a rational number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1   | n2   | result  |
          | 3/4  | 1/2  |  3/8   |
          | 5/3  | 2/3  |  10/9  |
          | 7/4  | 2/5  |  7/10  |

  Scenario Outline: Rational number multiplied by a rational number (resulting in rational)
      Given an operation '*'
      When I provide a rational number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a rational <result>

      Examples:
          | n1   | n2   | result |
          | 1/2  | 3/4  |  3/8  |
          | 5/3  | 2/5  |  2/3 |
          | 7/4  | 3/5  |  21/20 |

  Scenario Outline: Evaluating absolute value of a rational number
    Given an operation 'abs'
    When I provide a rational number <n>
    Then the operation evaluates to a rational <result>

    Examples:
      | n    | result |
      |  5/3 |    5/3 |
      | -5/3 |    5/3 |
      | 5/-3 |    5/3 |
