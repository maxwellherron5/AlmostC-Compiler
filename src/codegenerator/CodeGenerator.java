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

    ///////////////////////////
    // METHODS FOR STATEMENTS
    ///////////////////////////

    public String writeCode(StatementNode node, String resultRegister) {

        String nodeCode = null;

        if (node instanceof AssignmentStatementNode) {
            nodeCode = writeCode((AssignmentStatementNode) node, resultRegister);
        }
        else if (node instanceof CompoundStatementNode) {
            nodeCode = writeCode((CompoundStatementNode) node, resultRegister);
        }
        else if (node instanceof IfStatementNode) {
            nodeCode = writeCode((IfStatementNode) node, resultRegister);
        }
        else if (node instanceof ProcedureStatementNode) {
            nodeCode = writeCode((ProcedureStatementNode) node, resultRegister);
        }
        else if (node instanceof ReadStatementNode) {
            nodeCode = writeCode((ReadStatementNode) node, resultRegister);
        }
        else if (node instanceof ReturnStatementNode) {
            nodeCode = writeCode((ReturnStatementNode) node, resultRegister);
        }
        else if (node instanceof WhileStatementNode) {
            nodeCode = writeCode((WhileStatementNode) node, resultRegister);
        }
        else if (node instanceof WriteStatementNode) {
            nodeCode = writeCode((WriteStatementNode) node, resultRegister);
        }

        return nodeCode;
    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(IfStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(AssignmentStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(CompoundStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ProcedureStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ReadStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(WriteStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ReturnStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(WhileStatementNode node, String resultRegister) {

        String nodeCode = null;



        return nodeCode;
    }

    ///////////////////////////
    // METHODS FOR EXPRESSIONS
    ///////////////////////////

    /**
     * Generates assembly for the given ExpressionNode
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ExpressionNode node, String resultRegister) {

        String nodeCode = null;

        if (node instanceof FunctionCallNode) {
            nodeCode = writeCode((FunctionCallNode) node, resultRegister);
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
                nodeCode += "or  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case EQUAL: {
                nodeCode += "beq  " + leftReg + ",  " + rightReg + ",  ";
                break;
            }
            case LESS_THAN: {
                nodeCode += "blt  " + leftReg + ",  " + rightReg + ",  ";
                break;
            }
            case GREATER_THAN: {
                nodeCode += "bgt  " + leftReg + ",  " + rightReg + ",  ";
                break;
            }
            case LESS_THAN_EQUAL: {
                nodeCode += "ble  " + leftReg + ",  " + rightReg + ",  ";
                break;
            }
            case GREATER_THAN_EQUAL: {
                nodeCode += "bge  " + leftReg + ",  " + rightReg + ",  ";
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
