package syntaxtree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import parser.Parser;
import scanner.TokenType;

/**
 * @author Maxwell Herron
 * SyntaxTreeTest.java
 * Test class dedicated to running tests on the syntax tree nodes.
 */
public class SyntaxTreeTest {

    @Test
    public void indentedToStringTest() {
        ProgramNode actual = new ProgramNode();
        actual.setFunctions(new FunctionsNode());
        CompoundStatementNode main = new CompoundStatementNode();

        // Setting up the base declaration nodes
        DeclarationsNode declarations = new DeclarationsNode();
        declarations.addVariable(new VariableNode("first"));
        declarations.addVariable(new VariableNode("second"));
        declarations.addVariable(new VariableNode("third"));
        main.setVariables(declarations);

        // Setting up the first assignment statement
        AssignmentStatementNode first = new AssignmentStatementNode();
        first.setLvalue(new VariableNode("first"));
        first.setExpression(new ValueNode("5000"));

        // Setting up the second assignment statement
        AssignmentStatementNode second = new AssignmentStatementNode();
        second.setLvalue(new VariableNode("second"));
        OperationNode secondOperation = new OperationNode(TokenType.PLUS);
        secondOperation.setLeft(new VariableNode("first"));
        secondOperation.setRight(new ValueNode("58"));
        second.setExpression(secondOperation);

        // Setting up the third assignment statement
        AssignmentStatementNode third = new AssignmentStatementNode();
        third.setLvalue(new VariableNode("third"));
        OperationNode thirdOperation = new OperationNode(TokenType.MULTIPLY);
        thirdOperation.setLeft(new VariableNode("first"));
        thirdOperation.setRight(new ValueNode("23"));
        third.setExpression(thirdOperation);

        main.addStatement(first);
        main.addStatement(second);
        main.addStatement(third);

        actual.setMain(main);

        System.out.println("Made it here");
        // Getting the actual toString() from the manually built syntax tree
        String actualString = actual.indentedToString( 0);
        System.out.println(actualString);

        String expectedString =

                "Program:\n" +
                        "|-- FunctionDefinitions\n" +
                        "|-- Compound Statement\n" +
                        "|-- --- Declarations\n" +
                        "|-- --- --- Name: first\n" +
                        "|-- --- --- Name: second\n" +
                        "|-- --- --- Name: third\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: first\n" +
                        "|-- --- --- Value: 5000\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: second\n" +
                        "|-- --- --- Operation: PLUS\n" +
                        "|-- --- --- --- Name: first\n" +
                        "|-- --- --- --- Value: 58\n" +
                        "|-- --- Assignment\n" +
                        "|-- --- --- Name: third\n" +
                        "|-- --- --- Operation: MULTIPLY\n" +
                        "|-- --- --- --- Name: first\n" +
                        "|-- --- --- --- Value: 23\n";

        System.out.println(expectedString);
        assertEquals( expectedString, actualString);
    }

}
