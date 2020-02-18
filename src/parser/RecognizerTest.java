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
    void program() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void identifierList() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void declarations() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void type() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void functionDeclarations() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void functionDeclaration() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void functionDefinitions() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void functionDefinition() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void parameters() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void parameterList() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void compoundStatement() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void optionalStatements() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void statementList() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void statement() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void variable() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
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
    void expression() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }

    @Test
    void simpleExpression() {
        Recognizer r = new Recognizer("+ myVariable123", false);
        try {
            r.simplePart();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
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