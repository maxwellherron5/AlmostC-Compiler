
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a compound statement in Almost C.
 * A compound statement is a set of variable declarations
 * along with a block of zero or more
 * statements to be run sequentially.
 * @author ErikSteinmetz
 */
public class CompoundStatementNode extends StatementNode {
    
    private DeclarationsNode variables;
    
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
    
    public DeclarationsNode getVariables() {
        return variables;
    }

    public CompoundStatementNode() { }

    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;
    }
    public void setStatements(ArrayList<StatementNode> statements) { this.statements = statements; }

    /**
     * Adds a statement to this compound statement.
     * @param state The statement to add to this compound statement.
     */
    public void addStatement( StatementNode state) {
        this.statements.add( state);
    }
    
    /**
     * Creates a String representation of this compound statement node 
     * and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Compound Statement\n";
        answer += variables.indentedToString(level + 1);
        for( StatementNode state : statements) {
            answer += state.indentedToString( level + 1);
        }
        return answer;
    }
}
