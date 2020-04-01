package syntaxtree;

/**
 *
 * @author Maxwell Herron
 */
public class WriteStatementNode extends StatementNode {

    /** Output that will be written. */
    private ExpressionNode output;

    public ExpressionNode getOutput() { return this.output; }

    public void setOutput(ExpressionNode output) { this.output = output; }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Write\n";
        answer += this.output.indentedToString( level + 1);
        return answer;
    }
}
