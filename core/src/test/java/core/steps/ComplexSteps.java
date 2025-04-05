package core.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import core.number.MyComplexNumber;
import core.number.MyDouble;
import core.number.MyInt;
import core.number.MyNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ComplexSteps {

    public int validateSign(String sign) {
        if (!sign.equals("+") && !sign.equals("-")) {
            fail("Invalid sign: " + sign);
        }

        return sign.equals("+") ? 1 : -1;
    }

    @When("^I provide a complex number (-?\\d+.\\d+) ([+-]) (-?\\d+.\\d+)i$")
    public void whenAComplexNumberWithDoubles(double real, String sign, double imaginary) {
        int imaginarySign = validateSign(sign);
        CommonSteps.addParam(MyComplexNumber.createComplexNumber(new MyDouble(real), new MyDouble(imaginarySign * imaginary)));
    }

    @When("I provide a complex number {int} {word} {int}i")
    public void whenAComplexNumberWithInts(int real, String sign, int imaginary) {
        int imaginarySign = validateSign(sign);
        CommonSteps.addParam(MyComplexNumber.createComplexNumber(new MyInt(real), new MyInt(imaginarySign * imaginary)));
    }

    @Then("^the operation evaluates to a complex (-?\\d+.\\d+) ([+-]) (-?\\d+.\\d+)i$")
    public void theResultShouldBeAComplexWithDoubles(double res_real, String sign, double res_imag) {
        int imaginarySign = validateSign(sign);
        MyNumber res = CommonSteps.c.eval(CommonSteps.op);
        MyNumber expected = MyComplexNumber.createComplexNumber(new MyDouble(res_real), new MyDouble(imaginarySign * res_imag));
        assertEquals(expected, res);
    }

    @Then("the operation evaluates to a complex {int} {word} {int}i")
    public void theResultShouldBeAComplexWithInts(int res_real, String sign, int res_imag) {
        int imaginarySign = validateSign(sign);
        MyNumber res = CommonSteps.c.eval(CommonSteps.op);
        MyNumber expected = MyComplexNumber.createComplexNumber(new MyInt(res_real), new MyInt(imaginarySign * res_imag));
        assertEquals(expected, res);
    }
}
