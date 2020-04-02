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
        String expected = "Operation: PLUS\n" +
                "|-- Value: 2\n" +
                "|-- Value: 2\n";
        ExpressionNode e = p.factor();
        String actual = e.indentedToString(0);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    void simpleExpressionTest() {
        Parser p = new Parser("1 + 1 * 3", false);
        String expected = "Operation: PLUS\n" +
                "|-- Value: 1\n" +
                "|-- Operation: MULTIPLY\n" +
                "|-- --- Value: 1\n" +
                "|-- --- Value: 3\n";
        ExpressionNode e = p.simpleExpression();
        String actual = e.indentedToString(0);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    void firstStatementTest() {
        Parser p = new Parser("myVar = 2 + 2", false);
        p.getTable().addVariableName("myVar");
        String expected = "Assignment\n" +
                "|-- Name: myVar\n" +
                "|-- Operation: PLUS\n" +
                "|-- --- Value: 2\n" +
                "|-- --- Value: 2\n";
        StatementNode s = p.statement();
        String actual = s.indentedToString(0);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    void secondStatementTest() {
        Parser p = new Parser("return 2 + 2;", false);
        String expected = "Return\n" +
                "|-- Operation: PLUS\n" +
                "|-- --- Value: 2\n" +
                "|-- --- Value: 2\n";
        StatementNode s = p.statement();
        String actual = s.indentedToString(0);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    void thirdStatementTest() {
        Parser p = new Parser("{\n" +
                "     int a;\n" +
                "     float b;\n" +
                "     int c;\n" +
                "     float d;\n" +
                "     int e;\n" +
                "     float f;\n" +
                "     float g, h, i;\n" +
                "     a = 1 + 3;\n" +
                "     b = a;\n" +
                " }", false);
        String expected = "Compound Statement\n" +
                "|-- Declarations\n" +
                "|-- --- Name: a\n" +
                "|-- --- Name: b\n" +
                "|-- --- Name: c\n" +
                "|-- --- Name: d\n" +
                "|-- --- Name: e\n" +
                "|-- --- Name: f\n" +
                "|-- --- Name: g\n" +
                "|-- --- Name: h\n" +
                "|-- --- Name: i\n" +
                "|-- Assignment\n" +
                "|-- --- Name: a\n" +
                "|-- --- Operation: PLUS\n" +
                "|-- --- --- Value: 1\n" +
                "|-- --- --- Value: 3\n" +
                "|-- Assignment\n" +
                "|-- --- Name: b\n" +
                "|-- --- Name: a\n";
        StatementNode s = p.statement();
        String actual = s.indentedToString(0);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    void functionDefinitionTest() {
        Parser p = new Parser("int sum(int foo, int bar) {\n" +
                "return foo + bar;\n" +
                "}", false);
        String expected = "Function: sum returns null\n" +
                "|-- Compound Statement\n" +
                "|-- --- Declarations\n" +
                "|-- --- Return\n" +
                "|-- --- --- Operation: PLUS\n" +
                "|-- --- --- --- Name: foo\n" +
                "|-- --- --- --- Name: bar\n";
        FunctionNode f = p.functionDefinition();
        String actual = f.indentedToString(0);
        assertEquals(expected, actual);
    }

    /**
     *
     */
    @Test
    void declarationsTest() {
        Parser p = new Parser("int x, y, z;", false);
        String expected = "Declarations\n" +
                "|-- Name: x\n" +
                "|-- Name: y\n" +
                "|-- Name: z\n";
        DeclarationsNode s = p.declarations();
        String actual = s.indentedToString(0);
        assertEquals(expected, actual);
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
        String expected = "Program:\n" +
                "|-- FunctionDefinitions\n" +
                "|-- Compound Statement\n" +
                "|-- --- Declarations\n" +
                "|-- --- --- Name: dollars\n" +
                "|-- --- --- Name: yen\n" +
                "|-- --- --- Name: bitcoins\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: dollars\n" +
                "|-- --- --- Value: 1000000\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: yen\n" +
                "|-- --- --- Operation: MULTIPLY\n" +
                "|-- --- --- --- Name: dollars\n" +
                "|-- --- --- --- Value: 104\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: bitcoins\n" +
                "|-- --- --- Operation: DIVIDE\n" +
                "|-- --- --- --- Name: dollars\n" +
                "|-- --- --- --- Value: 6058\n";
        ProgramNode pr = p.program();
        String actual = pr.indentedToString(0);
        assertEquals(expected, actual);
    }
}