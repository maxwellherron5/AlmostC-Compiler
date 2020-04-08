package semanticanalyzer;

import org.junit.jupiter.api.Test;
import parser.Parser;
import symboltable.SymbolTable;
import syntaxtree.AssignmentStatementNode;
import syntaxtree.ProgramNode;
import syntaxtree.StatementNode;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Maxwell Herron
 */
class SemanticAnalyzerTest {

    @Test
    public void checkIdentifiersDeclarationTest() {
        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        dollars = 1000000;\n" +
                "        myvar = dollars * 104;\n" +
                "        if (myVar > 5) {\n" +
                "        yen = myVar;   " +
                "        } else { };\n" +
                "     };", false);
        ProgramNode progNode = p.program();
        SymbolTable st = p.getTable();
        SemanticAnalyzer s = new SemanticAnalyzer(progNode, st);
        s.checkIdentifiersDeclaration();
        assertEquals(false, s.getCanWriteAssembly());
    }

    @Test
    public void assignDataTypesTest() {
        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        dollars = 1000000;\n" +
                "        myvar = dollars * 104;\n" +
                "        if (myVar > 5) {\n" +
                "        yen = myVar;   " +
                "        } else { yen = dollars + 22; };\n" +
                "     };", false);
        ProgramNode progNode = p.program();
        SymbolTable st = p.getTable();
        SemanticAnalyzer s = new SemanticAnalyzer(progNode, st);
        s.assignDatatypes();
        System.out.println(progNode.indentedToString(0));
    }
}