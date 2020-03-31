package parser;

import scanner.BadCharacterException;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Parser.java
 * @author Maxwell Herron
 * This class builds all the grammatical rules necessary for a functioning
 * recursive descent parser.
 * Tests for this class can be found in ParserTest.java
 */
public class Parser {

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    private Token lookahead;

    private Scanner scanner;

    private SymbolTable table;

    /////////////////////////
    //    CONSTRUCTOR
    /////////////////////////

    /**
     * Main constructor for parser.
     * @param text input text that is being parsed.
     * @param isFile true if text is derived from a file, false if not.
     */
    public Parser(String text, boolean isFile) {
        if(isFile) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(text);
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
        }
        table = new SymbolTable();
    }

    /////////////////////////
    //      METHODS
    /////////////////////////

    /**
     * Runs through the production for program.
     */
    public ProgramNode program() {
        ProgramNode progNode = new ProgramNode();
        progNode.setFunctions(functionDeclarations());
        match(TokenType.MAIN);
        match(TokenType.LEFT_PARENTHESES);
        match(TokenType.RIGHT_PARENTHESES);
        progNode.setMain(compoundStatement());
        functionDefinitions();
        return progNode;
    }

    /**
     * Runs through the production for identifierList
     */
    public ArrayList<VariableNode> identifierList() {
        ArrayList<VariableNode> varList = new ArrayList<>();
        String name = lookahead.getLexeme();
        match(TokenType.IDENTIFIER);
        table.addVariableName(name);
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            varList.addAll(identifierList());
        }
        return varList;
    }

    /**
     * Runs through the production for declarations. Note that there is a lambda option
     * if no type is present.
     */
    public DeclarationsNode declarations() {
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
        String name = lookahead.getLexeme();
        match(TokenType.IDENTIFIER);
        table.addFunctionName(name);
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
    public CompoundStatementNode compoundStatement() {
        CompoundStatementNode compNode = new CompoundStatementNode();
        match(TokenType.LEFT_CURLY);
        compNode.setVariables(declarations());
        compNode.addStatement(optionalStatements());
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
    public StatementNode statement() {
        StatementNode stateNode = null;
        switch (lookahead.getType()) {
            case IDENTIFIER:
                if (table.get(lookahead.getLexeme()) == SymbolTable.IdentifierKind.VARIABLE) {
                    AssignmentStatementNode assignOp = new AssignmentStatementNode();
                    assignOp.setLvalue(variable());
                    match(TokenType.ASSIGNMENT);
                    assignOp.setExpression(expression());
                } else if (table.get(lookahead.getLexeme()) == SymbolTable.IdentifierKind.FUNCTION) {
                    return procedureStatement();
                } else {
                    error("statement");
                }
                break;
            case LEFT_CURLY:
                match(TokenType.LEFT_CURLY);
                CompoundStatementNode sstateNode = new CompoundStatementNode();
                sstateNode.
                declarations();
                optionalStatements();
                // just lecture stuff
                stateNode = sstateNode;
                match(TokenType.RIGHT_CURLY);
                break;
            case IF:
                IfStatementNode ifNode = new IfStatementNode();
                match(TokenType.IF);
                ifNode.setTest(expression());
                ifNode.setThenStatement(compoundStatement());
                match(TokenType.ELSE);
                ifNode.setElseStatement(compoundStatement());
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
        return stateNote;
    }

    public StatementNode procedureStatement() {
        StatementNode stateNode = null;
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
            match(TokenType.LEFT_PARENTHESES);
            expressionList();
            match(TokenType.RIGHT_PARENTHESES);
        }
        return stateNode;
    }

    /**
     * Runs through the production for variable. If there is a left bracket present it will call
     * expressionList and then match for a right bracket.
     */
    public VariableNode variable() {
        VariableNode varNode = new VariableNode(lookahead.getLexeme());
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.LEFT_BRACKET) {
            match(TokenType.LEFT_BRACKET);
            expressionList();
            match(TokenType.RIGHT_BRACKET);
        }
        return varNode;
    }

    /**
     * Runs through the production for expressionList. If there is a comma, it will match the comma
     * and call expression again.
     */
    public ArrayList<ExpressionNode> expressionList() {
        ArrayList<ExpressionNode> expList = new ArrayList<>();
        expList.add(expression());
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expList.addAll(expressionList());
        }
        return expList;
    }

    /**
     * Runs through the production for simpleExpression. If it detects a sign, it will call sign,
     * otherwise it will carry through with term and simplePart.
     */
    public ExpressionNode simpleExpression() {
        ExpressionNode expNode = null;
        if (lookahead.getType() == TokenType.PLUS || lookahead.getType() == TokenType.MINUS) {
            SignNode opNode = sign();
            expNode = term();
            expNode = simplePart(expNode);
            opNode.setExpNode(expNode);
            return opNode;
        }
        expNode = term();
        expNode = simplePart(expNode);
        return expNode;
    }

    /**
     * Runs through the production for simplePart. Note that there is a lambda option if no addop
     * is present.
     */
    public ExpressionNode simplePart(ExpressionNode possibleLeft) {
        if (isAddop()) {
            OperationNode opNode = addop();
            ExpressionNode right = term();
            opNode.setLeft(possibleLeft);
            opNode.setRight(right);
            return simplePart(possibleLeft);
        } else {
            // Lambda option
        }
        return possibleLeft;
    }

    /**
     * Runs through the production for term.
     */
    public ExpressionNode term() {
        ExpressionNode left = factor();
        return termPart(left);
    }

    /**
     * Runs through the production for termPart. Note that there is a lambda option if no
     * mulop is present.
     */
    public ExpressionNode termPart(ExpressionNode possibleLeft) {
        if (isMulop()) {
            OperationNode opNode = mulop();
            ExpressionNode right = factor();
            opNode.setLeft(possibleLeft);
            opNode.setRight(termPart(right));
            return opNode;
        } else {
            // Lambda option
        }
        return possibleLeft;
    }

    /**
     * Runs through the production for expression. Optionally calls relop and simpleExpression if
     * a relop is detected after initially calling simpleExpression.
     */
    public ExpressionNode expression() {
        ExpressionNode left = simpleExpression();
        if (isRelop()) {
            OperationNode opNode = relop();
            opNode.setLeft(left);
            opNode.setRight(simpleExpression());
            return opNode;
        }
        return left;
    }


    /**
     * Runs through the production for factor. Uses the first lookahead TokenType to determine
     * which diverging rule to follow.
     */
    public ExpressionNode factor() {
        ExpressionNode expNode = null;
        switch (lookahead.getType()) {
            /* If type is identifier, it then checks to see if the identifier is followed by
            * either a left parentheses or a left bracket. If not, it ends. */
            case IDENTIFIER:
                String name = lookahead.getLexeme();
                match(TokenType.IDENTIFIER);
                if (lookahead.getType() == TokenType.LEFT_BRACKET) {
                    match(TokenType.LEFT_BRACKET);
                    expression();
                    match(TokenType.RIGHT_BRACKET);
                } else if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
                    FunctionCallNode funcNode = new FunctionCallNode(name);
                    match(TokenType.LEFT_PARENTHESES);
                    funcNode.setParameters(expressionList());
                    match(TokenType.RIGHT_PARENTHESES);
                } else if(!table.exists(name)) {
                    return new VariableNode(name);
                }
                break;
            case LEFT_PARENTHESES:
                match(TokenType.LEFT_PARENTHESES);
                expression();
                match(TokenType.RIGHT_PARENTHESES);
                break;
            case NUMBER:
                expNode = new ValueNode(lookahead.getLexeme());
                match(TokenType.NUMBER);
                break;
            case NOT:
                match(TokenType.NOT);
                expNode = new OperationNode(TokenType.NOT, null, factor());
                break;
            default:
                error("Factor");
        }
        return expNode;
    }

    /**
     * Determines what type of mulop the lookahead is, and prints an error if there is no match.
     */
    public OperationNode mulop() {
        OperationNode opNode = null;
        switch (lookahead.getType()) {
            case MULTIPLY:
                match(TokenType.MULTIPLY);
                opNode = new OperationNode(TokenType.MULTIPLY);
                break;
            case DIVIDE:
                match(TokenType.DIVIDE);
                opNode = new OperationNode(TokenType.DIVIDE);
                break;
            case MODULO:
                match(TokenType.MODULO);
                opNode = new OperationNode(TokenType.MODULO);
                break;
            case AND:
                match(TokenType.AND);
                opNode = new OperationNode(TokenType.AND);
                break;
            default:
                error("Mulop");
        }
        return opNode;
    }

    /**
     * Determines what type of addop the lookahead is, and prints an error if there is no match.
     */
    public OperationNode addop() {
        OperationNode opNode = null;
        switch (lookahead.getType()) {
            case PLUS:
                match(TokenType.PLUS);
                opNode = new OperationNode(TokenType.PLUS);
                break;
            case MINUS:
                match(TokenType.MINUS);
                opNode = new OperationNode(TokenType.MINUS);
                break;
            case OR:
                match(TokenType.OR);
                opNode = new OperationNode(TokenType.OR);
                break;
            default:
                error("Addop");
        }
        return opNode;
    }

    /**
     * Determines what type of relop the lookahead is, and prints an error if there is no match.
     */
    public OperationNode relop() {
        OperationNode opNode = null;
        switch (lookahead.getType()) {
            case EQUAL:
                match(TokenType.EQUAL);
                opNode = new OperationNode(TokenType.EQUAL);
                break;
            case NOT_EQUAL:
                match(TokenType.NOT_EQUAL);
                opNode = new OperationNode(TokenType.NOT_EQUAL);
                break;
            case LESS_THAN:
                match(TokenType.LESS_THAN);
                opNode = new OperationNode(TokenType.LESS_THAN);
                break;
            case LESS_THAN_EQUAL:
                match(TokenType.LESS_THAN_EQUAL);
                opNode = new OperationNode(TokenType.LESS_THAN_EQUAL);
                break;
            case GREATER_THAN_EQUAL:
                match(TokenType.GREATER_THAN_EQUAL);
                opNode = new OperationNode(TokenType.GREATER_THAN_EQUAL);
                break;
            case GREATER_THAN:
                match(TokenType.GREATER_THAN);
                opNode = new OperationNode(TokenType.GREATER_THAN);
                break;
            default:
                error("Relop");
        }
        return opNode;
    }

    /**
     * Determines what type of sign the lookahead is, and prints an error if there is no match.
     */
    public SignNode sign() {
        SignNode sigNode = null;
        switch (lookahead.getType()) {
            case PLUS:
                sigNode = new SignNode(TokenType.PLUS);
                match(TokenType.PLUS);
                break;
            case MINUS:
                sigNode = new SignNode(TokenType.MINUS);
                match(TokenType.MINUS);
                break;
            default:
                error("Sign");
        }
        return sigNode;
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

    /**
     * Getter for the symboltable.
     * @return the symboltable.
     */
    public SymbolTable getTable() { return table; }
}
