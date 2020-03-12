
package syntaxtree;

/**
 * Represents an Almost C Program
 * @author Erik Steinmetz
 */
public class ProgramNode extends SyntaxTreeNode {
    
    private FunctionsNode functions;
    private CompoundStatementNode main;
    

    public FunctionsNode getFunctions() {
        return functions;
    }

    public void setFunctions(FunctionsNode functions) {
        this.functions = functions;
    }

    public CompoundStatementNode getMain() {
        return main;
    }

    public void setMain(CompoundStatementNode main) {
        this.main = main;
    }
    
    /**
     * Creates a String representation of this program node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Program:\n";
        answer += functions.indentedToString( level + 1);
        answer += main.indentedToString( level + 1);
        return answer;
    }
}
