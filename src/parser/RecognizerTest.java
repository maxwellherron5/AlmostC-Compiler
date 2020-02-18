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
    }

    @Test
    void identifierList() {
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
    }

    @Test
    void expression() {
    }

    @Test
    void simpleExpression() {
    }

    @Test
    void simplePart() {
    }

    @Test
    void term() {
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
    void exp() {

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