package parser;

/**
 * ParserException.java
 * @author Maxwell Herron
 * Creates a custom parser exception to be thrown if an illegal production is detected.
 */
public class ParserException extends RuntimeException {

    private String message;

    ParserException(String errorMessage) {
        this.message = errorMessage;
        System.out.println(message);
    }
}
