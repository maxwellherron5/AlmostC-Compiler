package parser;

import org.junit.jupiter.api.Test;
import scanner.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * RecognizerTest.java
 * @author Maxwell Herron
 */
class RecognizerTest {

    @Test
    void testProgramHappy() {
        Recognizer r = new Recognizer("main(){}", false);
        try {
            r.program();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void testProgramSad() {

    }

    @Test
    void declarations() {

    }

    @Test
    void type() {


    }

    @Test
    void functionDeclarations() {

    }

    @Test
    void functionDeclaration() {

    }

    @Test
    void functionDefinitions() {

    }

    @Test
    void functionDefinition() {

    }

    @Test
    void parameters() {

    }

    @Test
    void parameterList() {

    }

    @Test
    void compoundStatement() {

    }

    @Test
    void optionalStatements() {

    }

    @Test
    void statementList() {

    }

    @Test
    void statement() {

    }

    @Test
    void variable() {

    }

    @Test
    void expressionList() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void testExpressionHappy() {
        Recognizer r = new Recognizer("x >= y", false);
        try {
            r.expression();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void simpleExpression() {

    }

    @Test
    void simplePart() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void term() {
        Recognizer r = new Recognizer("myVariable321 * myVariable123", false);
        try {
            r.term();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void termPart() {
        Recognizer r = new Recognizer("* myVariable123", false);
        try {
            r.termPart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void factor() {
        Recognizer r = new Recognizer("myVariable123", false);
        try {
            r.factor();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }
}