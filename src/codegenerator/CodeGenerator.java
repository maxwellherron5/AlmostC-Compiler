package codegenerator;

import scanner.TokenType;
import syntaxtree.*;

/**
 *
 * @author Maxwell Herron
 */
public class CodeGenerator {

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    /** Keeps track of the current register. */
    private int currentRegister = 0;

    /////////////////////////
    //      METHODS
    /////////////////////////

    /**
     *
     * @param progNode
     * @return
     */
    public String writeCodeForRoot(ProgramNode progNode) {

        StringBuilder code = new StringBuilder();
        code.append(".data\n");
        for (VariableNode varNode : progNode.getMain().getVariables().getVars()) {
            code.append(varNode.getName() + ":\t.word\t0\n");
        }
        code.append("\n\n\n.text\n");
        code.append("main:\n");
        return code.toString();
    }

    public String writeCode(StatementNode node, String resultRegister) {

        String nodeCode = null;

        if (node instanceof AssignmentStatementNode) {

        }
        else if (node instanceof CompoundStatementNode) {

        }
        else if (node instanceof IfStatementNode) {

        }
        else if (node instanceof ProcedureStatementNode) {

        }
        else if (node instanceof ReadStatementNode) {

        }
        else if (node instanceof ReturnStatementNode) {

        }
        else if (node instanceof WhileStatementNode) {

        }
        else if (node instanceof WriteStatementNode) {

        }

        return nodeCode;
    }

    /**
     * Generates assembly for the given ExpressionNode
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ExpressionNode node, String resultRegister) {

        String nodeCode = null;

        if (node instanceof FunctionCallNode) {

        }
        else if(node instanceof VariableNode) {
            nodeCode = writeCode((VariableNode) node, resultRegister);
        }
        else if (node instanceof ValueNode) {
            writeCode((ValueNode) node, resultRegister);
        }

        return nodeCode;
    }

    public String writeCode(OperationNode opNode, String resultRegister) {

        String nodeCode = null;
        ExpressionNode lValue = opNode.getLeft();
        ExpressionNode rValue = opNode.getRight();

        String leftReg = "t" + currentRegister++;
        nodeCode = writeCode(lValue, leftReg);

        String rightReg = "t" + currentRegister++;
        nodeCode += writeCode(rValue, rightReg);

        switch (opNode.getOperation()) {
            case PLUS: {
                nodeCode += "add  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case MINUS: {
                nodeCode += "sub  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case MULTIPLY: {
                nodeCode += "mult  " + leftReg + ",  " + rightReg + "\n";
                nodeCode += "mflo  " + resultRegister + "\n";
                break;
            }
            case DIVIDE: {
                nodeCode += "div  " + leftReg + ",  " + rightReg + "\n";
                nodeCode += "mflo  " + resultRegister + "\n";
                break;
            }
            case MODULO: {
                nodeCode += "div  " + leftReg + ",  " + rightReg + "\n";
                nodeCode += "mfhi  " + resultRegister + "\n";
                break;
            }
            case AND: {
                nodeCode += "and  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case OR: {
                nodeCode += "and  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case EQUAL: {

                break;
            }
            case LESS_THAN: {

                break;
            }
            case GREATER_THAN: {

                break;
            }
            case LESS_THAN_EQUAL: {

                break;
            }
            case GREATER_THAN_EQUAL: {

                break;
            }
        }

        return nodeCode;
    }

    /**
     * Generates assembly for the given FunctionCallNode
     * @param funcNode
     * @param resultRegister
     * @return
     */
    public String writeCode(FunctionCallNode funcNode, String resultRegister) {

        String name = funcNode.getName();
        String code = "jal " + name + "\n";

        return code;
    }

    /**
     * Generates the assembly for the given VariableNode
     * @param varNode
     * @param resultRegister
     * @return
     */
    public String writeCode(VariableNode varNode, String resultRegister) {

        String name = varNode.getName();
        String code = "lw\t" + resultRegister + ", " + name + "\n";
        return name;
    }

    /**
     * Generates the assembly for the given ValueNode
     * @param valNode
     * @param resultRegister
     * @return
     */
    public String writeCode(ValueNode valNode, String resultRegister) {

        String value = valNode.getAttribute();
        String code = "addi\t" + resultRegister + ",\t$zero, " + value + "\n";
        return code;
    }
}
