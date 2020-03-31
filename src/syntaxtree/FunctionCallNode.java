package syntaxtree;

import symboltable.SymbolTable.DataType;
import java.util.ArrayList;

/**
 *
 * @author Maxwell Herron
 */
public class FunctionCallNode extends ExpressionNode {

    /** Name of function. */
    private String name;

    /** Type of function. */
    private DataType type;

    /** List of all parameters in func call. */
    private ArrayList<ExpressionNode> parameters = new ArrayList<>();

    public FunctionCallNode() { }
    public FunctionCallNode(String name) { this.name = name; }

    // Getters
    public String getName() { return this.name; }
    public DataType getType() { return this.type; }
    public ArrayList<ExpressionNode> getParameters() { return this.parameters; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setType(DataType type) { this.type = type; }
    public void setParameters(ArrayList<ExpressionNode> parameters) { this.parameters = parameters; }

    public void addArgument(ExpressionNode arg) {
        this.parameters.add(arg);
    }

    /**
     * Creates a String representation of this function call node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Function: " + this.name + " returns " + this.type + "\n";
        for( ExpressionNode parameter : parameters) {
            answer += parameter.indentedToString( level + 1);
        }
        return answer;
    }
}
