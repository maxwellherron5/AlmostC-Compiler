package parser;

/**
 *
 */
public class ParserException extends RuntimeException {

    private String message;

    ParserException(String message) {
        this.message = message;
        System.out.println(message);
    }



}
