package scanner;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ScannerTest.java
 * @author Maxwell Herron
 * This class contains seven unit tests that are used to test my scanner object.
 * Five of these tests are testing a happy path and expect the output to match the expected.
 * Two of them test sad paths, and expect the exception thrown to match the expected exception.
 */

class ScannerTest {

    /**
     * Checks to see if scanner correctly detects a real number.
     */
    @Test
    void nextTokenHappy1()
    {
        Token expected = new Token("-32.4", TokenType.REAL_NUMBER);
        Scanner scan = new Scanner(new StringReader("-32.4    "));
        try {
            Token actual = new Token(scan.nextToken().lexeme, scan.nextToken().type);
            assertEquals(expected, actual);
        } catch (Exception e) { }
    }

    /**
     * Checks to see if scanner correctly detects an identifier.
     */
    @Test
    void nextTokenHappy2()
    {
        Token expected = new Token("myVariable", TokenType.IDENTIFIER);
        Scanner scan = new Scanner(new StringReader("   myVariable    "));
        try {
            Token actual = new Token(scan.nextToken().lexeme, scan.nextToken().type);
            assertEquals(expected, actual);
        } catch (Exception e) { }
    }

    /**
     * Checks to see if scanner correctly detects a comment.
     */
//    @Test
//    void nextTokenHappy3()
//    {
//        Token expected = new Token("/* this is a comment */", TokenType.COMMENT);
//        Scanner scan = new Scanner(new StringReader("/* this is a comment */"));
//        try {
//            Token actual = new Token(scan.nextToken().lexeme, scan.nextToken().type);
//            assertEquals(expected, actual);
//        } catch (Exception e) { }
//    }

    /**
     * Checks to see if scanner correctly detects an integer.
     */
    @Test
    void nextTokenHappy4()
    {
        Token expected = new Token("-2e23", TokenType.NUMBER);
        Scanner scan = new Scanner(new StringReader("-2e23  "));
        try {
            Token actual = new Token(scan.nextToken().lexeme, scan.nextToken().type);
            assertEquals(expected, actual);
        } catch (Exception e) { }
    }

    /**
     * Checks to see if scanner correctly detects the print keyword.
     */
    @Test
    void nextTokenHappy5()
    {
        Token expected = new Token("print", TokenType.PRINT);
        Scanner scan = new Scanner(new StringReader("\n print"));
        try {
            Token actual = new Token(scan.nextToken().lexeme, scan.nextToken().type);
            assertEquals(expected, actual);
        } catch (Exception e) { }
    }

    /**
     * Checks to see if scanner correctly detects and throws a bad character exception.
     */
    @Test
    void nextTokenSad1()
    {
        BadCharacterException expected = new BadCharacterException("Illegal char: '@' found.");
        Scanner scan = new Scanner(new StringReader("  @"));
        try {
            scan.nextToken();
        } catch (Exception actual) {
            assertEquals(expected, actual);
        }
    }

    /**
     * Checks to see if scanner correctly detects and throws a bad character exception.
     */
    @Test
    void nextTokenSad2()
    {
        BadCharacterException expected = new BadCharacterException("Illegal char: '@' found.");
        Scanner scan = new Scanner(new StringReader("@"));
        Token result = null;
        try {
            result = scan.nextToken();
        } catch (Exception actual) {
            assertEquals(expected, actual);
        }
        while (result != null) {
            try {
                result = scan.nextToken();
                fail();
            } catch (Exception actual) {
                assertEquals(expected, actual);
            }
        }

    }
}