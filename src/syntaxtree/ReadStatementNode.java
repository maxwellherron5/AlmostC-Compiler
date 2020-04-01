package syntaxtree;

/**
 *
 * @author Maxwell Herron
 */
public class ReadStatementNode extends StatementNode {

    /** input being read. */
    private ExpressionNode output;

    private ExpressionNode getOutput() { return this.output; }

    private void setOutput(ExpressionNode output) { this.output = output; }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "If\n";
        answer += this.output.indentedToString( level + 1);
        return answer;
    }

}
