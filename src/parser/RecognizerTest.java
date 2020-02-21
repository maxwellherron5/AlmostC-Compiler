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
    void testDeclarationsHappy() {
        Recognizer r = new Recognizer("void firstFunc123;\nint secondFunc123;\nfloat thirdFunc123;", false);
        try {
            r.declarations();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void testFunctionDefinition() {
        Recognizer r = new Recognizer("", false);
        try {

        } catch (Exception e) {

        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void testStatement() {
        Recognizer r = new Recognizer("", false);
        try {

        } catch (Exception e) {

        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void testSimpleExpression() {
        Recognizer r = new Recognizer("", false);
        try {

        } catch (Exception e) {

        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void testFactorHappy() {
        Recognizer r = new Recognizer("myVariable123", false);
        try {
            r.factor();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }
}