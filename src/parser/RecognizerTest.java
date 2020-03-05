package parser;

import org.junit.jupiter.api.Test;
import scanner.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * RecognizerTest.java
 * @author Maxwell Herron
 * This class contains six happy path tests for the following productions: Program, Declarations,
 * FunctionDefinition, Statement, SimpleExpression, and Factor.
 * NOTE: MODIFY SOME TESTS SO THAT YOU MANUALLY ADD ID'S TO SYMBOL TABLE BEFORE TEST
 */
class RecognizerTest {

    /**
     * Tests the Program production. Verifies correctness by checking if the lookahead is null.
     * The lookahead will only be null if it has marched through the entire input.
     */
    @Test
    void testProgram() {
        Recognizer r = new Recognizer("main(){}", false);
        r.program();
        assertNull(r.getLookahead().getType());
    }

    /**
     * Tests the Declarations production. Verifies correctness by checking if the lookahead is null.
     * The lookahead will only be null if it has marched through the entire input.
     */
    @Test
    void testDeclarations() {
        Recognizer r = new Recognizer("void firstFunc123;\nint secondFunc123;\nfloat thirdFunc123;", false);
        r.declarations();
        assertNull(r.getLookahead().getType());
    }

    /**
     * Tests the FunctionDefinition production. Verifies correctness by checking if the lookahead is null.
     * The lookahead will only be null if it has marched through the entire input.
     */
    @Test
    void testFunctionDefinition() {
        Recognizer r = new Recognizer("int myFunction(int varOne , float varTwo) { int x; x = 2 + 2 } ", false);
        r.functionDefinition();
        assertNull(r.getLookahead().getType());
    }

    /**
     * Tests the Statement production. Verifies correctness by checking if the lookahead is null.
     * The lookahead will only be null if it has marched through the entire input.
     */
    @Test
    void testStatement() {
        Recognizer r = new Recognizer("myVar = 2 + 2", false);
        r.statement();
        System.out.println("HERE: " + r.getTable().get("myVar"));
        assertNull(r.getLookahead().getType());
    }

    /**
     * Tests the Expression production. Verifies correctness by checking if the lookahead is null.
     * The lookahead will only be null if it has marched through the entire input.
     */
    @Test
    void testSimpleExpression() {
        Recognizer r = new Recognizer("23 + myVariable", false);
        r.simpleExpression();
        assertNull(r.getLookahead().getType());
    }

    /**
     * Tests the Factor production. Verifies correctness by checking if the lookahead is null.
     * The lookahead will only be null if it has marched through the entire input.
     */
    @Test
    void testFactor() {
        Recognizer r = new Recognizer("myVariable123", false);
        r.factor();
        assertNull(r.getLookahead().getType());
    }

    /**
     * Tests to see if the ParserException is correctly thrown when an illegal string
     * is parsed...or 'recognized'.
     */
    @Test
    void parserExceptionTest() {
        Recognizer r = new Recognizer("main({", false);
        try {
            r.program();
            fail();
        } catch (ParserException e) { }
    }
}