
package syntaxtree;

import java.util.ArrayList;
import symboltable.SymbolTable.DataType;

/**
 * Represents a single function definition.
 * @author Erik Steinmetz
 */
public class FunctionNode extends SyntaxTreeNode {
    
    private String name;
    private DataType returnType;
    private ArrayList<VariableNode> parameters = new ArrayList();
    private CompoundStatementNode body;
    
    /**
     * Creates a function definition node with the given name.
     * @param name The name of this function.
     */
    public FunctionNode( String name) {
        this.name = name;
    }
    
    public String getName() { return this.name;}
    
    public DataType getReturnType() { return this.returnType;}
    
    public void setReturnType( DataType type) { this.returnType = type;}
    
    public CompoundStatementNode getBody() {
        return this.body;
    }

    public void setBody(CompoundStatementNode body) {
        this.body = body;
    }
    
    /**
     * Adds a parameter to this declaration.
     * @param aParameter The variable node to add to this declaration.
     */
    public void addParameter( VariableNode aParameter) {
        parameters.add( aParameter);
    }

    public ArrayList<VariableNode> getParameters() { return this.parameters; }
    
    /**
     * Creates a String representation of this function definition node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Function: " + this.name + " returns " + this.returnType + "\n";
        for( VariableNode parameter : parameters) {
            answer += parameter.indentedToString( level + 1);
        }
        answer += body.indentedToString( level + 1);
        return answer;
    }

}
