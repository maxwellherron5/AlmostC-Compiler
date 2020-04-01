package syntaxtree;

/**
 *
 * @author Maxwell Herron
 */
public class ReadStatementNode extends StatementNode {

    /** input being read. */
    private String input;

    public String getInputput() { return this.input; }

    public void setInput(String input) { this.input = input; }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Read: " + this.input + "\n";
        return answer;
    }

}
