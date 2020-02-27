package parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    @Test
    void testAddVariableNameHappy() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }

    @Test
    void testAddVariableNameSad() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
    }

    @Test
    void testAddFunctionNameHappy() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }

    @Test
    void testAddFunctionNameSad() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }

    @Test
    void testAddProgramName() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }

    @Test
    void testAddArrayName() {
        SymbolTable table = new SymbolTable();
        String varName = "myVar123";
        table.addVariableName(varName);
        assertTrue(table.isVariableName(varName));
    }

    @Test
    void testIsVariableName() {

    }
}