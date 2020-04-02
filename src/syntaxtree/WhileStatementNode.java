package syntaxtree;

/**
 * Represents a while statement.
 * @author Maxwell Herron
 */
public class WhileStatementNode extends StatementNode {

    /** This is the while conditional. */
    private ExpressionNode test;

    /** Statement to be executed in the loop. */
    private StatementNode doStatement;

    // Getters
    public ExpressionNode getTest() { return this.test; }
    public StatementNode getDoStatement() { return this.doStatement; }

    // Setters
    public void setTest(ExpressionNode test) { this.test = test; }
    public void setDoStatement(StatementNode doStatement) { this.doStatement = doStatement; }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "While\n";
        answer += this.test.indentedToString( level + 1);
        answer += this.doStatement.indentedToString( level + 1);
        return answer;
    }

}
