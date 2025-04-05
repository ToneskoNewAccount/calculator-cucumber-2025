Feature: Complex Number Operations

  Background:
    Given I initialize a calculator

  Scenario: Addition of two complex numbers
    Given an operation "+"
    When I provide a complex number 3 + 4i
    And I provide a complex number 1 + 2i
    Then the operation evaluates to a complex 4 + 6i

  Scenario: Addition of one complex numbers and one double number
    Given an operation "+"
    When I provide a complex number 3 + 4i
    And I provide a double number 2
    Then the operation evaluates to a complex 5 + 4i

  Scenario: Subtraction of two complex numbers
    Given an operation "-"
    When I provide a complex number -5 + 7i
    And I provide a complex number 2 + 3i
    Then the operation evaluates to a complex -7 + 4i

  Scenario: Multiplication of two complex numbers
    Given an operation "*"
    When I provide a complex number 2 + 3i
    And I provide a complex number 1 + 4i
    Then the operation evaluates to a complex -10 + 11i

  Scenario: Division of two complex numbers
    Given an operation "/"
    When I provide a complex number 4 + 2i
    And I provide a complex number 3 + 1i
    Then the operation evaluates to a complex 1.4 + 0.2i

  Scenario: Division of one complex number and one double number
    Given an operation "/"
    When I provide a complex number 4 + 2i
    And I provide a double number 2
    Then the operation evaluates to a complex 2 + 1i

  Scenario: Addition of two complex number that result in a int
    Given an operation "+"
    When I provide a complex number 3 + 4i
    And I provide a complex number 3 - 4i
    Then the operation evaluates to an integer 6

  Scenario: Addition of two complex number that result in a double imaginary part 0.0
    Given an operation "+"
    When I provide a complex number 3.3 + 4.5i
    And I provide a complex number 3.3 - 4.5i
    Then the operation evaluates to a complex 6.6 + 0.0i

  Scenario: Addition of a complex number and a double number
    Given an operation "+"
    When I provide a complex number 2.5 + 3.7i
    And I provide a double number 5.2
    Then the operation evaluates to a complex 7.7 + 3.7i

  Scenario: Addition of a complex number and a negative double number
    Given an operation "+"
    When I provide a complex number 2.5 + 3.7i
    And I provide a double number -2.8
    Then the operation evaluates to a complex -0.3 + 3.7i

  Scenario: Subtraction of a complex number and a double number
    Given an operation "-"
    When I provide a complex number 3.4 + 2.1i
    And I provide a double number 1.3
    Then the operation evaluates to a complex 2.1 + 2.1i

  Scenario: Subtraction of a complex number and a negative double number
    Given an operation "-"
    When I provide a complex number 3.4 + 2.1i
    And I provide a double number -1.5
    Then the operation evaluates to a complex 4.9 + 2.1i

  Scenario: Multiplication of a complex number and a double number
    Given an operation "*"
    When I provide a complex number 3.2 + 4.6i
    And I provide a double number 2.5
    Then the operation evaluates to a complex 8.0 + 11.5i

  Scenario: Multiplication of a complex number and a negative double number
    Given an operation "*"
    When I provide a complex number 3.2 + 4.6i
    And I provide a double number -2.5
    Then the operation evaluates to a complex -8.0 - 11.5i

  Scenario: Division of a complex number by a double number
    Given an operation "/"
    When I provide a complex number 6.0 + 4.0i
    And I provide a double number 2.0
    Then the operation evaluates to a complex 3.0 + 2.0i

  Scenario: Division of a complex number by a negative double number
    Given an operation "/"
    When I provide a complex number 6.0 + 4.0i
    And I provide a double number -2.0
    Then the operation evaluates to a complex -3.0 - 2.0i

  Scenario Outline: Addition of a complex number and a double that results in a real number
    Given an operation "+"
    When I provide a complex number <complex>
    And I provide a double number <double>
    Then the operation evaluates to a double <result>

    Examples:
      | complex         | double | result |
      | 5.25 + 0.0i     | 3.75   | 9.0    |
      | 7.15 + 0.0i     | 1.85   | 9.0    |

  Scenario: Addition of a double number and a complex number
    Given an operation "+"
    When I provide a double number 5.5
    And I provide a complex number 3 + 4i
    Then the operation evaluates to a complex 8.5 + 4.0i

  Scenario: Addition of a double number and a complex number with a negative real part
    Given an operation "+"
    When I provide a double number 2.5
    And I provide a complex number -1 + 2i
    Then the operation evaluates to a complex 1.5 + 2.0i

  Scenario: Subtraction of a double number and a complex number
    Given an operation "-"
    When I provide a double number 6.0
    And I provide a complex number 2 + 3i
    Then the operation evaluates to a complex 4.0 - 3.0i

  Scenario: Subtraction of a double number and a complex number with a negative imaginary part
    Given an operation "-"
    When I provide a double number 4.2
    And I provide a complex number 1 + -2i
    Then the operation evaluates to a complex 3.2 + 2.0i

  Scenario: Multiplication of a double number and a complex number
    Given an operation "*"
    When I provide a double number 3.5
    And I provide a complex number 2 + 4i
    Then the operation evaluates to a complex 7.0 + 14.0i

  Scenario: Multiplication of a double number and a complex number with negative imaginary part
    Given an operation "*"
    When I provide a double number 2.5
    And I provide a complex number 1 + -3i
    Then the operation evaluates to a complex 2.5 - 7.5i

  Scenario: Division of a double number by a complex number
    Given an operation "/"
    When I provide a double number 6.0
    And I provide a complex number 2 + 2i
    Then the operation evaluates to a complex 1.5 - 1.5i

  Scenario: Division of a double number by a complex number with negative real and imaginary parts
    Given an operation "/"
    When I provide a double number 8.0
    And I provide a complex number -2 - 2i
    Then the operation evaluates to a complex -2.0 + 2.0i

  Scenario: Addition of a integer number and a complex number resulting in a real integer
    Given an operation "+"
    When I provide an integer number 6
    And I provide a complex number 6 + 0i
    Then the operation evaluates to an integer 12

  Scenario: Subtraction of a double number and a complex number resulting in a real double
    Given an operation "-"
    When I provide a double number 7.5
    And I provide a complex number 7.5 + 0.0i
    Then the operation evaluates to a double 0.0
