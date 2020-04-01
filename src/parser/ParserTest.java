package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import syntaxtree.*;

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
        Parser p = new Parser("(2 + 2)", false);
        ExpressionNode e = p.factor();
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
        Parser p = new Parser("int sum(int foo, int bar) {\n" +
                "return foo + bar;\n" +
                "}", false);
        FunctionNode f = p.functionDefinition();
        System.out.println(f.indentedToString(0));
    }

    /**
     *
     */
    @Test
    void declarationsTest() {
        Parser p = new Parser("int x, y, z;", false);
        DeclarationsNode s = p.declarations();
        System.out.println(s.indentedToString(0));
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