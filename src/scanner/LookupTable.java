package scanner;

import java.util.HashMap;

/**
 * LookupTable.java
 * @author Maxwell Herron
 * This class uses a Hashmap to act as a lookup table to instantly assign a token type to each inputted lexeme.
 */
public class LookupTable extends HashMap<String, TokenType> {

    public LookupTable() {

        this.put("-", TokenType.MINUS);
        this.put("+", TokenType.PLUS);
        this.put("/", TokenType.DIVIDE);
        this.put("*", TokenType.MULTIPLY);
        this.put("%", TokenType.MODULO);
        this.put(";", TokenType.SEMICOLON);
        this.put("(", TokenType.LEFT_PARENTHESES);
        this.put(")", TokenType.RIGHT_PARENTHESES);
        this.put("[", TokenType.LEFT_BRACKET);
        this.put("]", TokenType.RIGHT_BRACKET);
        this.put("{", TokenType.LEFT_CURLY);
        this.put("}", TokenType.RIGHT_CURLY);
        this.put("==", TokenType.EQUAL);
        this.put("=", TokenType.ASSIGNMENT);
        this.put("<", TokenType.LESS_THAN);
        this.put(">", TokenType.GREATER_THAN);
        this.put("<=", TokenType.LESS_THAN_EQUAL);
        this.put(">=", TokenType.GREATER_THAN_EQUAL);
        this.put("!=", TokenType.NOT_EQUAL);
        this.put("&&", TokenType.AND);
        this.put("||", TokenType.OR);
        this.put("!", TokenType.NOT);
        this.put(",", TokenType.COMMA);
        this.put("char", TokenType.CHAR);
        this.put("int", TokenType.INT);
        this.put("float", TokenType.FLOAT);
        this.put("void", TokenType.VOID);
        this.put("main", TokenType.MAIN);
        this.put("if", TokenType.IF);
        this.put("else", TokenType.ELSE);
        this.put("while", TokenType.WHILE);
        this.put("read", TokenType.READ);
        this.put("write", TokenType.WRITE);
        this.put("return", TokenType.RETURN);
        this.put("func", TokenType.FUNC);
        this.put("program", TokenType.PROGRAM);
        this.put("end", TokenType.END);
    }
}
