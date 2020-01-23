package scanner;

/**
 * TokenType.java
 * @author Maxwell Herron
 * This enum holds all the types that a token can be.
 */

public enum TokenType
{
	NUMBER,
	REAL_NUMBER,
	PLUS,
	MINUS,
	MULTIPLY,
	DIVIDE,
	KEYWORD,
	IDENTIFIER,
	SEMICOLON,
	LEFT_PARENTHESES,
	RIGHT_PARENTHESES,
	RIGHT_BRACKET,
	LEFT_BRACKET,
	LEFT_CURLY,
	RIGHT_CURLY,
	LESS_THAN,
	GREATER_THAN,
	LESS_THAN_EQUAL,
	GREATER_THAN_EQUAL,
	NOT_EQUAL,
	AND,
	OR,
	NOT,
	COMMENT,
}