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
		String testString = "/* here is comment */  @@ %yf : 17E+1223 myVar123 Myvar123 -100 - + char float int";
		//String testString = "/* this should be a comment */\n //As should this";
		MyScanner scan = new MyScanner(new StringReader(testString));
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
