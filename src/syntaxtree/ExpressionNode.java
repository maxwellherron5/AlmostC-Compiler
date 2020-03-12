
package syntaxtree;

/**
 * General representation of any expression.
 * @author Erik Steinmetz
 */
public abstract class ExpressionNode extends SyntaxTreeNode {
    
    private DataType type;
    
    public DataType getType() { return this.type;}
    
    public void setType( DataType type) { this.type = type;}
    
}
