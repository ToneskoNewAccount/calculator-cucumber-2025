package calculator;

import java.util.List;

/** This class represents the arithmetic division operation "/".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * @see Operation
 * @see Minus
 * @see Times
 * @see Plus
 */
public final class Divides extends Operation
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
     *
     * @param l The first double
     * @param r The second double that should divide the first
     * @return The double that is the result of the division
     */
 public double op(double l, double r)
    {
        if (r == 0) {
            System.err.println("Warning: Division by zero detected. Returning NaN.");

            // Return NaN if division by zero
            return Double.NaN;
        }
        return l / r;
    }
}
