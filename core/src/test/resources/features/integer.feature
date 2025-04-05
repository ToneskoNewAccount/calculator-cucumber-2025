Feature: Integer Arithmetic Expressions
  This feature provides a range of scenarios corresponding to the
  intended external behaviour of arithmetic expressions on integers.

  Background:
    Given I initialize a calculator

  Scenario Outline: Adding two integer numbers
    Given an operation '+'
    When I provide an integer number <n1>
    And I provide an integer number <n2>
    Then the operation evaluates to an integer <result>

    Examples:
      | n1 | n2 | result |
      | 4  | 5  | 9      |
      | 5  | 3  | 8      |

  Scenario: Adding two integer numbers with negative numbers
    Given an operation '+'
    When I provide an integer number 4
    And I provide an integer number -5
    Then the operation evaluates to an integer -1

  Scenario Outline: Subtracting two integer numbers
    Given an operation '-'
    When I provide an integer number <n1>
    And I provide an integer number <n2>
    Then the operation evaluates to an integer <result>

    Examples:
      | n1 | n2 | result |
      | 4  | 5  | -1     |
      | 5  | 3  | 2      |

  Scenario: Subtracting two integer numbers with negative numbers
    Given an operation '-'
    When I provide an integer number 4
    And I provide an integer number -5
    Then the operation evaluates to an integer 9

  Scenario Outline: Multiplying two integer numbers
    Given an operation '*'
    When I provide an integer number <n1>
    And I provide an integer number <n2>
    Then the operation evaluates to an integer <result>

    Examples:
      | n1 | n2 | result |
      | 7  | 5  | 35     |
      | 5  | 7  | 35     |
      | 3  | 7  | 21     |

  Scenario: Multiplying two integer numbers with negative numbers
    Given an operation '*'
    When I provide an integer number 7
    And I provide an integer number -5
    Then the operation evaluates to an integer -35

  Scenario Outline: Dividing two integer numbers gives a rational number when cannot be simplified in an integer
    Given an operation '/'
    When I provide an integer number <n1>
    And I provide an integer number <n2>
    Then the operation evaluates to a rational <result>

    Examples:
      | n1 | n2 | result |
      |  7 |  5 |    7/5 |
      |  5 |  7 |    5/7 |
      | -4 |  5 |   -4/5 |
      |  4 | -5 |   -4/5 |
      | -4 | -5 |    4/5 |

  Scenario Outline: Dividing two integer numbers gives an integer when can be simplified in an integer
    Given an operation '/'
    When I provide an integer number <n1>
    And I provide an integer number <n2>
    Then the operation evaluates to an integer <result>

    Examples:
      | n1 | n2 | result |
      |  8 |  4 |      2 |
      | -8 | -4 |      2 |
      |  8 | -4 |     -2 |
      | -8 |  4 |     -2 |

  Scenario: Division by zero
    Given an operation '/'
    When I provide an integer number 5
    And I provide an integer number 0
    Then the operation evaluates to a double NaN

  Scenario: Negative division by zero
    Given an operation '/'
    When I provide an integer number -5
    And I provide an integer number 0
    Then the operation evaluates to a double NaN

  Scenario: Zero divided by zero
    Given an operation '/'
    When I provide an integer number 0
    And I provide an integer number 0
    Then the operation evaluates to a double NaN

  Scenario Outline: Evaluating absolute value of an integer number
    Given an operation 'abs'
    When I provide an integer number <n>
    Then the operation evaluates to an integer <result>

    Examples:
      | n  | result |
      |  5 |      5 |
      | -5 |      5 |
      |  0 |      0 |
