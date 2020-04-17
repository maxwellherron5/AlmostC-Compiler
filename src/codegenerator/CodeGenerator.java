package codegenerator;

import syntaxtree.*;

/**
 *
 * @author Maxwell Herron
 */
public class CodeGenerator {

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    /** Keeps track of the current t register. */
    private int currentTRegister = 0;

    private int currentSRegister = 0;

    /** Keeps track of the label number. */
    private int labelNum = 0;


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
        // Boilerplate
        code.append("#++++++\n");
        code.append("# Data Segment\n");
        code.append("#------\n");
        code.append(".data\n");

        for (VariableNode varNode : progNode.getMain().getVariables().getVars()) {
            code.append(varNode.getName() + ":    .word    0\n");
        }

        code.append("\n\n#++++++\n");
        code.append("# Program Segment\n");
        code.append("#------\n");
        code.append(".text\n");
        code.append("\n\n#++++++\n");
        code.append("# Main Function \n");
        code.append("#------\n");
        code.append("main:\n");
        code.append("addi  $sp, $sp, -4\n");
        code.append("sw    $ra, 0 ($sp\n");

        for (StatementNode state : progNode.getMain().getStatements()) {
            code.append(writeCode(state, "$s"));
        }

        code.append("\n\nlw    $s0, 4($sp)\n");
        code.append("lw    $ra, 0($sp)\n");
        code.append("addi  $sp, $sp, 8\n");
        code.append("jr    $ra\n");

        return code.toString();
    }

    ///////////////////////////
    // METHODS FOR STATEMENTS
    ///////////////////////////

    public String writeCode(StatementNode node, String resultRegister) {

        String nodeCode = ""    ;

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

        String nodeCode = ""    ;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(AssignmentStatementNode node, String resultRegister) {

        String nodeCode = "\n#++++++ Assignment ++++++\n";
        nodeCode += writeCode(node.getExpression(), resultRegister);
        nodeCode += "sw    " + resultRegister + currentSRegister + ", " + node.getLvalue().getName() + "\n";
        currentSRegister++;
        return nodeCode;
    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(CompoundStatementNode node, String resultRegister) {

        String nodeCode = ""    ;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ProcedureStatementNode node, String resultRegister) {

        String nodeCode = ""    ;



        return nodeCode;

    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ReadStatementNode node, String resultRegister) {

        String nodeCode = ""    ;

        return nodeCode;
    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(WriteStatementNode node, String resultRegister) {

        String nodeCode = ""    ;

        return nodeCode;
    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(ReturnStatementNode node, String resultRegister) {

        return writeCode(node.getReturnValue(), "$v0");
    }

    /**
     *
     * @param node
     * @param resultRegister
     * @return
     */
    public String writeCode(WhileStatementNode node, String resultRegister) {

        String nodeCode = "\n#++++++ While Loop ++++++\n";
        nodeCode += "while" + labelNum + "\n";
        labelNum++;

        nodeCode += writeCode(node.getTest(), resultRegister);
        resultRegister = "$s" + ++currentTRegister;
        nodeCode += writeCode(node.getDoStatement(), resultRegister);

        nodeCode += "j while" + (labelNum - 1) + "\n";
        nodeCode += "end-while" + labelNum + "\n";
        labelNum++;
        nodeCode += "\n#------ End While Loop ------\n";
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

        String nodeCode = "";

        if (node instanceof FunctionCallNode) {
            nodeCode = writeCode((FunctionCallNode) node, resultRegister);
        }
        else if(node instanceof VariableNode) {
            nodeCode = writeCode((VariableNode) node, resultRegister);
        }
        else if (node instanceof ValueNode) {
            nodeCode = writeCode((ValueNode) node, resultRegister);
        }

        return nodeCode;
    }

    public String writeCode(OperationNode opNode, String resultRegister) {

        String nodeCode;
        ExpressionNode lValue = opNode.getLeft();
        ExpressionNode rValue = opNode.getRight();

        String leftReg = "$t" + currentTRegister++;
        nodeCode = writeCode(lValue, leftReg);

        String rightReg = "$t" + currentTRegister++;
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
                nodeCode += "seq  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case LESS_THAN: {
                nodeCode += "slt  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case GREATER_THAN: {
                nodeCode += "sgt  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case LESS_THAN_EQUAL: {
                nodeCode += "sle  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
            case GREATER_THAN_EQUAL: {
                nodeCode += "sge  " + resultRegister + ",  " + leftReg + ",  " + rightReg + "\n";
                break;
            }
        }

        currentTRegister -= 2;
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
        String code = "jal   " + name + "\n";

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
        String code = "lw    " + resultRegister + currentTRegister + ", " + name + "\n";
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
        String code = "addi    " + resultRegister + currentTRegister + ",  $zero, " + value + "\n";
        return code;
    }
}
