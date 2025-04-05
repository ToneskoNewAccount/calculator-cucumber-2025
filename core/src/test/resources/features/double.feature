Feature: Double Arithmetic Expressions
  This feature provides a range of scenarios corresponding to the
  intended external behaviour of arithmetic expressions on doubles.

  Background:
    Given I initialize a calculator

  Scenario Outline: Adding two double numbers
    Given an operation '+'
    When I provide a double number <n1>
    And I provide a double number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      | n1  | n2  | result |
      | 4.3 | 5.3 |    9.6 |
      | 5.3 | 4.3 |    9.6 |
      | 5.8 | 3.2 |    9.0 |

  Scenario: Adding two double numbers with negative numbers
    Given an operation '+'
    When I provide a double number 4.4
    And I provide a double number -5.5
    Then the operation evaluates to a double -1.1

  Scenario Outline: Subtracting two double numbers
    Given an operation '-'
    When I provide a double number <n1>
    And I provide a double number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      | n1  | n2  | result |
      | 4.3 | 5.3 |   -1.0 |
      | 5.3 | 4.3 |    1.0 |
      | 5.8 | 3.2 |    2.6 |

  Scenario: Subtracting two double numbers with negative numbers
    Given an operation '-'
    When I provide a double number 4.3
    And I provide a double number -5.6
    Then the operation evaluates to a double 9.9

  Scenario Outline: Multiplying two double numbers
    Given an operation '*'
    When I provide a double number <n1>
    And I provide a double number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      | n1  | n2  | result |
      | 7.5 | 5.5 | 41.25  |
      | 5.5 | 7.5 | 41.25  |
      | 3.3 | 7.4 | 24.42  |

  Scenario: Multiplying two double numbers with negative numbers
    Given an operation '*'
    When I provide a double number 7.3
    And I provide a double number -5.4
    Then the operation evaluates to a double -39.42

  Scenario Outline: Dividing two double numbers
    Given an operation '/'
    When I provide a double number <n1>
    And I provide a double number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      | n1      | n2   | result |
      |  41.25  |  7.5 |    5.5 |
      |  41.25  |  5.5 |    7.5 |
      |  41.25  | -5.5 |   -7.5 |
      | -41.25  |  5.5 |   -7.5 |
      | -41.25  | -5.5 |    7.5 |
      |  24.42  |  3.3 |    7.4 |

  Scenario: Division by zero
    Given an operation '/'
    When I provide a double number 5.0
    And I provide a double number 0.0
    Then the operation evaluates to a double NaN

  Scenario: Negative division by zero
    Given an operation '/'
    When I provide a double number -5
    And I provide a double number 0.0
    Then the operation evaluates to a double NaN

  Scenario: Zero divided by zero
    Given an operation '/'
    When I provide a double number 0
    And I provide a double number 0.0
    Then the operation evaluates to a double NaN

  Scenario: Division by very small number
    Given an operation '/'
    When I provide a double number 5.0
    And I provide a double number 0.0000000001
    Then the operation evaluates to a double 50000000000.0

  Scenario Outline: Adding a double and an integer
      Given an operation '+'
      When I provide a double number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2 | result |
          | 2.5 |  3 |   5.5  |
          | 4.1 |  2 |   6.1  |
          | 7.6 | -3 |   4.6  |

  Scenario Outline: Adding a double and a rational number
      Given an operation '+'
      When I provide a double number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2  | result |
          | 4.75 | 1/2 |  5.25  |
          | 6.2  | 3/4 |  6.95  |
          | 3.1  | 2/5 |  3.5   |

  Scenario Outline: Subtracting a double and an integer
      Given an operation '-'
      When I provide a double number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2 | result |
          | 7.8 |  2 |   5.8  |
          | 5.4 |  1 |   4.4  |
          | 8.9 | -3 |  11.9  |

  Scenario Outline: Subtracting a double and a rational number
      Given an operation '-'
      When I provide a double number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1   | n2  | result |
          | 6.25 | 3/4 |  5.5   |
          | 9.5  | 1/2 |  9.0   |
          | 4.8  | 2/5 |  4.4   |

  Scenario Outline: Multiplying a double and an integer
      Given an operation '*'
      When I provide a double number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2 | result |
          | 2.4 |  5 |  12.0  |
          | 3.3 | -2 |  -6.6  |
          | 7.1 |  4 |  28.4  |

  Scenario Outline: Multiplying a double and a rational number
      Given an operation '*'
      When I provide a double number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2  | result |
          | 3.6 | 2/3 |  2.4   |
          | 5.1 | 3/4 |  3.825 |
          | 8.2 | 4/5 |  6.56  |

  Scenario Outline: Dividing a double by an integer
      Given an operation '/'
      When I provide a double number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2 | result |
          | 8.4 |  2 |   4.2  |
          | 9.9 |  3 |   3.3  |
          | 6.6 | -2 |  -3.3  |

  Scenario Outline: Dividing a double by a rational number
      Given an operation '/'
      When I provide a double number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2  | result |
          | 5.5 | 1/2 |  11.0  |
          | 7.2 | 3/4 |   9.6  |
          | 9.8 | 2/5 |  24.5  |

  Scenario Outline: Multiplying a negative double and an integer
      Given an operation '*'
      When I provide a double number <n1>
      And I provide an integer number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1   | n2 | result  |
          | -2.3 |  4 |  -9.2   |
          | -5.5 | -2 |  11.0   |
          | -7.1 |  3 | -21.3   |

  Scenario Outline: Dividing a negative double by a rational number
      Given an operation '/'
      When I provide a double number <n1>
      And I provide a rational number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1   | n2  | result  |
          | -6.4 | 4/5 |  -8.0   |
          | -9.0 | 1/2 | -18.0   |
          | -7.2 | 3/4 |  -9.6   |

  Scenario Outline: Integer divided by a double
      Given an operation '/'
      When I provide an integer number <n1>
      And I provide a double number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1 | n2  | result  |
          | 5  | 2.5 |  2.0    |
          | 9  | 4.5 |  2.0    |
          | 7  | 3.5 |  2.0    |

  Scenario Outline: Rational number divided by a double
      Given an operation '/'
      When I provide a rational number <n1>
      And I provide a double number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2  | result        |
          | 3/4 | 1.5 |           0.5 |
          | 5/2 | 2.5 |           1.0 |
          | 7/3 | 3.5 |  0.6666666667 |

  Scenario Outline: Integer multiplied by a double
      Given an operation '*'
      When I provide an integer number <n1>
      And I provide a double number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1 | n2  | result  |
          | 5  | 2.5 |  12.5   |
          | 3  | 4.2 |  12.6   |
          | 7  | 3.5 |  24.5   |

  Scenario Outline: Rational number multiplied by a double
      Given an operation '*'
      When I provide a rational number <n1>
      And I provide a double number <n2>
      Then the operation evaluates to a double <result>

      Examples:
          | n1  | n2  | result         |
          | 3/4 | 1.5 |          1.125 |
          | 5/2 | 2.5 |           6.25 |
          | 7/3 | 3.5 |  8.16666666667 |
