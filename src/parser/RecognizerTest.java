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
    void functionDefinition() {

    }

    @Test
    void statement() {

    }

    @Test
    void simpleExpression() {

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