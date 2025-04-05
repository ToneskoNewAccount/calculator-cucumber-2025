package core.operation;

import java.util.List;

import core.Expression;
import core.exception.IllegalConstruction;
import core.number.MyNumber;

/** This class represents the arithmetic division operation "/".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * @see BinaryOperation
 * @see Minus
 * @see Times
 * @see Plus
 */
public final class Divides extends BinaryOperation
{
  /**
   * Class constructor specifying a number of Expressions to divide.
   *
   * @param elist The list of Expressions to divide
   * @throws IllegalConstruction If an empty list of expressions if passed as parameter
   */
  public Divides(List<Expression> elist) throws IllegalConstruction {
	super(elist);
	symbol = "/";
	neutral = 1;
  }

    /**
     * The actual computation of the (binary) arithmetic division of two doubles.
     * @param right second number to divide
     * @param left  first number to divide
     * @return The result of dividing the two numbers
     */
    @Override
    public MyNumber op(MyNumber left, MyNumber right) {
        return  left.divide(right);
    }

}
