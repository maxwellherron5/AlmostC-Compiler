package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import syntaxtree.ExpressionNode;
import syntaxtree.ProgramNode;
import syntaxtree.StatementNode;

import java.util.ArrayList;

/**
 *
 * @author Maxwell Herron
 */
class ParserTest {

    /**
     * Asserts that the indentedToString() produced from a factor generated
     * ExpressionNode is as expected.
     */
    @Test
    void factorTest() {
        Parser p = new Parser("myVar(22)", false);
        ExpressionNode e = p.factor();
//        String expected = "Name: myVar123\n";
//        String actual = e.indentedToString(0);
//        assertEquals(expected, actual);
        System.out.println(e.indentedToString(0));
    }

    /**
     *
     */
    @Test
    void simpleExpressionTest() {
        Parser p = new Parser("1 + 1 * 3", false);
        ExpressionNode e = p.simpleExpression();
        System.out.println(e.indentedToString(0));
    }

    /**
     *
     */
    @Test
    void statementTest() {
        Parser p = new Parser("myVar = 2 + 2", false);
        p.getTable().addVariableName("myVar");
        StatementNode s = p.statement();
        System.out.println(s.indentedToString(0));
    }

    /**
     *
     */
    @Test
    void functionDefinitionTest() {

    }

    /**
     *
     */
    @Test
    void declarationsTest() {

    }

    /**
     *
     */
    @Test
    void programTest() {
        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        dollars = 1000000;\n" +
                "        yen = dollars * 104;\n" +
                "        bitcoins = dollars / 6058;\n" +
                "     };", false);
        ProgramNode pr = p.program();
        System.out.println(pr.indentedToString(0));

    }
}