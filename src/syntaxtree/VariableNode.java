
package syntaxtree;

/**
 * Represents a variable in the syntax tree.
 * @author Erik Steinmetz
 */
public class VariableNode extends ExpressionNode {
    
    String name;
    
    /**
     * Creates a VariableNode with the given name.
     * @param name The name for this variable node.
     */
    public VariableNode( String name) {
        this.name = name;
    }
    
    public String getName() { return( this.name);}
    
    /**
     * Returns the name of the variable as the description of this node.
     * @return The name of this variable.
     */
    @Override
    public String toString() {
        return( name);
    }
    
    /**
     * Creates a String representation of this variable node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Name: " + this.name + " type: " + this.getType() +"\n";
        return answer;
    }

    @Override
    public boolean equals( Object o) {
        boolean answer = false;
        if( o instanceof VariableNode) {
            VariableNode other = (VariableNode)o;
            if( this.name.equals( other.name)) answer = true;
        }
        return answer;
    }    
    
}
