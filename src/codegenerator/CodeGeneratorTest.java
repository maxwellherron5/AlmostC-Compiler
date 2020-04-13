package codegenerator;

import org.junit.jupiter.api.Test;
import parser.Parser;
import semanticanalyzer.SemanticAnalyzer;
import symboltable.SymbolTable;
import syntaxtree.ProgramNode;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    public void writeCodeForProgramTest() {

        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        float myFloat;\n" +
                "        myFloat = 22.347;\n" +
                "        dollars = 150 + myFloat;\n" +
                "        myvar = dollars * 104;\n" +
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
        System.out.println(s.getCanWriteAssembly());
    }
}