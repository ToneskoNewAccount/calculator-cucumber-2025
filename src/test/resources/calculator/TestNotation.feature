Feature: Notation Arithmetic Expression
  This feature provides a range of scenarios corresponding intended external behaviour
  of notation operations.

  Scenario Outline: Testing notations
    Given an integer operation '<operation>'
    When I provide a first number <first_number>
    When I provide a second number <second_number>
    Then its PREFIX notation is <prefix_notation>
    And its POSTFIX notation is <postfix_notation>
    And its INFIX notation is <infix_notation>

    Examples:
      | operation | first_number | second_number | prefix_notation | postfix_notation | infix_notation |
      | +         | 5            | 6            | + (5, 6)        | (5, 6) +        | ( 5 + 6 )     |
      | -         | 5            | 6            | - (5, 6)        | (5, 6) -        | ( 5 - 6 )     |
      | *         | 5            | 6            | * (5, 6)        | (5, 6) *        | ( 5 * 6 )     |
      | /         | 5            | 6            | / (5, 6)        | (5, 6) /        | ( 5 / 6 )     |


