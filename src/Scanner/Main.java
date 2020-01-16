package Scanner;

import java.io.StringReader;

/**
* @author Maxwell Herron
 * This main class is currently used to test the scanner on string inputs.
*/

public class Main 
{
	public static void main(String[] args)
	{
		String testString = "32.4 @@ %yf : 	-5.2e23 17E+1223 myVar123 Myvar123 -212.23 char float int";
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
