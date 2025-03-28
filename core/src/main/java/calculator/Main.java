package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A very simple calculator in Java
 * University of Mons - UMONS
 * Software Engineering Lab
 * Faculty of Sciences
 *
 * @author tommens
 */
public class Main {

    /**
     * This is the main method of the application.
     * It provides examples of how to use it to construct and evaluate arithmetic expressions.
     *
     * @param args Command-line parameters are not used in this version
     */
    public static void main(String[] args) {

        Expression e;
        Calculator c = new Calculator();

        try {

            e = new MyInt(8);
            c.print(e);
            c.eval(e);

            List<Expression> params = new ArrayList<>();
            Collections.addAll(params, new MyInt(3), new MyInt(4), new MyInt(5));
            e = new Plus(params);
            c.printExpressionDetails(e, Notation.PREFIX);
            c.eval(e);

            List<Expression> params2 = new ArrayList<>();
            Collections.addAll(params2, new MyInt(5), new MyInt(3));
            e = new Minus(params2);
            c.print(e);
            c.eval(e);

            List<Expression> params3 = new ArrayList<>();
            Collections.addAll(params3, new Plus(params), new Minus(params2));
            e = new Times(params3);
            c.printExpressionDetails(e);
            c.eval(e);

            List<Expression> params4 = new ArrayList<>();
            Collections.addAll(params4, new Plus(params), new Minus(params2), new MyInt(5), MyRationalNumber.createRationalNumber(4, 5));
            e = new Divides(params4);
            c.print(e, Notation.POSTFIX);
            c.eval(e);
        } catch (IllegalConstruction exception) {
            System.out.println("cannot create operations without parameters");
        }
    }

}
