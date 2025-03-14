package parser;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;

import calculator.Divides;
import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.Minus;
import calculator.MyNumber;
import calculator.Plus;
import calculator.Times;
import parser.antlr.ExprBaseVisitor;
import parser.antlr.ExprParser;
import parser.exceptions.IllegalConstructionRuntime;

/**
 * A visitor implementation for parsing arithmetic expressions using ANTLR.
 * This class converts string input into {@link Expression} objects.
 */
public class ExprVisitorImpl extends ExprBaseVisitor<Expression> {

    private String operator;

    /**
     * Visits a complete expression (i.e. an expression with an EOF at the end).
     *
     * @param ctx The parser context.
     * @return The parsed {@link Expression}.
     */
    @Override
    public Expression visitCompleteExpr(ExprParser.CompleteExprContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * Creates an {@link Expression} based on the provided operator and list of arguments.
     * 
     * @param firstContext The first context (left operand) of the operator expression.
     * @param othersContext A list of subsequent contexts (right operands) for the operator.
     * @param operator The operator to apply.
     * @return An {@link Expression} that represents the operator applied to the operands.
     */
    private Expression operator(ParserRuleContext firstContext, List<? extends ParserRuleContext> othersContext, String operator) {
        List<Expression> args = othersContext.stream().map(this::visit).collect(Collectors.toList());
        args.addFirst(visit(firstContext));
        return operator(args, operator);
    }

    /**
     * Creates an {@link Expression} based on the provided list of arguments and the operator context.
     * 
     * @param argsContext A list of contexts containing the arguments for the operation.
     * @param operatorContext The context of the operator.
     * @return An {@link Expression} that represents the operator applied to the arguments.
     */
    private Expression operator(List<? extends ParserRuleContext> argsContext, ParserRuleContext operatorContext) {
        List<Expression> args = argsContext.stream().map(this::visit).collect(Collectors.toList());
        visit(operatorContext);
        return operator(args, operator);
    }

    /**
     * Creates an {@link Expression} based on the provided arguments and operator string.
     * 
     * @param args A list of {@link Expression} objects representing the operands.
     * @param operator The operator string (e.g., "+", "-", "*", "/").
     * @return The resulting {@link Expression} based on the operator.
     */
    private Expression operator(List<Expression> args, String operator) {
        try {
            return switch (operator) {
                case "+" -> new Plus(args);
                case "-" -> new Minus(args);
                case "*" -> new Times(args);
                case "/" -> new Divides(args);

                // Should never happen, since we know that the operator is one of the above.
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        } catch (IllegalConstruction e) {
            // Should also never happen, since we know that the list args contains at least one element.
            throw new IllegalConstructionRuntime();
        }
    }

    // Infix expressions
    // =================

    /**
     * Visits an addition in an infix expression.
     *
     * @param ctx The parser context.
     * @return An {@link Expression} representing the addition.
     */
    @Override
    public Expression visitAddInfixExpr(ExprParser.AddInfixExprContext ctx) {
        return operator((ParserRuleContext) ctx.infixExpr(), ctx.term(), "+");
    }

    /**
     * Visits a subtraction in an infix expression.
     *
     * @param ctx The parser context.
     * @return An {@link Expression} representing the subtraction.
     */
    @Override
    public Expression visitSubInfixExpr(ExprParser.SubInfixExprContext ctx) {
        return operator((ParserRuleContext) ctx.infixExpr(), ctx.term(), "-");
    }

    /**
     * Visits a division in an infix expression.
     *
     * @param ctx The parser context.
     * @return An {@link Expression} representing the division.
     */
    @Override
    public Expression visitDivTerm(ExprParser.DivTermContext ctx) {
        return operator((ParserRuleContext) ctx.term(), ctx.factor(), "/");
    }

    /**
     * Visits a multiplication in an infix expression.
     *
     * @param ctx The parser context.
     * @return An {@link Expression} representing the multiplication.
     */
    @Override
    public Expression visitMulTerm(ExprParser.MulTermContext ctx) {
        return operator((ParserRuleContext) ctx.term(), ctx.factor(), "*");
    }

    /**
     * Visits an expression enclosed in parentheses.
     *
     * @param ctx The parser context.
     * @return The {@link Expression} contained within the parentheses.
     */
    @Override
    public Expression visitParensFactor(ExprParser.ParensFactorContext ctx) { 
        return visit(ctx.infixExpr());
    }

    // Postfix expressions
    // ===================

    /**
     * Visits an expression with a postfix operator.
     *
     * @param ctx The parser context.
     * @return An {@link Expression} that represents the postfix operation.
     */
    @Override
    public Expression visitWithOperatorPostfix(ExprParser.WithOperatorPostfixContext ctx) {
        return operator(ctx.postfixExpr(), ctx.operator());
    }


    /**
     * Visits a number in a postfix expression.
     *
     * @param ctx The parser context.
     * @return The parsed {@link Expression} representing the number.
     */
    @Override
    public Expression visitNumberPostfix(ExprParser.NumberPostfixContext ctx) {
        return visit(ctx.number());
    }

    // Postfix expressions
    // ===================

    /**
     * Visits an expression with a prefix operator.
     *
     * @param ctx The parser context.
     * @return An {@link Expression} that represents the prefix operation.
     */
    @Override
    public Expression visitWithOperatorPrefix(ExprParser.WithOperatorPrefixContext ctx) {
        return operator(ctx.prefixExpr(), ctx.operator());
    }

    /**
     * Visits a number in a prefix expression.
     *
     * @param ctx The parser context.
     * @return The parsed {@link Expression} representing the number.
     */
    @Override
    public Expression visitNumberPrefix(ExprParser.NumberPrefixContext ctx) {
        return visit(ctx.number());
    }

    // Atoms
    // =====

    /**
     * Visits and sets the operator for addition.
     *
     * @param ctx The parser context.
     * @return Always returns null as this method only sets the operator.
     */
    @Override
    public Expression visitAddOperator(ExprParser.AddOperatorContext ctx) {
        operator = ctx.ADD().getText();
        return null;
    }


    /**
     * Visits and sets the operator for subtraction.
     *
     * @param ctx The parser context.
     * @return Always returns null as this method only sets the operator.
     */
    @Override
    public Expression visitSubOperator(ExprParser.SubOperatorContext ctx) {
        operator = ctx.SUB().getText();
        return null;
    }

    /**
     * Visits and sets the operator for multiplication.
     *
     * @param ctx The parser context.
     * @return Always returns null as this method only sets the operator.
     */
    @Override
    public Expression visitMulOperator(ExprParser.MulOperatorContext ctx) {
        operator = ctx.MUL().getText();
        return null;
    }

    /**
     * Visits and sets the operator for division.
     *
     * @param ctx The parser context.
     * @return Always returns null as this method only sets the operator.
     */
    @Override
    public Expression visitDivOperator(ExprParser.DivOperatorContext ctx) {
        operator = ctx.DIV().getText();
        return null;
    }

    /**
     * Visits an signed integer value.
     *
     * @param ctx The parser context.
     * @return A {@link MyNumber} representing the parsed integer.
     */
    @Override
    public Expression visitSignedInt(ExprParser.SignedIntContext ctx) { 
        return new MyNumber(Integer.parseInt(ctx.getText()));
    }
}
