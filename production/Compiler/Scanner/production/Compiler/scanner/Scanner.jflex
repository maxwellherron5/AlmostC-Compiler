
/**
* @author Maxwell Herron
* The purpose of this program is to generate a java scanner that will be able to 
* distinguish between words and numbers.
*/

/* Declarations */

%%

%class MyScanner
%function nextToken /* Renames the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}

%{
    // Instantiating a LookupTable object
    LookupTable lookupTable = new LookupTable();
%}

/* Patterns */

other         = .
letter        = [A-Za-z]
whitespace    = [ \n\t]+
number 	      = [\-]?[1-9]\d*|0|[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?
real_number   = [/-]?{number}\.([0-9]*)
operator	  = [\+\-\*/]
symbol        = ":" | ";" | "(" | ")" | "[" | "]" | "{" | "}" | "<" | ">" | "<=" | ">=" | "!=" | "&&" | "||" | "!"
// Will have to get this working someday
//comment       = (([?<=\/\/])[^\\/\/]]+[\n\$])|(([?<=\/\*])[\/\*]([^*\/]|[\r\n]|(\*+([^*\/]|[\r\n])))*[\*+\/]|[\*+\/])
//comment       = (([?<=\/\/])[^\/\/]]+[\n\$])|(([?<=\/\*])[\/\*][.*][\*\/\$])
keyword       = "char" | "int" | "float" | "if" | "else" | "while" | "print" | "read" | "return" | "func" | "program" | "end"
identifier    = {letter}+[0-9]?+

%%
/* Lexical Rules */

{number}	{
			 //System.out.println("Found a number: " + yytext());
			 Token t = new Token();
			 t.lexeme = yytext();
			 t.type = TokenType.NUMBER;
			 System.out.println(t.toString());
			 return t;
			}
            
{whitespace}  {  //System.out.println("Found whitespace!"); 

              }
              
{operator}   {
				Token t = new Token();
				t.lexeme = yytext();
				t.type = lookupTable.get(t.lexeme);
				System.out.println(t.toString());
				return t;
			 }

{real_number}  {
                    Token t = new Token();
                    t.lexeme = yytext();
                    t.type = TokenType.NUMBER;
                    System.out.println(t.toString());
                    return t;
               }

{keyword}  {
                Token t = new Token();
                t.lexeme = yytext();
                t.type = TokenType.KEYWORD;
                System.out.println(t.toString());
                return t;
           }

{symbol}   {
                Token t = new Token();
                t.lexeme = yytext();
                t.type = lookupTable.get(t.lexeme);
                System.out.println(t.toString());
                return t;
           }

{identifier}       {
                        Token t = new Token();
                        t.lexeme = yytext();
                        t.type = TokenType.IDENTIFIER;
                        System.out.println(t.toString());
                        return t;
                   }

//{comment}   {
  //              Token t = new Token();
    //            t.lexeme = yytext();
      //          t.type = TokenType.COMMENT;
        //        System.out.println(t.toString());
          //      return t;
            //}

{other}    { 
             System.out.println("Illegal char: '" + yytext() + "' found.");

           }
