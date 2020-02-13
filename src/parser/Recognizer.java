package parser;

import java.io.FileInputStream;
import scanner.*;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Recognizer.java
 * @author Maxwell Herron
 * This class builds all the grammatical rules necessary for a functioning
 * recursive descent recognizer.
 * Tests for this class can be found in /ParserTests/RecognizerTest.java
 */
public class Recognizer {

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    private Token lookahead;

    private Scanner scanner;

    /////////////////////////
    //    CONSTRUCTOR
    /////////////////////////
    public Recognizer(String text, boolean isFile) {
        if(isFile) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("expressions/simplest.pas");
            } catch (FileNotFoundException ex) {
                error( "No file");
            }
            InputStreamReader isr = new InputStreamReader(fis);
            scanner = new Scanner(isr);
        }
        else {
            scanner = new Scanner( new StringReader( text));
        }
        try {
            lookahead = scanner.nextToken();
        } catch (IOException ex) {
            error( "Scan error");
        } catch (BadCharacterException ex) {
            error(ex.getErrorMessage());
        }

    }

    /////////////////////////
    //      METHODS
    /////////////////////////

    public void exp() {

    }

    /**
     *
     */
    public void factor() {
        if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
            match(TokenType.LEFT_PARENTHESES);
            exp();
            match(TokenType.RIGHT_PARENTHESES);
        }
        else if (lookahead.getType() == TokenType.NUMBER) {
            match(TokenType.NUMBER);
        }
        else {
            error("Factor");
        }
    }

    public void match(TokenType expected) {
        System.out.println("match( " + expected + ")");
        if( this.lookahead.getType() == expected) {
            try {
                this.lookahead = scanner.nextToken();
                if( this.lookahead == null) {
                    this.lookahead = new Token( "End of File", null);
                }
            } catch (IOException ex) {
                error( "Scanner exception");
            } catch (BadCharacterException ex) {
                error(ex.toString());
            }
        }
        else {
            error("Match of " + expected + " found " + this.lookahead.getType()
                    + " instead.");
        }
    }

    public void error(String message) {

    }
}
