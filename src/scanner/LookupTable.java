package scanner;

import java.util.HashMap;

/**
 * LookupTable.java
 * @author Maxwell Herron
 * This class uses a Hashmap to act as a lookup table to instantly assign a token type to each inputted lexeme.
 */
public class LookupTable extends HashMap<String, TokenType>
{
    public LookupTable()
    {
        this.put("-", TokenType.MINUS);
        this.put("+", TokenType.PLUS);
        this.put("/", TokenType.DIVIDE);
        this.put("*", TokenType.MULTIPLY);
        this.put(";", TokenType.SEMICOLON);
        this.put("(", TokenType.LEFT_PARENTHESES);
        this.put(")", TokenType.RIGHT_PARENTHESES);
        this.put("[", TokenType.LEFT_BRACKET);
        this.put("]", TokenType.RIGHT_BRACKET);
        this.put("{", TokenType.LEFT_CURLY);
        this.put("}", TokenType.RIGHT_CURLY);
        this.put("<", TokenType.LESS_THAN);
        this.put(">", TokenType.GREATER_THAN);
        this.put("<=", TokenType.LESS_THAN_EQUAL);
        this.put(">=", TokenType.GREATER_THAN_EQUAL);
        this.put("!=", TokenType.NOT_EQUAL);
        this.put("&&", TokenType.AND);
        this.put("||", TokenType.OR);
        this.put("!", TokenType.NOT);
    }
}
