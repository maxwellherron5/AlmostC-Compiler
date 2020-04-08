package parser;

import scanner.BadCharacterException;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class builds all the grammatical rules necessary for a functioning
 * recursive descent parser. By running program, it will return a syntax tree generated
 * from the input file/string.
 * Tests for this class can be found in ParserTest.java
 * Parser.java
 * @author Maxwell Herron
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
     * Top level function call. This will run through all the productions and return
     * a program node.
     * @return progNode
     */
    public ProgramNode program() {
        ProgramNode progNode = new ProgramNode();
        functionDeclarations();
        match(TokenType.MAIN);
        match(TokenType.LEFT_PARENTHESES);
        match(TokenType.RIGHT_PARENTHESES);
        progNode.setMain(compoundStatement());
        progNode.setFunctions(functionDefinitions());
        return progNode;
    }

    /**
     * Runs through the production for identifierList. Returns and arraylist of
     * Strings representing identifier names.
     * @return varList
     */
    public ArrayList<String> identifierList() {
        ArrayList<String> varList = new ArrayList<>();
        String name = lookahead.getLexeme();
        varList.add(name);
        match(TokenType.IDENTIFIER);
        table.addVariableName(name);
        while (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            varList.add(lookahead.getLexeme());
            table.addVariableName(lookahead.getLexeme());
            match(TokenType.IDENTIFIER);
        }
        return varList;
    }

    /**
     * Runs through the production for declarations. Returns a DeclarationsNode.
     * @return decsNode
     */
    public DeclarationsNode declarations() {
        DeclarationsNode decsNode = new DeclarationsNode();
        while (isType()) {
            type();
            ArrayList<String> varList = identifierList();
            //declarations();
            for (String var : varList) {
                VariableNode varNode = new VariableNode(var);
                decsNode.addVariable(varNode);
            }
            match(TokenType.SEMICOLON);
//        } else {
//            // Lambda option
        }
        return decsNode;
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
     * Runs through the production for functionDeclaration. Adds function names to the symbol table.
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
     * type is present. Returns a functions node containing function definitions.
     * @return funcsNode
     */
    public FunctionsNode functionDefinitions() {
        FunctionsNode funcsNode = new FunctionsNode();
        while (isType()) {
            funcsNode.addFunctionDefinition(functionDefinition());
        }
        return funcsNode;
    }

    /**
     * Runs through the production for functionDefinition. Returns a function node containing
     * a function definition.
     * @return funcNode
     */
    public FunctionNode functionDefinition() {
        type();
        FunctionNode funcNode = new FunctionNode(lookahead.getLexeme());
        match(TokenType.IDENTIFIER);
        ArrayList<VariableNode> params = parameters();
        for (VariableNode var : params) {
            funcNode.addParameter(var);
        }
        funcNode.setBody(compoundStatement());
        return funcNode;
    }

    /**
     * Runs through the production for parameters.
     * @return params an arraylist of variablenodes that will be function parameters
     */
    public ArrayList<VariableNode> parameters() {
        match(TokenType.LEFT_PARENTHESES);
        ArrayList<VariableNode> params = new ArrayList<>();
        params = parameterList();
        match(TokenType.RIGHT_PARENTHESES);
        return params;
    }

    /**
     * Runs through the production for parameterList. Note that there are two diverging options; if there is
     * a comma present, it will call itself again. Along with that, there is a lambda option, to allow
     * for functions that have no parameters.
     * @return paramerList an arraylist of variables
     */
    public ArrayList<VariableNode> parameterList() {
        ArrayList<VariableNode> varList = new ArrayList<>();
        if (isType()) {
            type();
            VariableNode var = new VariableNode(lookahead.getLexeme());
            match(TokenType.IDENTIFIER);
            if (lookahead.getType() == TokenType.COMMA) {
                match(TokenType.COMMA);
                varList.addAll(parameterList());
            }
        } else {
            // Lambda option
        }
        return varList;
    }

    /**
     * Runs through the production for compoundStatement.
     * @return compNode a CompoundStatementNode
     */
    public CompoundStatementNode compoundStatement() {
        CompoundStatementNode compNode = new CompoundStatementNode();
        match(TokenType.LEFT_CURLY);
        compNode.setVariables(declarations());
        ArrayList<StatementNode> stateList = optionalStatements();
        for(StatementNode state : stateList) {
            compNode.addStatement(state);
        }
        match(TokenType.RIGHT_CURLY);
        return compNode;
    }

    /**
     * Runs through the production for optionalStatements. Note that there is a lambda option if no
     * statement is present.
     * @return stateList an arraylist of StatementNodes
     */
    public ArrayList<StatementNode> optionalStatements() {
        ArrayList<StatementNode> stateList = new ArrayList<>();
        if(isStatement()) {
            stateList = statementList();
        } else {
            // Lambda option
        }
        return stateList;
    }

    /**
     * Runs through the production for statementList. Note that if a semicolon is present, it will recursively
     * will recursively call itself until there is not one.
     * @return stateList an arraylist of StatementNodes
     */
    public ArrayList<StatementNode> statementList() {
        ArrayList<StatementNode> stateList = new ArrayList<>();
        stateList.add(statement());
        match(TokenType.SEMICOLON);
        if (isStatement()) {
            stateList.addAll(statementList());
        }
        return stateList;
    }

    /**
     * Runs through the production for statement. Uses the first lookahead tokenType value to
     * determine which diverging rule to follow.
     * @return stateNode a StatementNode generated from the statement production
     */
    public StatementNode statement() {
        StatementNode stateNode = null;
        switch (lookahead.getType()) {
            case IDENTIFIER:
                if (table.get(lookahead.getLexeme()) == SymbolTable.IdentifierKind.FUNCTION ){
                    stateNode = procedureStatement();
                } else {
                    AssignmentStatementNode assignOp = new AssignmentStatementNode();
                    assignOp.setLvalue(variable());
                    match(TokenType.ASSIGNMENT);
                    assignOp.setExpression(expression());
                    stateNode = assignOp;
                }
                break;
            case LEFT_CURLY:
                stateNode = compoundStatement();
                break;
            case IF:
                IfStatementNode ifNode = new IfStatementNode();
                match(TokenType.IF);
                ifNode.setTest(expression());
                ifNode.setThenStatement(compoundStatement());
                match(TokenType.ELSE);
                ifNode.setElseStatement(compoundStatement());
                stateNode = ifNode;
                break;
            case WHILE:
                WhileStatementNode whileNode = new WhileStatementNode();
                match(TokenType.WHILE);
                whileNode.setTest(expression());
                whileNode.setDoStatement(compoundStatement());
                stateNode = whileNode;
                break;
            case READ:
                ReadStatementNode readNode = new ReadStatementNode();
                match(TokenType.READ);
                match(TokenType.LEFT_PARENTHESES);
                readNode.setInput(lookahead.getLexeme());
                stateNode = readNode;
                match(TokenType.IDENTIFIER);
                match(TokenType.RIGHT_PARENTHESES);
                break;
            case WRITE:
                WriteStatementNode writeNode = new WriteStatementNode();
                match(TokenType.WRITE);
                match(TokenType.LEFT_PARENTHESES);
                writeNode.setOutput(expression());
                stateNode = writeNode;
                match(TokenType.RIGHT_PARENTHESES);
                break;
            case RETURN:
                ReturnStatementNode returnNode = new ReturnStatementNode();
                match(TokenType.RETURN);
                returnNode.setReturnValue(expression());
                stateNode = returnNode;
        }
        return stateNode;
    }

    /**
     * Production for a void function call.
     * @return stateNode a ProcedureStatementNode of a void func call;
     */
    public StatementNode procedureStatement() {
        String name = lookahead.getLexeme();
        ProcedureStatementNode stateNode = new ProcedureStatementNode(name);
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
            match(TokenType.LEFT_PARENTHESES);
            ArrayList<ExpressionNode> expList = new ArrayList<>();
            expressionList(expList);
            stateNode.setParameters(expList);
            match(TokenType.RIGHT_PARENTHESES);
        }
        return stateNode;
    }

    /**
     * Runs through the production for variable. If there is a left bracket present it will call
     * expressionList and then match for a right bracket.
     * @return varNode a VariableNode
     */
    public VariableNode variable() {
        VariableNode varNode = new VariableNode(lookahead.getLexeme());
        match(TokenType.IDENTIFIER);
        if (lookahead.getType() == TokenType.LEFT_BRACKET) {
            match(TokenType.LEFT_BRACKET);
            expression();
            match(TokenType.RIGHT_BRACKET);
        }
        return varNode;
    }

    /**
     * Runs through the production for expressionList. If there is a comma, it will match the comma
     * and call expression again.
     * @param expList an arrayList of expressions to be recursively added to
     */
    public void expressionList(ArrayList<ExpressionNode> expList) {
        expList.add(expression());
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expressionList(expList);
        } else {
            // Lambda option
        }
    }

    /**
     * Runs through the production for simpleExpression. If it detects a sign, it will call sign,
     * otherwise it will carry through with term and simplePart.
     * @return expNode an ExpressionNode
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
     * @return possibleLeft an ExpressionNode containing data for the possible left side of an operation
     */
    public ExpressionNode simplePart(ExpressionNode possibleLeft) {
        if (isAddop()) {
            OperationNode opNode = addop();
            ExpressionNode right = term();
            opNode.setLeft(possibleLeft);
            opNode.setRight(right);
            return simplePart(opNode);
        } else {
            // Lambda option
        }
        return possibleLeft;
    }

    /**
     * Runs through the production for term.
     * @return left an ExpressionNode
     */
    public ExpressionNode term() {
        ExpressionNode left = factor();
        return termPart(left);
    }

    /**
     * Runs through the production for termPart. Note that there is a lambda option if no
     * mulop is present.
     * @param possibleLeft an ExpressionNode containing data for the possible left side of an operation
     * @return possibleLeft an ExpressionNode containing data for the left side of an operation
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
     * @return left an ExpressionNode containing data for the left side of an operation
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
     * @return expNode an ExpressionNode that has its meaning derived from the internal switch statement.
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
                    expNode = expression();
                    match(TokenType.RIGHT_BRACKET);
                } else if (lookahead.getType() == TokenType.LEFT_PARENTHESES) {
                    FunctionCallNode funcNode = new FunctionCallNode(name);
                    match(TokenType.LEFT_PARENTHESES);
                    ArrayList<ExpressionNode> expList = new ArrayList<>();
                    expressionList(expList);
                    funcNode.setParameters(expList);
                    expNode = funcNode;
                    match(TokenType.RIGHT_PARENTHESES);
                } else  {
                    expNode = new VariableNode(name);
                }
                break;
            case LEFT_PARENTHESES:
                match(TokenType.LEFT_PARENTHESES);
                expNode = expression();
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
     * @return opNode an OperationNode specifically for mulops
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
     * @return opNode an OperationNode specifically for addops
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
     * @return opNode an OperationNode specifically for relops
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
     * @return sigNode a SignNode used to denote the sign of a value
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
