package syntaxtree;

import scanner.Token;
import scanner.TokenType;

/**
 * Represents a sign.
 * @author Maxwell Herron
 */
public class SignNode extends ExpressionNode {

    /** The expression dictated by the sign. */
    private ExpressionNode expNode;

    /** The sign type (plus, minus, not). */
    private TokenType sign;

    public SignNode(TokenType sign) { this.sign = sign; }

    // Getters
    public ExpressionNode getExpNode() { return this.expNode; }
    public TokenType getSign() { return this.sign; }

    // Setters
    public void setExpNode(ExpressionNode expNode) { this.expNode = expNode; }
    public void setSign(TokenType sign) { this.sign = sign; }

    /**
     * Returns the sign token as a String.
     * @return The String version of the sign token.
     */
    @Override
    public String toString() { return this.sign.toString(); }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Sign: " + this.sign + "\n" + " Expression Type: " + expNode.getType();
        answer += expNode.indentedToString(level + 1);
        return( answer);
    }
}
