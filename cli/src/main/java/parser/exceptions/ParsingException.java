package parser.exceptions;

/**
 * An exception that is thrown when an error occurs during parsing.
 */
public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }
}
