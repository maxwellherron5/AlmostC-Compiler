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

    /**
     * Main constructor for recognizer.
     * @param text input text that is being parsed.
     * @param isFile true if text is derived from a file, false if not.
     */
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

    /**
     *
     */
    public void program() {
        functionDeclarations();
        match(TokenType.VOID);
        match(TokenType.MAIN);
        match(TokenType.LEFT_PARENTHESES);
        match(TokenType.RIGHT_PARENTHESES);
    }

    /**
     *
     */
    public void identifierList() {

    }

    /**
     *
     */
    public void declarations() {

    }

    /**
     *
     */
    public void type() {

    }

    /**
     *
     */
    public void functionDeclarations() {

    }

    /**
     *
     */
    public void functionDeclaration() {

    }

    /**
     *
     */
    public void functionDefinitions() {

    }

    /**
     *
     */
    public void functionDefinition() {

    }

    /**
     *
     */
    public void parameters() {

    }

    /**
     *
     */
    public void parameterList() {

    }

    /**
     *
     */
    public void compoundStatement() {

    }

    /**
     *
     */
    public void optionalStatements() {

    }

    /**
     *
     */
    public void statementList() {

    }

    /**
     *
     */
    public void statement() {

    }

    /**
     *
     */
    public void variable() {

    }

    /**
     *
     */
    public void expressionList() {

    }

    /**
     *
     */
    public void expression() {

    }

    /**
     *
     */
    public void simpleExpression() {

    }

    /**
     *
     */
    public void simplePart() {
        if (isAddop(lookahead)) {
            term();
            simplePart();
        } else {
            // Lambda
        }
    }

    /**
     *
     */
    public void term() {
        factor();
        termPart();
    }

    /**
     *
     */
    public void termPart() {
        if (isMulop(lookahead)) {
            mulop();
            factor();
            termPart();
        } else {
            // Lambda
        }
    }

    public void exp() {

    }

    /**
     *
     */
    public void factor() {
        switch (lookahead.getType()) {
            /* If type is identifier, it then checks to see if the identifier is followed by
            * either a left parentheses or a left bracket. If not, it ends. */
            case IDENTIFIER:
                match(TokenType.IDENTIFIER);
                if (lookahead.getType() == TokenType.LEFT_BRACKET) {
                    match(TokenType.LEFT_BRACKET);
                    exp();
                    match(TokenType.RIGHT_BRACKET);
                }
                else if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
                    match(TokenType.LEFT_PARENTHESES);
                    exp();
                    match(TokenType.RIGHT_PARENTHESES);
                }
                break;
            case LEFT_PARENTHESES:
                match(TokenType.LEFT_PARENTHESES);
                exp();
                match(TokenType.RIGHT_PARENTHESES);
                break;
            case NUMBER:
                match(TokenType.NUMBER);
                break;
            case NOT:
                match(TokenType.NOT);
                factor();
                break;
            default:
                error("Factor");
                break;
        }
    }

    /**
     *
     */
    public void mulop() {
        switch (lookahead.getType()) {
            case MULTIPLY:
                match(TokenType.MULTIPLY);
                break;
            case DIVIDE:
                match(TokenType.DIVIDE);
                break;
            case MODULO:
                match(TokenType.MODULO);
                break;
            case AND:
                match(TokenType.AND);
                break;
            default:
                error("Mulop");
        }
    }

    /**
     *
     */
    public void addop() {
        switch (lookahead.getType()) {
            case PLUS:
                match(TokenType.PLUS);
                break;
            case MINUS:
                match(TokenType.MINUS);
                break;
            case OR:
                match(TokenType.OR);
                break;
            default:
                error("Addop");
        }
    }

    /**
     *
     */
    public void relop() {
        switch (lookahead.getType()) {
            case EQUAL:
                match(TokenType.EQUAL);
                break;
            case NOT_EQUAL:
                match(TokenType.NOT_EQUAL);
                break;
            case LESS_THAN:
                match(TokenType.LESS_THAN);
                break;
            case LESS_THAN_EQUAL:
                match(TokenType.LESS_THAN_EQUAL);
                break;
            case GREATER_THAN_EQUAL:
                match(TokenType.GREATER_THAN_EQUAL);
                break;
            case GREATER_THAN:
                match(TokenType.GREATER_THAN);
                break;
            default:
                error("Relop");
        }
    }

    /**
     *
     * @param inToken
     * @return
     */
    public Boolean isMulop(Token inToken) {
        boolean answer = false;
        if( inToken.getType() == TokenType.MULTIPLY ||
                inToken.getType() == TokenType.DIVIDE ||
                inToken.getType() == TokenType.MODULO ||
                inToken.getType() == TokenType.AND) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @param inToken
     * @return
     */
    public Boolean isAddop(Token inToken) {
        boolean answer = false;
        if( inToken.getType() == TokenType.PLUS ||
                inToken.getType() == TokenType.MINUS ||
                inToken.getType() == TokenType.OR) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @param inToken
     * @return
     */
    public Boolean isRelop(Token inToken) {
        boolean answer = false;
        if( inToken.getType() == TokenType.EQUAL ||
                inToken.getType() == TokenType.NOT_EQUAL ||
                inToken.getType() == TokenType.LESS_THAN ||
                inToken.getType() == TokenType.LESS_THAN_EQUAL ||
                inToken.getType() == TokenType.GREATER_THAN_EQUAL ||
                inToken.getType() == TokenType.GREATER_THAN) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @param message
     */
    public void error(String message) {
        System.out.println( "Error " + message + " at line " +
                this.scanner.getLine() + " column " +
                this.scanner.getColumn());
    }

    /**
     *
     * @param expected
     */
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

    /**
     * Getter for lookahead token.
     * @return the lookahead token.
     */
    public Token getLookahead() {
        return lookahead;
    }
}