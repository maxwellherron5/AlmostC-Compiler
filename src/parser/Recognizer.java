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
        compoundStatement();
        functionDefinitions();
    }

    /**
     *
     */
    public void identifierList() {
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            identifierList();
        }
    }

    /**
     *
     */
    public void declarations() {
        if (isType()) {
            type();
            identifierList();
            match(TokenType.SEMICOLON);
            declarations();
        } else {
            // Lambda option
        }
    }

    /**
     *
     */
    public void type() {
        switch (lookahead.getType()) {
            case VOID:
                match(TokenType.VOID);
                break;
            case INT:
                match(TokenType.INT);
                break;
            case FLOAT:
                match(TokenType.FLOAT);
                break;
            default:
                error("Type");
        }
    }

    /**
     *
     */
    public void functionDeclarations() {
        if (isType()) {
            functionDeclaration();
            match(TokenType.SEMICOLON);
            functionDeclarations();
        } else {
            // Lambda option
        }
    }

    /**
     *
     */
    public void functionDeclaration() {
        type();
        match(TokenType.IDENTIFIER);
        parameters();
    }

    /**
     *
     */
    public void functionDefinitions() {
        if (isType()) {
            functionDefinition();
            functionDefinitions();
        } else {
            // Lambda option
        }
    }

    /**
     *
     */
    public void functionDefinition() {
        type();
        match(TokenType.IDENTIFIER);
        parameters();
        compoundStatement();
    }

    /**
     *
     */
    public void parameters() {
        match(TokenType.LEFT_PARENTHESES);
        parameterList();
        match(TokenType.RIGHT_PARENTHESES);
    }

    /**
     *
     */
    public void parameterList() {
        type();
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            parameterList();
        }
    }

    /**
     *
     */
    public void compoundStatement() {
        match(TokenType.LEFT_CURLY);
        declarations();
        optionalStatements();
        match(TokenType.RIGHT_CURLY);
    }

    /**
     *
     */
    public void optionalStatements() {
        if(isStatement()) {
            statementList();
        } else {
            // Lambda
        }
    }

    /**
     *
     */
    public void statementList() {
        statement();
        if (lookahead.getType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            statementList();
        }
    }

    /**
     *
     */
    public void statement() {
        switch (lookahead.getType()) {
            case IDENTIFIER:
                variable();
                match(TokenType.EQUAL);
                expression();
                break;
            case LEFT_CURLY:
                match(TokenType.LEFT_CURLY);
                declarations();
                optionalStatements();
                match(TokenType.RIGHT_CURLY);
                break;
            case IF:
                match(TokenType.IF);
                expression();
                compoundStatement();
                match(TokenType.ELSE);
                compoundStatement();
                break;
            case WHILE:
                match(TokenType.WHILE);
                expression();
                compoundStatement();
                break;
            case READ:
                match(TokenType.READ);
                match(TokenType.LEFT_PARENTHESES);
                match(TokenType.IDENTIFIER);
                match(TokenType.RIGHT_PARENTHESES);
                break;
            case WRITE:
                match(TokenType.WRITE);
                match(TokenType.LEFT_PARENTHESES);
                expression();
                match(TokenType.RIGHT_PARENTHESES);
                break;
            case RETURN:
                match(TokenType.RETURN);
                expression();
        }
    }

    /**
     *
     */
    public void variable() {
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
            match(TokenType.LEFT_PARENTHESES);
            expressionList();
            match(TokenType.RIGHT_PARENTHESES);
        }
    }

    /**
     *
     */
    public void expressionList() {
        expression();
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expression();
        }
    }

    /**
     *
     */
    public void simpleExpression() {
        if (lookahead.getType() == TokenType.PLUS || lookahead.getType() == TokenType.MINUS) {
            sign();
        }
        term();
        simplePart();
    }

    /**
     *
     */
    public void simplePart() {
        if (isAddop(lookahead)) {
            addop();
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

    /**
     *
     */
    public void expression() {
        simpleExpression();
        if (isRelop(lookahead)) {
            relop();
            simpleExpression();
        }
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
                    expression();
                    match(TokenType.RIGHT_BRACKET);
                }
                else if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
                    match(TokenType.LEFT_PARENTHESES);
                    expression();
                    match(TokenType.RIGHT_PARENTHESES);
                }
                break;
            case LEFT_PARENTHESES:
                match(TokenType.LEFT_PARENTHESES);
                expression();
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

    public void sign() {
        switch (lookahead.getType()) {
            case PLUS:
                match(TokenType.PLUS);
                break;
            case MINUS:
                match(TokenType.MINUS);
                break;
            default:
                error("Sign");
        }
    }

    /**
     *
     * @return
     */
    public boolean isMulop(Token lookahead) {
        boolean answer = false;
        if( lookahead.getType() == TokenType.MULTIPLY ||
                lookahead.getType() == TokenType.DIVIDE ||
                lookahead.getType() == TokenType.MODULO ||
                lookahead.getType() == TokenType.AND) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isAddop(Token lookahead) {
        boolean answer = false;
        if( lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ||
                lookahead.getType() == TokenType.OR) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isRelop(Token lookahead) {
        boolean answer = false;
        if( lookahead.getType() == TokenType.EQUAL ||
                lookahead.getType() == TokenType.NOT_EQUAL ||
                lookahead.getType() == TokenType.LESS_THAN ||
                lookahead.getType() == TokenType.LESS_THAN_EQUAL ||
                lookahead.getType() == TokenType.GREATER_THAN_EQUAL ||
                lookahead.getType() == TokenType.GREATER_THAN) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isStatement() {
        boolean answer = false;
        if (lookahead.getType() == TokenType.IF || lookahead.getType() == TokenType.WHILE ||
            lookahead.getType() == TokenType.READ || lookahead.getType() == TokenType.WRITE ||
            lookahead.getType() == TokenType.RETURN || lookahead.getType() == TokenType.IDENTIFIER ||
            lookahead.getType() == TokenType.LEFT_CURLY) {
                answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isFactor() {
        boolean answer = false;
        if (lookahead.getType() == TokenType.IDENTIFIER || lookahead.getType() == TokenType.NUMBER ||
            lookahead.getType() == TokenType.LEFT_PARENTHESES || lookahead.getType() == TokenType.NOT) {
                answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isType() {
        boolean answer = false;
        if (lookahead.getType() == TokenType.VOID || lookahead.getType() == TokenType.INT ||
            lookahead.getType() == TokenType.FLOAT) {
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
