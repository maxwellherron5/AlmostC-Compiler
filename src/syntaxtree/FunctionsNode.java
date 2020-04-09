
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of function definitions.
 * @author Erik Steinmetz
 */
public class FunctionsNode extends SyntaxTreeNode {
    
    private ArrayList<FunctionNode> definitions = new ArrayList<FunctionNode>();
    
    public void addFunctionDefinition( FunctionNode aFunction) {
        definitions.add( aFunction);
    }

    // Getter
    public ArrayList<FunctionNode> getDefinitions() { return this.definitions; }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "FunctionDefinitions\n";
        for( FunctionNode functionDef : definitions) {
            answer += functionDef.indentedToString( level + 1);
        }
        return answer;
    }
    
}
