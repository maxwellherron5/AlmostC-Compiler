package codegenerator;

import org.junit.jupiter.api.Test;
import parser.Parser;
import scanner.TokenType;
import semanticanalyzer.SemanticAnalyzer;
import symboltable.SymbolTable;
import syntaxtree.OperationNode;
import syntaxtree.ProgramNode;
import syntaxtree.ValueNode;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    public void writeCodeForProgramTest() {

        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        float myFloat;\n" +
                "        myFloat = 1;\n" +
                "        dollars = 12 + myFloat;\n" +
                "        yen = dollars * 3;\n" +
                "        if (myVar > 5) {\n" +
                "        yen = myVar;   " +
                "        } else { yen = dollars + 22; };\n" +
                "     };", false);
        ProgramNode progNode = p.program();
        SymbolTable st = p.getTable();
        SemanticAnalyzer s = new SemanticAnalyzer(progNode, st);
        s.assignDatatypes();
        s.checkAssignmentTypes();
        CodeGenerator c = new CodeGenerator();
        System.out.println(c.writeCodeForRoot(progNode));
    }

    @Test
    public void writeCodeForOperationTest() {

        ValueNode lVal = new ValueNode("5");
        ValueNode rVal = new ValueNode("55");
        OperationNode opNode = new OperationNode(TokenType.MODULO, lVal, rVal);

        CodeGenerator c = new CodeGenerator();
        System.out.println(c.writeCode(opNode, "$s0"));
    }
}