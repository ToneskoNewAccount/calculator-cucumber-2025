package cli;

import calculator.Calculator;
import parser.Parser;

public class Main {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();

        Calculator calculator = new Calculator();
        calculator.print(parser.parse("(1 + 2) * 3"));
    }
}
