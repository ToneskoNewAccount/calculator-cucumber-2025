Feature: Parset
    This feature provides a range of scenarios corresponding to the
    intended external behaviour of the parser.

    Background:
        Given I initialise a parser
        Given I initialise a calculator

    Scenario: Parsing an integer
        Given an expression '6'
        When I parse the expression
        Then the result should be 6

        Given an expression '-6'
        When I parse the expression
        Then the result should be -6

        Given an expression '- 6'
        When I parse the expression
        Then the result should be -6

    # Testing infix expressions
    # =========================

    Scenario Outline: Parsing a simple infix expression to test that every operator is parsed correctly
        Given an expression '6 <operator> 2'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | operator | result |
            | +        | 8      |
            | -        | 4      |
            | *        | 12     |
            | /        | 3      |

    Scenario Outline: Parsing a longer infix expression to test that the order of operations is correct
        Given an expression '12 <op1> 4 <op2> 2'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | op1 | op2 | result |
            | +   | *   | 20     |
            | +   | /   | 14     |
            | -   | *   | 4      |
            | -   | /   | 10     |
            | *   | *   | 96     |
            | *   | /   | 24     |
            | /   | *   | 6      |
            | /   | /   | 1.5    |

    Scenario Outline: Parsing a infix expression to test that the parentheses work correctly
        Given an expression '<expression>'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | expression                    | result |
            | ( -6 + 2 ) * 3                | -12    |
            | -6 + ( 2 * 3 )                | 0      |
            | ( 6 + 2 ) * 3                 | 24     |
            | 6 + ( 2 * 3 )                 | 12     |
            | ( 6 + 2 ) * ( 3 - 1 )         | 16     |
            | ( 6 + 2 ) * ( 3 - 1 ) / 2     | 8      |
            | ( 6 + 2 ) / ( 3 - 1 ) * 2     | 8      |
            | ( 6 + 2 ) / ( ( 3 - 1 ) * 2 ) | 2      |

    # Testing postfix expressions
    # ===========================

    Scenario Outline: Parsing a simple postfix expression to test that every operator is parsed correctly
        Given an expression '(6 2) <operator>'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | operator | result |
            | +        | 8      |
            | -        | 4      |
            | *        | 12     |
            | /        | 3      |

    Scenario Outline: Parsing a longer postfix expression to test that the order of operations is correct
        Given an expression '((12 4) <op1> 2) <op2>'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | op1 | op2 | result |
            | +   | *   | 32     |
            | +   | /   | 8      |
            | -   | *   | 16     |
            | -   | /   | 4      |
            | *   | *   | 96     |
            | *   | /   | 24     |
            | /   | *   | 6      |
            | /   | /   | 1.5    |

    Scenario: Parsing postfix expression to test that ',' works correctly
        Given an expression '(6, 2, 3) +'
        When I parse the expression
        Then the result should be 11

        Given an expression '((6, 2, 3) + 2) *'
        When I parse the expression
        Then the result should be 22

    # Testing prefix expressions
    # ==========================

    Scenario Outline: Parsing a simple prefix expression to test that every operator is parsed correctly
        Given an expression '<operator> ( 6 2 )'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | operator | result |
            | +        | 8      |
            | -        | 4      |
            | *        | 12     |
            | /        | 3      |

    Scenario Outline: Parsing a longer prefix expression to test that the order of operations is correct
        Given an expression '<op2> ( <op1> (12 4) 2 )'
        When I parse the expression
        Then the result should be <result>

        Examples:
            | op1 | op2 | result |
            | +   | *   | 32     |
            | +   | /   | 8      |
            | -   | *   | 16     |
            | -   | /   | 4      |
            | *   | *   | 96     |
            | *   | /   | 24     |
            | /   | *   | 6      |
            | /   | /   | 1.5    |

    Scenario: Parsing prefix expression to test that ',' works correctly
        Given an expression '+ (6, 2, 3)'
        When I parse the expression
        Then the result should be 11

        Given an expression '* ( + (6, 2, 3) 2 )'
        When I parse the expression
        Then the result should be 22

    # Other
    # =====

    Scenario: Testing that is not possible to use two different notations in the same expression
        Given an expression '(+ (6 2) 2) +'
        When I parse the expression
        Then the result should be an error

        Given an expression '(+ (6 2) + 2)'
        When I parse the expression
        Then the result should be an error

        Given an expression '((6 2) + + 2)'
        When I parse the expression
        Then the result should be an error

    Scenario: Testing that whitespaces are being ignored
        Given an expression with whitespaces ' ( 6  \n\t\r  +  \n\t\r  2 )  '
        When I parse the expression
        Then the result should be 8

        Given an expression '6+2'
        When I parse the expression
        Then the result should be 8

    Scenario: Testing that an expression is parsed completely
        Given an expression '6 + 2 +'
        When I parse the expression
        Then the result should be an error
