package scanner;

import java.io.StringReader;

/**
* @author Maxwell Herron
 * This main class is currently used to test the scanner on string inputs.
*/

public class Main 
{
	public static void main(String[] args)
	{
		String testString = "/* here is comment */ 5.78 @@ %yf 8.5E10 : 17E+1223 myVar123 Myvar123 -100.094 char float int  ";
		//String testString = "/* this should be a comment */\n //As should this";
		Scanner scan = new Scanner(new StringReader(testString));
		Token result = null;
		try {
			result = scan.nextToken();
		} catch (Exception e) {}
		while(result != null) 
		{
			try {
			result = scan.nextToken();
		} catch (Exception e) {}
		}
		System.out.println("Done!");
	}
}
