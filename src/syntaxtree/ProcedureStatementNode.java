package syntaxtree;

import symboltable.SymbolTable;

import java.util.ArrayList;

/**
 *
 * @author Maxwell Herron
 */
public class ProcedureStatementNode extends StatementNode {

    /** Name of function. */
    private String name;

    /** List of all parameters in func call. */
    private ArrayList<ExpressionNode> parameters = new ArrayList<>();

    public ProcedureStatementNode(String name) { this.name = name; }

    // Getters
    public String getName() { return this.name; }
    public ArrayList<ExpressionNode> getParameters() { return this.parameters; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setParameters(ArrayList<ExpressionNode> parameters) { this.parameters = parameters; }

    /**
     * Creates a String representation of this function call node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Function: " + this.name + "\n";
        for( ExpressionNode parameter : parameters) {
            answer += parameter.indentedToString( level + 1);
        }
        return answer;
    }
}
