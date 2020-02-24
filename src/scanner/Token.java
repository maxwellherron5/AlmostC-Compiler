package scanner;

import java.util.Objects;

/**
 * Token.java
 * @author Maxwell Herron
 * This is the token object that is returned from the scanner. It has a lexeme, which is the string literal,
 * and it has a tokentype, which represents what type the lexeme is.
 */
public class Token
{
	String lexeme;
	TokenType type;

	/**
	 * Empty constructor
	 */
	public Token() { }

	/**
	 * Constructor with params
	 * @param lexeme
	 * @param type
	 */
	public Token(String lexeme, TokenType type)
	{
		this.lexeme = lexeme;
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Token)) return false;
		Token token = (Token) o;
		return Objects.equals(getLexeme(), token.getLexeme()) &&
		getType() == token.getType();
	}


	public String getLexeme()
	{
		return lexeme;
	}

	public void setLexeme(String lexeme)
	{
		this.lexeme = lexeme;
	}

	public TokenType getType()
	{
		return type;
	}

	public void setType(TokenType type)
	{
		this.type = type;
	}

	@Override
	public String toString() {
		return "Token{" +
				"lexeme='" + lexeme + '\'' +
				", type=" + type +
				'}';
	}
}