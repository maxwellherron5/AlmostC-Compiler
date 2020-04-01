package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import syntaxtree.ExpressionNode;
import syntaxtree.StatementNode;

import java.util.ArrayList;

/**
 *
 * @author Maxwell Herron
 */
class ParserTest {

    @Test
    void testOperator() {

    }

    /**
     * Asserts that the indentedToString() produced from a factor generated
     * ExpressionNode is as expected.
     */
    @Test
    void factorTest() {
        Parser p = new Parser("!myVar", false);
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
        Parser p = new Parser("23 + myVariable", false);
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
}