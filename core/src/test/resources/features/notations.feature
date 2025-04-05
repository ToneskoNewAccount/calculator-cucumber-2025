Feature: Testing notations of arithmetic expressions
  This feature provides a range of scenarios corresponding to the
  intended external behaviour of notations of arithmetic expressions.

  Background:
    Given I initialize a calculator

  Scenario: Printing the sum of two double numbers
    Given an operation '+'
    When I provide an integer number 8
    And I provide an integer number 6
    Then its INFIX notation is ( 8 + 6 )
    And its PREFIX notation is + (8, 6)
    And its POSTFIX notation is (8, 6) +

  Scenario Outline: Testing notations with two double numbers
    Given an operation '<operation>'
    When I provide an integer number <first_number>
    When I provide an integer number <second_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | prefix_notation | postfix_notation | infix_notation |
      | +         | 5            | 6             | + (5, 6)        | (5, 6) +         | ( 5 + 6 )      |
      | -         | 5            | 6             | - (5, 6)        | (5, 6) -         | ( 5 - 6 )      |
      | *         | 5            | 6             | * (5, 6)        | (5, 6) *         | ( 5 * 6 )      |
      | /         | 5            | 6             | / (5, 6)        | (5, 6) /         | ( 5 / 6 )      |

  Scenario Outline: Testing notations with three double numbers
    Given an operation '<operation>'
    When I provide an integer number <first_number>
    When I provide a double number <second_number>
    When I provide a double number <third_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | third_number | prefix_notation | postfix_notation | infix_notation    |
      | +         | 5            | 6             | 7            | + (5, 6.0, 7.0) | (5, 6.0, 7.0) +  | ( 5 + 6.0 + 7.0 ) |
      | -         | 5            | 6             | 7            | - (5, 6.0, 7.0) | (5, 6.0, 7.0) -  | ( 5 - 6.0 - 7.0 ) |
      | *         | 5            | 6             | 7            | * (5, 6.0, 7.0) | (5, 6.0, 7.0) *  | ( 5 * 6.0 * 7.0 ) |
      | /         | 5            | 6             | 7            | / (5, 6.0, 7.0) | (5, 6.0, 7.0) /  | ( 5 / 6.0 / 7.0 ) |
