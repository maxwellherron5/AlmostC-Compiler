package syntaxtree;

/**
 * Represents a return statement.
 * @author Maxwell Herron
 */
public class ReturnStatementNode extends StatementNode {

    /** Return value of the statement. */
    private ExpressionNode returnValue;

    public ExpressionNode getReturnValue() { return this.returnValue; }

    public void setReturnValue(ExpressionNode returnValue) { this.returnValue = returnValue; }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Return\n";
        answer += this.returnValue.indentedToString( level + 1);
        return answer;
    }

}
