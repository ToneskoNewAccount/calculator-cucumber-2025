Feature: Complex Number Operations

  Background:
    Given A complex calculator

  Scenario: Addition of two complex numbers
    Given a complex operation "+"
    When I provide a complex number (3;4i)
    And I provide a complex number (1;2i)
    Then The result should be (4;6i)

  Scenario: Addition of one complex numbers and one real number
    Given a complex operation "+"
    When I provide a complex number (3;4i)
    And I provide a real number 2
    Then The result should be (5;4i)

  Scenario: Subtraction of two complex numbers
    Given a complex operation "-"
    When I provide a complex number (-5;7i)
    And I provide a complex number (2;3i)
    Then The result should be (-7;4i)

  Scenario: Multiplication of two complex numbers
    Given a complex operation "*"
    When I provide a complex number (2;3i)
    And I provide a complex number (1;4i)
    Then The result should be (-10;11i)

  Scenario: Division of two complex numbers
    Given a complex operation "/"
    When I provide a complex number (4;2i)
    And I provide a complex number (3;1i)
    Then The result should be (1.4;0.2i)

  Scenario: Division of one complex number and one real number
    Given a complex operation "/"
    When I provide a complex number (4;2i)
    And I provide a real number 2
    Then The result should be (2;1i)

