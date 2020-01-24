package scanner;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void nextTokenHappy1()
    {
        Token expected = new Token("-32.4", TokenType.REAL_NUMBER);
        Scanner scan = new Scanner(new StringReader("-32.4"));
        try {
            Token actual = new Token(scan.nextToken().lexeme, scan.nextToken().type);
        } catch (Exception e) { }

        assertEquals(expected.equals(actual))
    }

    @Test
    void nextTokenHappy2()
    {

    }

    @Test
    void nextTokenHappy3()
    {

    }

    @Test
    void nextTokenHappy4()
    {

    }

    @Test
    void nextTokenHappy5()
    {

    }

    @Test
    void nextTokenSad1()
    {

    }

    @Test
    void nextTokenSad2()
    {

    }
}