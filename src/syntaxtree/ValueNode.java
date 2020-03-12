
package syntaxtree;

/**
 * Represents a value or number in an expression.
 * @author Erik Steinmetz
 */
public class ValueNode extends ExpressionNode {
    
    String attribute;
    
    /**
     * Creates a value node with the given attribute.
     * @param attr The attribute of this node.
     */
    public ValueNode( String attr) {
        this.attribute = attr;
    }
    
    public String getAttribute() { return( this.attribute);}
    
    /**
     * Returns the attribute as the description of this node.
     * @return The attribute String of this node.
     */
    @Override
    public String toString() {
        return( attribute);
    }
    
    /**
     * Creates a String representation of this value node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Value: " + this.attribute + "\n";
        return answer;
    }

    @Override
    public boolean equals( Object o) {
        boolean answer = false;
        if( o instanceof ValueNode) {
            ValueNode other = (ValueNode)o;
            if( this.attribute.equals( other.attribute)) answer = true;
        }
        return answer;
    }    
}
