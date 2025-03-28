Feature: Double Arithmetic Expressions
  This feature provides a range of scenarios corresponding to the
  intended external behaviour of arithmetic expressions on doubles.

  # This is just a comment.
  # You can start with a Background: that will be run before executing each scenario.

  Background:
    Given I initialise a calculator
#
# Each scenario can be seen as a test that can be executed with JUnit,
# provided that each of the steps (Given, When, And and Then) are
# implemented in a Java mapping file (CalculatorSteps.Java)

  Scenario: Adding two double numbers
    Given an operation '+'
    When I provide a first integer number 4
    And I provide a second integer number 5
    Then the operation evaluates to an integer 9

  Scenario: Subtracting two double numbers
    Given an operation '-'
    When I provide a first integer number 7
    And I provide a second integer number 5
    Then the operation evaluates to an integer 2

  Scenario: Multiplying two double numbers
    Given an operation '*'
    When I provide a first integer number 7
    And I provide a second integer number 5
    Then the operation evaluates to an integer 35

  Scenario: Dividing two double numbers
    Given an operation '/'
    When I provide a first double number 7
    And I provide a second integer number 5
    Then the operation evaluates to a double 1.4

  Scenario: Printing the sum of two double numbers
    Given the sum of two integer numbers 8 and 6
    Then its INFIX notation is ( 8 + 6 )
    And its PREFIX notation is + (8, 6)
    And its POSTFIX notation is (8, 6) +

  # This is an example of a scenario in which we provide a list of numbers as input.
  # (In fact, this is not entirely true, since what is given as input is a table of
  # strings. In this case, the table is of dimension 1 * 3 (1 line and three columns).
  Scenario: Evaluation arithmetic operations over a list of double numbers
    Given the following list of double numbers
      | 8 | 2 | 2 |
    Then the sum is 12
    And the product is 32
    And the difference is 4
    And the quotient is 2

  # A scenario outline (or template) is a scenario that is parameterised
  # with different values. The outline comes with a set of examples.
  # The scenario will be executed with each of the provided inputs.
  Scenario Outline: Adding two double numbers
    Given an operation '+'
    When I provide a first integer number <n1>
    And I provide a second integer number <n2>
    Then the operation evaluates to an integer <result>

    Examples:
      |n1|n2|result|
      |4|5|9|
      |5|3|8|

  Scenario Outline: Dividing two double numbers
    Given an operation '/'
    When I provide a first integer number <n1>
    And I provide a second double number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      |n1|n2|result|
      |35|5|7.0|
      |7|5|1.4|
      |5|7|0.714285714|

  Scenario Outline: Evaluating arithmetic operations with two integer parameters
    Given an operation <op>
    When I provide a first integer number <n1>
    And I provide a second integer number <n2>
    Then the operation evaluates to an integer <result>

    Examples:
      | op  |n1|n2|result|
      | "+" | 4| 5|     9|
      | "-" | 8| 5|     3|
      | "*" | 7| 2|    14|

  Scenario: Division by zero
    Given an operation '/'
    When I provide a first integer number 5
    And I provide a second integer number 0
    Then the operation evaluates to a double NaN

  Scenario: Negative division by zero
    Given an operation '/'
    When I provide a first integer number -5
    And I provide a second integer number 0
    Then the operation evaluates to a double NaN

  Scenario: Zero divided by zero
    Given an operation '/'
    When I provide a first integer number 0
    And I provide a second integer number 0
    Then the operation evaluates to a double NaN

  Scenario Outline: Testing notations with two double numbers
    Given an operation '<operation>'
    When I provide a first integer number <first_number>
    When I provide a second integer number <second_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | prefix_notation | postfix_notation | infix_notation |
      | +         | 5            | 6            | + (5, 6)        | (5, 6) +        | ( 5 + 6 )     |
      | -         | 5            | 6            | - (5, 6)        | (5, 6) -        | ( 5 - 6 )     |
      | *         | 5            | 6            | * (5, 6)        | (5, 6) *        | ( 5 * 6 )     |
      | /         | 5            | 6            | / (5, 6)        | (5, 6) /        | ( 5 / 6 )     |

  Scenario Outline: Testing notations with three double numbers
    Given an operation '<operation>'
    When I provide a first integer number <first_number>
    When I provide a second double number <second_number>
    When I provide a third double number <third_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | third_number | prefix_notation | postfix_notation | infix_notation   |
      | +         | 5            | 6             | 7            | + (5, 6.0, 7.0) | (5, 6.0, 7.0) + | ( 5 + 6.0 + 7.0 ) |
      | -         | 5            | 6             | 7            | - (5, 6.0, 7.0) | (5, 6.0, 7.0) - | ( 5 - 6.0 - 7.0 ) |
      | *         | 5            | 6             | 7            | * (5, 6.0, 7.0) | (5, 6.0, 7.0) * | ( 5 * 6.0 * 7.0 ) |
      | /         | 5            | 6             | 7            | / (5, 6.0, 7.0) | (5, 6.0, 7.0) / | ( 5 / 6.0 / 7.0 ) |


  Scenario Outline: Evaluating arithmetic operations with a double and integer parameters
    Given an operation <op>
    When I provide a first double number <n1>
    And I provide a second integer number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      | op  |n1|n2|result|
      | "+" | 4| 5|     9.0|
      | "-" | 8| 5|     3.0|
      | "*" | 7| 2|    14.0|

  Scenario Outline: Evaluating arithmetic operations with a integer and a double parameters
    Given an operation <op>
    When I provide a first integer number <n1>
    And I provide a second double number <n2>
    Then the operation evaluates to a double <result>

    Examples:
      | op  |n1|n2|result|
      | "+" | 4| 5|     9.0|
      | "-" | 8| 5|     3.0|
      | "*" | 7| 2|    14.0|

