grammar Expr;

completeExpr: expr EOF ;

expr
    : infixExpr
    | postfixExpr
    | prefixExpr
    ;

// Infix expressions
// =================

infixExpr
    : infixExpr ('+' term)+     # AddInfixExpr
    | infixExpr ('-' term)+     # SubInfixExpr
    | term                      # TermInfixExpr
    ;

term
    : term ('*' factor)+        # MulTerm
    | term ('/' factor)+        # DivTerm
    | factor                    # FactorTerm
    ;

factor
    : '(' infixExpr ')'         # ParensFactor
    | number                    # NumberFactor
    ;

// Postfix expressions
// ===================

postfixExpr
    : '(' postfixExpr (postfixExpr* | (',' postfixExpr)*) ')' operator      # WithOperatorPostfix
    | number                                                                # NumberPostfix
    ;

// Prefix expressions
// ==================

prefixExpr
    : operator '(' prefixExpr (prefixExpr* | (',' prefixExpr)*) ')'         # WithOperatorPrefix
    | number                                                                # NumberPrefix
    ;

// Atoms
// =====

number
    : signedInt
    ;

operator
    : ADD                       # AddOperator
    | SUB                       # SubOperator
    | DIV                       # DivOperator
    | MUL                       # MulOperator
    ;

signedInt: SUB? INT ;

ADD: '+' ;
SUB: '-' ;
DIV: '/' ;
MUL: '*' ;

INT: [0-9]+ ;       // Match numbers
WS: [ \t\r\n]+ -> skip ; // Ignore whitespaces
