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
        Recognizer r = new Recognizer("main(@){}", false);
        try {
            r.program();
        } catch (Exception e) {
            fail();
        }
        assertNotNull(r.getLookahead().getType());
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
    void testDeclarationsSad() {

    }

    @Test
    void testFunctionDefinition() {

    }

    @Test
    void testStatement() {

    }

    @Test
    void testSimpleExpression() {

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

    @Test
    void testFactorSad() {
        Recognizer r = new Recognizer("myVariable123", false);
        try {
            r.factor();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }
}