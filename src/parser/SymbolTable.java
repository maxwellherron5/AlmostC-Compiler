package parser;

import java.util.HashMap;

/**
 * SymbolTable.java
 * @author Maxwell Herron
 *
 */
public class SymbolTable {

    private HashMap<String, IdentifierKind> map;

    /**
     * Default constructor
     */
    public SymbolTable() {
        map = new HashMap<>();
    }

    public void addVariableName(String name) {
        if (!exists(name)) {
            map.put(name, IdentifierKind.VARIABLE);
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    public void addFunctionName(String name) {
        if (!exists(name)) {
            map.put(name, IdentifierKind.FUNCTION);
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    public void addProgramName(String name) {
        if (!exists(name)) {
            map.put(name, IdentifierKind.PROGRAM);
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    public void addArrayName(String name) {
        if (!exists(name)) {
            map.put(name, IdentifierKind.ARRAY);
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    /**
     *
     * @return true if input is a variable name
     */
    public boolean isVariableName(String name) {
        boolean answer = false;
        if (map.get(name) == IdentifierKind.VARIABLE) {
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
        if (map.get(name) == IdentifierKind.FUNCTION) {
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
        if (map.get(name) == IdentifierKind.PROGRAM) {
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
        if (map.get(name) == IdentifierKind.ARRAY) {
            answer = true;
        }
        return answer;
    }

    public boolean exists(String name) {
        boolean answer = false;
        if (map.get(name) != null) {
            answer = true;
        }
        return answer;
    }

    public IdentifierKind get(String key) {
        return map.get(key);
    }

    /**
     * This enum holds the kind of identifier
     */
    public enum IdentifierKind {
        FUNCTION,
        VARIABLE,
        PROGRAM,
        ARRAY
    }

    /**
     * This internal class encapsulates the identifier name and datatype
     */
    private class Data {
        String id;
        IdentifierKind kind;

        Data(String inID, IdentifierKind inKind) {
            this.id = inID;
            this.kind = inKind;
        }
    }
}



