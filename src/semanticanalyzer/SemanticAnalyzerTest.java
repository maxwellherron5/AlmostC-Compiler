package semanticanalyzer;

import org.junit.jupiter.api.Test;
import parser.Parser;
import symboltable.SymbolTable;
import syntaxtree.AssignmentStatementNode;
import syntaxtree.ProgramNode;
import syntaxtree.StatementNode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs tests on the
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
                "        float myFloat;\n" +
                "        myFloat = 22.347;\n" +
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
        String expected = "Program:\n" +
                "|-- FunctionDefinitions\n" +
                "|-- Compound Statement\n" +
                "|-- --- Declarations\n" +
                "|-- --- --- Name: dollars type: INT\n" +
                "|-- --- --- Name: yen type: INT\n" +
                "|-- --- --- Name: bitcoins type: INT\n" +
                "|-- --- --- Name: myFloat type: FLOAT\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: myFloat type: FLOAT\n" +
                "|-- --- --- Value: 22.347 type: FLOAT\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: dollars type: INT\n" +
                "|-- --- --- Value: 1000000 type: INT\n" +
                "|-- --- Assignment\n" +
                "|-- --- --- Name: myvar type: INT\n" +
                "|-- --- --- Operation: MULTIPLY type: INT\n" +
                "|-- --- --- --- Name: dollars type: INT\n" +
                "|-- --- --- --- Value: 104 type: INT\n" +
                "|-- --- If\n" +
                "|-- --- --- Operation: GREATER_THAN type: INT\n" +
                "|-- --- --- --- Name: myVar type: INT\n" +
                "|-- --- --- --- Value: 5 type: INT\n" +
                "|-- --- --- Compound Statement\n" +
                "|-- --- --- --- Declarations\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: yen type: INT\n" +
                "|-- --- --- --- --- Name: myVar type: INT\n" +
                "|-- --- --- Compound Statement\n" +
                "|-- --- --- --- Declarations\n" +
                "|-- --- --- --- Assignment\n" +
                "|-- --- --- --- --- Name: yen type: INT\n" +
                "|-- --- --- --- --- Operation: PLUS type: INT\n" +
                "|-- --- --- --- --- --- Name: dollars type: INT\n" +
                "|-- --- --- --- --- --- Value: 22 type: INT\n";
        String actual = progNode.indentedToString(0);
        assertEquals(expected, actual);
    }

    @Test
    public void checkAssignmentTypesTest() {
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
        assertEquals(false, s.getCanWriteAssembly());
    }
}