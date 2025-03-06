Feature: Double Arithmetic Expressions
  This feature provides a range of scenarios corresponding to the
  intended external behaviour of arithmetic expressions on doubles.

  # This is just a comment.
  # You can start with a Background: that will be run before executing each scenario.

  Background:
    Given I initialise a calculator

  # Each scenario can be seen as a test that can be executed with JUnit,
  # provided that each of the steps (Given, When, And and Then) are
  # implemented in a Java mapping file (CalculatorSteps.Java)

  Scenario: Adding two double numbers
    Given an double operation '+'
    When I provide a first number 4
    And I provide a second number 5
    Then the operation evaluates to 9

  Scenario: Subtracting two double numbers
    Given an double operation '-'
    When I provide a first number 7
    And I provide a second number 5
    Then the operation evaluates to 2

  Scenario: Multiplying two double numbers
    Given an double operation '*'
    When I provide a first number 7
    And I provide a second number 5
    Then the operation evaluates to 35

  Scenario: Dividing two double numbers
    Given an double operation '/'
    When I provide a first number 7
    And I provide a second number 5
    Then the operation evaluates to 1.4

  Scenario: Printing the sum of two double numbers
    Given the sum of two numbers 8 and 6
    Then its INFIX notation is ( 8.0 + 6.0 )
    And its PREFIX notation is + (8.0, 6.0)
    And its POSTFIX notation is (8.0, 6.0) +

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
    Given an double operation '+'
    When I provide a first number <n1>
    And I provide a second number <n2>
    Then the operation evaluates to <result>

    Examples:
      |n1|n2|result|
      |4|5|9|
      |5|3|8|

  Scenario Outline: Dividing two double numbers
    Given an double operation '/'
    When I provide a first number <n1>
    And I provide a second number <n2>
    Then the operation evaluates to <result>

    Examples:
      |n1|n2|result|
      |35|5|7|
      |7|5|1.4|
      |5|7|0.7142857|

  Scenario Outline: Evaluating arithmetic operations with two double parameters
    Given an double operation <op>
    When I provide a first number <n1>
    And I provide a second number <n2>
    Then the operation evaluates to <result>

    Examples:
      | op  |n1|n2|result|
      | "+" | 4| 5|     9|
      | "-" | 8| 5|     3|
      | "*" | 7| 2|    14|
      | "/" | 6| 2|     3|

  Scenario: Division by zero
    Given an double operation '/'
    When I provide a first number 5
    And I provide a second number 0
    Then the operation evaluates to NaN

  Scenario: Negative division by zero
    Given an double operation '/'
    When I provide a first number -5
    And I provide a second number 0
    Then the operation evaluates to NaN

  Scenario: Zero divided by zero
    Given an double operation '/'
    When I provide a first number 0
    And I provide a second number 0
    Then the operation evaluates to NaN

  Scenario Outline: Testing notations with two double numbers
    Given an double operation '<operation>'
    When I provide a first number <first_number>
    When I provide a second number <second_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | prefix_notation | postfix_notation | infix_notation |
      | +         | 5            | 6            | + (5.0, 6.0)        | (5.0, 6.0) +        | ( 5.0 + 6.0 )     |
      | -         | 5            | 6            | - (5.0, 6.0)        | (5.0, 6.0) -        | ( 5.0 - 6.0 )     |
      | *         | 5            | 6            | * (5.0, 6.0)        | (5.0, 6.0) *        | ( 5.0 * 6.0 )     |
      | /         | 5            | 6            | / (5.0, 6.0)        | (5.0, 6.0) /        | ( 5.0 / 6.0 )     |

  Scenario Outline: Testing notations with three double numbers
    Given an double operation '<operation>'
    When I provide a first number <first_number>
    When I provide a second number <second_number>
    When I provide a third number <third_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | third_number | prefix_notation | postfix_notation | infix_notation |
      | +         | 5            | 6             | 7            | + (5.0, 6.0, 7.0) | (5.0, 6.0, 7.0) + | ( 5.0 + 6.0 + 7.0 ) |
      | -         | 5            | 6             | 7            | - (5.0, 6.0, 7.0) | (5.0, 6.0, 7.0) - | ( 5.0 - 6.0 - 7.0 ) |
      | *         | 5            | 6             | 7            | * (5.0, 6.0, 7.0) | (5.0, 6.0, 7.0) * | ( 5.0 * 6.0 * 7.0 ) |
      | /         | 5            | 6             | 7            | / (5.0, 6.0, 7.0) | (5.0, 6.0, 7.0) / | ( 5.0 / 6.0 / 7.0 ) |
