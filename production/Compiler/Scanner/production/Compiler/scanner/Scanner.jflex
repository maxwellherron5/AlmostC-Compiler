package scanner;    /* Declares this class to be a part of the scanner package */

/**
* @author Maxwell Herron
* The purpose of this program is to generate a java scanner that will be able to 
* distinguish between words and numbers.
*/

/* Declarations */

%%
%public
%class Scanner
%yylexthrow BadCharacterException
%function nextToken /* Renames the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
%line
%column

%eofval{
  return null;
%eofval}

%{
    // Instantiating a LookupTable object
    LookupTable lookupTable = new LookupTable();

      /**
       * Gets the line number of the most recent lexeme.
       * @return The current line number.
       */
      public int getLine() { return yyline;}

      /**
       * Gets the column number of the most recent lexeme.
       * This is the number of chars since the most recent newline char.
       * @return The current column number.
       */
      public int getColumn() { return yycolumn;}
%}

/* Patterns */

other         = .
letter        = [A-Za-z]
whitespace    = [ \n\t]+
//number 	      = [\-]?[1-9]\d*|0|[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?
number        = [\-]?\d+([eE][-+]?[0-9]+)?
real_number   = [/-]?{number}\.([0-9]*)([eE][-+]?[0-9]+)?
operator	  = [\+\-\*/%]
symbol        = ":" | ";" | "(" | ")" | "[" | "]" | "{" | "}" | "<" | ">" | "<=" | ">=" | "!=" | "&&" | "||" | "!" | "==" | "=" | ","
comment       = (\/\*(\*(!\/)|[^*])*\*\/)|(\/[\/]+.*)
keyword       = "char" | "int" | "float" | "void" | "if" | "else" | "while" | "print" | "read" | "return" | "func" | "program" | "end" | "main"
word          = {letter}+
identifier    = {letter}+[0-9]?+

%%
/* Lexical Rules */

{number}	{
			 //System.out.println("Found a number: " + yytext());
			 Token t = new Token();
			 t.lexeme = yytext();
			 t.type = TokenType.NUMBER;
			 return t;
			}
            
{whitespace}  {  //System.out.println("Found whitespace!"); 

              }
              
{operator}   {
				Token t = new Token();
				t.lexeme = yytext();
				t.type = lookupTable.get(t.lexeme);
				return t;
			 }

{real_number}  {
                    Token t = new Token();
                    t.lexeme = yytext();
                    t.type = TokenType.REAL_NUMBER;
                    return t;
               }

{keyword}  {
                Token t = new Token();
                t.lexeme = yytext();
                t.type = lookupTable.get(t.lexeme);
                return t;
           }

{symbol}   {
                Token t = new Token();
                t.lexeme = yytext();
                t.type = lookupTable.get(t.lexeme);
                return t;
           }

{identifier} {
                        Token t = new Token();
                        t.lexeme = yytext();
                        t.type = TokenType.IDENTIFIER;
                        return t;
            }

{comment}   {

            }

{word}      {

            }

{other}    { 
             String errorMessage = "Illegal char: '" + yytext() + "' found.";
             throw new BadCharacterException(errorMessage);
           }
