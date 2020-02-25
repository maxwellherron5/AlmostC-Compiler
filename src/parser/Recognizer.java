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
 * Tests for this class can be found in RecognizerTest.java
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
        } catch (BadCharacterException ex) { }
    }

    /////////////////////////
    //      METHODS
    /////////////////////////

    /**
     * Runs through the production for program.
     */
    public void program() {
        functionDeclarations();
        match(TokenType.MAIN);
        match(TokenType.LEFT_PARENTHESES);
        match(TokenType.RIGHT_PARENTHESES);
        compoundStatement();
        functionDefinitions();
    }

    /**
     * Runs through the production for identifierList
     */
    public void identifierList() {
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            identifierList();
        }
    }

    /**
     * Runs through the production for declarations. Note that there is a lambda option
     * if no type is present.
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
     * Determines which type keyword the token is. If there is no match, it will print an error.
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
     * Runs through the production for functionDeclarations. Note that there is a lambda option if no
     * type is present.
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
     * Runs through the production for functionDeclaration.
     */
    public void functionDeclaration() {
        type();
        match(TokenType.IDENTIFIER);
        parameters();
    }

    /**
     * Runs through the production for functionDefinitions. Note that there is a lambda option if no
     * type is present.
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
     * Runs through the production for functionDefinition.
     */
    public void functionDefinition() {
        type();
        match(TokenType.IDENTIFIER);
        parameters();
        compoundStatement();
    }

    /**
     * Runs through the production for parameters.
     */
    public void parameters() {
        match(TokenType.LEFT_PARENTHESES);
        parameterList();
        match(TokenType.RIGHT_PARENTHESES);
    }

    /**
     * Runs through the production for parameterList. Note that there are two diverging options; if there is
     * a comma present, it will call itself again. Along with that, there is a lambda option, to allow
     * for functions that have no parameters.
     */
    public void parameterList() {
        if (isType()) {
            type();
            match(TokenType.IDENTIFIER);
            if (lookahead.getType() == TokenType.COMMA) {
                match(TokenType.COMMA);
                parameterList();
            }
        } else {
            // Lambda option
        }
    }

    /**
     * Runs through the production for compoundStatement.
     */
    public void compoundStatement() {
        match(TokenType.LEFT_CURLY);
        declarations();
        optionalStatements();
        match(TokenType.RIGHT_CURLY);
    }

    /**
     * Runs through the production for optionalStatements. Note that there is a lambda option if no
     * statement is present.
     */
    public void optionalStatements() {
        if(isStatement()) {
            statementList();
        } else {
            // Lambda option
        }
    }

    /**
     * Runs through the production for statementList. Note that if a semicolon is present, it will recursively
     * will recursively call itself until there is not one.
     */
    public void statementList() {
        statement();
        if (lookahead.getType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            statementList();
        }
    }

    /**
     * Runs through the production for statement. Uses the first lookahead tokenType value to
     * determine which diverging rule to follow.
     */
    public void statement() {
        switch (lookahead.getType()) {
            case IDENTIFIER:
                variable();
                match(TokenType.ASSIGNMENT);
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
     * Runs through the production for variable. If there is a left bracket present it will call
     * expressionList and then match for a right bracket.
     */
    public void variable() {
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.LEFT_BRACKET) {
            match(TokenType.LEFT_BRACKET);
            expressionList();
            match(TokenType.RIGHT_BRACKET);
        }
    }

    /**
     * Runs through the production for expressionList. If there is a comma, it will match the comma
     * and call expression again.
     */
    public void expressionList() {
        expression();
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expression();
        }
    }

    /**
     * Runs through the production for simpleExpression. If it detects a sign, it will call sign,
     * otherwise it will carry through with term and simplePart.
     */
    public void simpleExpression() {
        if (lookahead.getType() == TokenType.PLUS || lookahead.getType() == TokenType.MINUS) {
            sign();
        }
        term();
        simplePart();
    }

    /**
     * Runs through the production for simplePart. Note that there is a lambda option if no addop
     * is present.
     */
    public void simplePart() {
        if (isAddop()) {
            addop();
            term();
            simplePart();
        } else {
            // Lambda option
        }
    }

    /**
     * Runs through the production for term.
     */
    public void term() {
        factor();
        termPart();
    }

    /**
     * Runs through the production for termPart. Note that there is a lambda option if no
     * mulop is present.
     */
    public void termPart() {
        if (isMulop()) {
            mulop();
            factor();
            termPart();
        } else {
            // Lambda option
        }
    }

    /**
     * Runs through the production for expression. Optionally calls relop and simpleExpression if
     * a relop is detected after initially calling simpleExpression.
     */
    public void expression() {
        simpleExpression();
        if (isRelop()) {
            relop();
            simpleExpression();
        }
    }


    /**
     * Runs through the production for factor. Uses the first lookahead TokenType to determine
     * which diverging rule to follow.
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
     * Determines what type of mulop the lookahead is, and prints an error if there is no match.
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
     * Determines what type of addop the lookahead is, and prints an error if there is no match.
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
     * Determines what type of relop the lookahead is, and prints an error if there is no match.
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
     * Determines what type of sign the lookahead is, and prints an error if there is no match.
     */
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
     * Determines if the lookahead TokenType is a mulop.
     * @return True if the lookahead TokenType is a mulop, false if not.
     */
    public boolean isMulop() {
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
     * Determines if the lookahead TokenType is an addop.
     * @return True if the lookahead TokenType is an addop, false if not.
     */
    public boolean isAddop() {
        boolean answer = false;
        if( lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ||
                lookahead.getType() == TokenType.OR) {
            answer = true;
        }
        return answer;
    }

    /**
     * Determines if the lookahead TokenType is a relop.
     * @return True if the lookahead TokenType is a relop, false if not.
     */
    public boolean isRelop() {
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
     * Determines if the lookahead TokenType is a what is expected of a statement.
     * @return True if the lookahead TokenType matches what is expected of a statement, false if not.
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
     * Determines if the lookahead TokenType is one of the type keywords.
     * @return True if the lookahead TokenType is one of the type keywords, false if not.
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
     * Prints an error caused by no available match.
     * @param message the error message.
     */
    public void error(String message) {
        throw new ParserException( "Error " + message + " at line " +
                this.scanner.getLine() + " column " +
                this.scanner.getColumn());
    }

    /**
     * Attempts to match the lookahead TokenType with an expected TokenType. If no match is possible,
     * it will call the error function to print out what was expected, and what the actual was.
     * @param expected the expected TokenType
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
