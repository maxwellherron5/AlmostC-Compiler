package parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SymbolTableTest.java
 * @author Maxwell Herron
 * Runs tests on several of the main methods found in SymbolTable.java
 */
class SymbolTableTest {

    /**
     * Attempts to add a variable name to the symbol table, then verifies that
     * the name has been added to the table.
     */
    @Test
    void testAddVariableNameHappy() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }

    /**
     *
     */
//    @Test
//    void testAddVariableNameSad() {
//        SymbolTable table = new SymbolTable();
//        String varName = "myVar123";
//        table.addVariableName(varName);
//    }

    /**
     * Attempts to add a function name to the symbol table, then verifies that
     * the name has been added to the table.
     */
    @Test
    void testAddFunctionNameHappy() {
        SymbolTable table = new SymbolTable();
        String funcName = "myFunc123";
        table.addFunctionName(funcName);
        assertTrue(table.isFunctionName(funcName));
    }

    /**
     *
     */
//    @Test
//    void testAddFunctionNameSad() {
//        SymbolTable table = new SymbolTable();
//        String varName = "myVar123";
//        table.addVariableName(varName);
//        assertTrue(table.isVariableName(varName));
//    }

    /**
     * Attempts to add a program name to the symbol table, then verifies that
     * the name has been added to the table.
     */
    @Test
    void testAddProgramNameHappy() {
        SymbolTable table = new SymbolTable();
        String progName = "myProg123";
        table.addProgramName(progName);
        assertTrue(table.isProgramName(progName));
    }

    /**
     * Attempts to add an array name to the symbol table, then verifies that
     * the name has been added to the table.
     */
    @Test
    void testAddArrayNameHappy() {
        SymbolTable table = new SymbolTable();
        String arrName = "myArr123";
        table.addArrayName(arrName);
        assertTrue(table.isArrayName(arrName));
    }

    /**
     * Verifies correctness of isVariableName by inserting a variable name into the table,
     * and then calling the function on that and asserting a return of true.
     */
    @Test
    void testIsVariableNameHappy() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }
}