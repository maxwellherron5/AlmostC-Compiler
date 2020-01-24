package scanner;

import java.io.IOException;
import java.io.StringReader;

/**
* @author Maxwell Herron
 * This main class is currently used to test the scanner on string inputs.
*/

public class Main 
{
	public static void main(String[] args)
	{
		//String testString = "/* here is comment */ 5.78 @@ %yf 8.5E10 : 17E+1223 myVar123 Myvar123 -100.094 char float int  ";
		String testString = "45 identifier myChar print int  ~ ^ __ @ ß";
		Scanner scan = new Scanner(new StringReader(testString));
		Token result = null;
		try {
			result = scan.nextToken();
		} catch (BadCharacterException ex) {
			System.out.println(ex.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(result != null) 
		{
			try {
			result = scan.nextToken();
		} catch (BadCharacterException exp) {
				System.out.println(exp.toString());

			}
			catch (Exception e) {

			}
		}
		System.out.println("Done!");
	}
}
