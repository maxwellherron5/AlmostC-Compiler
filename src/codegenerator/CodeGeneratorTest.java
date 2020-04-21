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
                "        int myFloat;\n" +
                "        myFloat = 1;\n" +
                "        dollars = 0;\n" +
                "        yen = 1 + 20;\n" +
                "        if (yen > 8) {\n" +
                "        yen = yen - 1;   " +
                "        dollars = dollars + 1;\n" +
                "        } else { yen = 2 + 2;};\n" +
                "        write(2 + 2);\n" +
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
    public void writeCodeForProgramTestOne() {

        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        int myFloat;\n" +
                "        myFloat = 1;\n" +
                "        dollars = 0;\n" +
                "        yen = 1 + 20;\n" +
                "        if (yen > 8) {\n" +
                "        yen = yen - 1;   " +
                "        dollars = dollars + 1;\n" +
                "        } else { yen = 2 + 2;};\n" +
                "        write(2 + 2);\n" +
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