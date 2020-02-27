package parser;

import java.util.HashMap;

/**
 * SymbolTable.java
 * @author Maxwell Herron
 *
 */
public class SymbolTable {

    private HashMap<String, Data> map;

    /**
     * Default constructor
     */
    public SymbolTable() {
        map = new HashMap<>();
    }

    public void add(String name, Data inData) {
        map.put(name, inData);
    }

    /**
     *
     * @return true if input is a variable name
     */
    public boolean isVariableName(String name) {
        boolean answer = false;
        Data check = map.get(name);
        if (check.kind == IdentifierKind.VARIABLE) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @return true if input is a function name
     */
    public boolean isFunctionName(String name) {
        boolean answer = false;
        Data check = map.get(name);
        if (check.kind == IdentifierKind.FUNCTION) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isProgramName(String name) {
        boolean answer = false;
        Data check = map.get(name);
        if (check.kind == IdentifierKind.PROGRAM) {
            answer = true;
        }
        return answer;
    }

    /**
     *
     * @return
     */
    public boolean isArrayName(String name) {
        boolean answer = false;
        Data check = map.get(name);
        if (check.kind == IdentifierKind.ARRAY) {
            answer = true;
        }
        return answer;
    }

    /**
     * This enum holds the kind of identifier
     */
    private enum IdentifierKind {
        FUNCTION,
        VARIABLE,
        PROGRAM,
        ARRAY
    }

    /**
     * This enum holds the datatype of the identifier
     */
    private enum DataType {
        VOID,
        INT,
        FLOAT
    }

    /**
     * This internal class encapsulates the identifier name and datatype
     */
    private class Data {
        String id;
        IdentifierKind kind;
        DataType type;

        Data(String inID, IdentifierKind inKind, DataType inType) {
            this.id = inID;
            this.kind = inKind;
            this.type = inType;
        }
    }
}



