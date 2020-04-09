package symboltable;

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

    /**
     * Adds a new variable name to the Symbol Table. If that name already exists, it will
     * throw a runtime exception.
     * @param name the name of the variable.
     */
    public void addVariableName(String name) {
        if (!exists(name)) {
            map.put(name, new Data(name, IdentifierKind.VARIABLE));
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    /**
     * Adds a new function name to the Symbol Table. If that name already exists, it will
     * throw a runtime exception.
     * @param name the name of the function.
     */
    public void addFunctionName(String name) {
        if (!exists(name)) {
            map.put(name, new Data(name, IdentifierKind.FUNCTION));
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    /**
     * Adds a new program name to the Symbol Table. If that name already exists, it will
     * throw a runtime exception.
     * @param name the name of the program.
     */
    public void addProgramName(String name) {
        if (!exists(name)) {
            map.put(name, new Data(name, IdentifierKind.PROGRAM));
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    /**
     * Adds a new array name to the Symbol Table. If that name already exists, it will
     * throw a runtime exception.
     * @param name the name of the array.
     */
    public void addArrayName(String name) {
        if (!exists(name)) {
            map.put(name, new Data(name, IdentifierKind.ARRAY));
        } else {
            throw new RuntimeException("ERROR: " + name + " already exists in the table.");
        }
    }

    /**
     * Checks whether or not the value associated with the input string is a variable kind.
     * @param name the key used to access the value.
     * @return true if input is a variable name.
     */
    public boolean isVariableName(String name) {
        boolean answer = false;
        if (map.get(name).getKind() == IdentifierKind.VARIABLE) {
            answer = true;
        }
        return answer;
    }

    /**
     * Checks whether or not the value associated with the input string is a function kind.
     * @param name the key used to access the value.
     * @return true if input is a function name.
     */
    public boolean isFunctionName(String name) {
        boolean answer = false;
        if (map.get(name).getKind() == IdentifierKind.FUNCTION) {
            answer = true;
        }
        return answer;
    }

    /**
     * Checks whether or not the value associated with the input string is a program kind.
     * @param name the key used to access the value.
     * @return true if input is a program name.
     */
    public boolean isProgramName(String name) {
        boolean answer = false;
        if (map.get(name).getKind() == IdentifierKind.PROGRAM) {
            answer = true;
        }
        return answer;
    }

    /**
     * Checks whether or not the value associated with the input string is an array kind.
     * @param name the key used to access the value.
     * @return true if input is an array name.
     */
    public boolean isArrayName(String name) {
        boolean answer = false;
        if (map.get(name).getKind() == IdentifierKind.ARRAY) {
            answer = true;
        }
        return answer;
    }

    /**
     * Checks to see if entry given by input key exists.
     * @param name the entry that you are checking.
     * @return true if the given entry exists in the Symbol Table.
     */
    public boolean exists(String name) {
        boolean answer = false;
        if (map.get(name) != null) {
            answer = true;
        }
        return answer;
    }

    /**
     * Sets the datatype of the specified Identifier in the symbol table
     * @param name
     * @param type
     */
    public void setType(String name, DataType type) {
        map.get(name).setType(type);
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
     * This enum holds the datatype
     */
    public enum DataType {
        VOID, INT, FLOAT,
    }

    /**
     * This internal class encapsulates the identifier name and datatype
     */
    public class Data {
        private String id;
        private IdentifierKind kind;
        private DataType type;

        public Data(String inID, IdentifierKind inKind) {
            this.id = inID;
            this.kind = inKind;
            this.type = null;
        }

        // Getters
        public IdentifierKind getKind() { return this.kind; }
        public DataType getType() { return this.type; }

        // Setters
        public void setKind(IdentifierKind kind) { this.kind = kind; }
        public void setType(DataType type) { this.type = type; }
    }

    /**
     * Extension of the standard HashMap get() method used to get the kind value associated with
     * the string key.
     * @param key the identifier name
     * @return the kind value associated with the key input
     */
    public Data get(String key) {
        return map.get(key);
    }

    /**
     * A nicely formatted table printing out the keys/values within the symbol table.
     * @return the stringified table.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        // This format will maintain an even spacing for each line
        String format = "|%1$-10s|%2$-10s|\n";
        // Column names
        output.append(String.format(format, "Name", "Kind"));
        output.append(String.format(format, "----------", "----------"));
        // Iterating through each the table to grab/print the key and value
        map.entrySet().forEach(entry->{
            output.append(String.format(format, entry.getKey(), entry.getValue()));
            output.append(String.format(format, "----------", "----------"));
        });
        return output.toString();
    }
}



