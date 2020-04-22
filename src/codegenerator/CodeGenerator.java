package codegenerator;

import syntaxtree.*;

/**
 * The final component of the compiler. This takes in an input ProgramNode,
 * and uses the overloaded method writeCode() to generate an assembly program.
 * @author Maxwell Herron
 */
public class CodeGenerator {

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    /** Keeps track of the current t register. */
    private int currentTRegister = 0;

    /** Keeps track of the current s register. */
    private int currentSRegister = 0;

    /** Keeps track of the while label number. */
    private int whileLabelNum = 0;

    /** Keeps track of the if label number. */
    private int ifLabelNum = 0;

    /////////////////////////
    //      METHODS
    /////////////////////////

    /**
     * Writes all boilerplate assembly, then generates program code by iterating
     * through all statements in main.
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
        code.append("newLine: .asciiz \"\\n\"\n");
        code.append("input: .asciiz \"Enter Value: \"\n");

        // Extracting all variables from main declarations
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
        code.append("sw    $ra, 0 ($sp)\n");

        code.append(writeCode(progNode.getMain()));

        code.append("\n\nlw    $s0, 4($sp)\n");
        code.append("lw    $ra, 0($sp)\n");
        code.append("addi  $sp, $sp, 8\n");
        code.append("jr    $ra\n");
        code.append("\n#++++++\n");
        code.append("# End Program\n");
        code.append("#------\n");

        return code.toString();
    }

    ///////////////////////////
    // METHODS FOR STATEMENTS
    ///////////////////////////

    /**
     * Determines what kind of statement it needs to write assembly for.
     * @param node
     * @return
     */
    public String writeCode(StatementNode node) {

        String nodeCode = "";

        if (node instanceof AssignmentStatementNode) {
            nodeCode = writeCode((AssignmentStatementNode) node);
        }
        else if (node instanceof CompoundStatementNode) {
            nodeCode = writeCode((CompoundStatementNode) node);
        }
        else if (node instanceof IfStatementNode) {
            nodeCode = writeCode((IfStatementNode) node);
        }
        else if (node instanceof ProcedureStatementNode) {
            nodeCode = writeCode((ProcedureStatementNode) node);
        }
        else if (node instanceof ReadStatementNode) {
            nodeCode = writeCode((ReadStatementNode) node);
        }
        else if (node instanceof ReturnStatementNode) {
            nodeCode = writeCode((ReturnStatementNode) node);
        }
        else if (node instanceof WhileStatementNode) {
            nodeCode = writeCode((WhileStatementNode) node);
        }
        else if (node instanceof WriteStatementNode) {
            nodeCode = writeCode((WriteStatementNode) node);
        }

        return nodeCode;
    }

    /**
     * Generates and returns the assembly for an if statement.
     * @param node
     * @return
     */
    public String writeCode(IfStatementNode node) {

        String nodeCode = "\n#++++++ If Statement ++++++\n";
        nodeCode += "if" + ifLabelNum + ":\n";
        ifLabelNum++;

        // Keeps track of current label num so it doesn't look track of it in nested ifs
        int curr = ifLabelNum;

        nodeCode += writeCode(node.getTest(), "$s" + currentSRegister);
        nodeCode += "beq    " + "$s" + currentSRegister + ", $zero, else" + curr;
        currentSRegister++;

        nodeCode += writeCode(node.getThenStatement());
        nodeCode += "j    endelse" + curr + "\n";
        nodeCode += "else" + curr + ":\n";
        nodeCode += writeCode(node.getElseStatement());
        nodeCode += "endelse" + curr + ":\n";
        nodeCode += "\n#------ End If Statement ------\n";
        currentSRegister--;
        return nodeCode;
    }

    /**
     * Generates and returns the assembly for an assignment statement.
     * @param node
     * @return
     */
    public String writeCode(AssignmentStatementNode node) {

        String nodeCode = "\n#++++++ Assignment ++++++\n";
        String reg = "$t" + ++currentTRegister;
        nodeCode += writeCode(node.getExpression(), reg);
        nodeCode += "sw    " + reg + ", " + node.getLvalue().getName() + "\n";
        currentTRegister--;
        return nodeCode;
    }

    /**
     * Iterates through all statements in compound node and generates the assembly for them.
     * @param node
     * @return
     */
    public String writeCode(CompoundStatementNode node) {

        String nodeCode = "";

        for (StatementNode state : node.getStatements()) {
            nodeCode += writeCode(state);
        }
        return nodeCode;
    }

    /**
     * Generates and returns the assembly for a void function call.
     * @param node
     * @return
     */
    public String writeCode(ProcedureStatementNode node) {

        String name = node.getName();
        String code = "jal   " + name + "\n";

        return code;
    }

    /**
     * Generates and returns the assembly for a read statement.
     * @param node
     * @return
     */
    public String writeCode(ReadStatementNode node) {

        String nodeCode = "\n#++++++ Read Statement ++++++\n";
        nodeCode += "li  $v0,  4" + "\nla  $a0,  input \n" + "syscall\n";
        nodeCode += "li  $v0, 5\n" + "syscall\n" + "sw  $v0,  " + node.getInput() + '\n';
        nodeCode += "\n#------ End Read ------\n";
        return nodeCode;
    }

    /**
     * Generates and returns the assembly for a write statement.
     * @param node
     * @return
     */
    public String writeCode(WriteStatementNode node) {

        String nodeCode = "\n#++++++ Write Statement ++++++\n";
        nodeCode += writeCode(node.getOutput(), "$s" + currentSRegister);
        nodeCode += "addi   $v0,   $zero,   1\n" + "add   $a0,   " + "$s" + currentSRegister + ",   $zero\n" +
                    "syscall\n" + "li   $v0,   4" + "\nla   $a0, newLine\n" + "syscall\n";
        nodeCode += "\n#------ End Write ------\n";
        return nodeCode;
    }

    /**
     * Generates and returns the assembly to create a return statement.
     * @param node
     * @return
     */
    public String writeCode(ReturnStatementNode node) {

        return writeCode(node.getReturnValue(), "$v0");
    }

    /**
     * Generates and returns the assembly to create a while loop.
     * @param node
     * @return
     */
    public String writeCode(WhileStatementNode node) {

        String nodeCode = "\n#++++++ While Loop ++++++\n";
        nodeCode += "while" + whileLabelNum + ":\n";
        whileLabelNum++;

        nodeCode += writeCode(node.getTest(), "$s" + currentSRegister);

        nodeCode += "beq    " + "$s" + currentSRegister + ", $zero, endwhile" + whileLabelNum;

        currentSRegister++;

        nodeCode += writeCode(node.getDoStatement());

        nodeCode += "j while" + (whileLabelNum - 1) + "\n";
        nodeCode += "endwhile" + whileLabelNum + ":\n";
        nodeCode += "\n#------ End While Loop ------\n";
        currentSRegister--;
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
        else if (node instanceof OperationNode) {
            nodeCode = writeCode((OperationNode) node, resultRegister);
        }

        return nodeCode;
    }

    /**
     * Generates the assembly for the given operation.
     * @param opNode
     * @param resultRegister
     * @return
     */
    public String writeCode(OperationNode opNode, String resultRegister) {

        String nodeCode = "";
        ExpressionNode lValue = opNode.getLeft();
        ExpressionNode rValue = opNode.getRight();

        String leftReg = "$t" + currentTRegister++;
        nodeCode += writeCode(lValue, leftReg);

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
                nodeCode += "sgt  " + resultRegister +  ",  " + leftReg + ",  " + rightReg + "\n";
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
        String code = "lw    " + resultRegister + ", " + name + "\n";
        return code;
    }

    /**
     * Generates the assembly for the given ValueNode
     * @param valNode
     * @param resultRegister
     * @return
     */
    public String writeCode(ValueNode valNode, String resultRegister) {

        String value = valNode.getAttribute();
        String code = "addi    " + resultRegister + ",  $zero, " + value + "\n";
        return code;
    }

    ///////////////////////////
    // METHODS FOR FUNCTIONS
    ///////////////////////////

    /**
     *
     * @param node
     * @return
     */
    public String writeCode(FunctionNode node) {

        int paramNumber = node.getParameters().size();
        String nodeCode = node.getName() + ":\n";
        nodeCode += "addi  $sp, $sp, -" + (paramNumber * 4);
        nodeCode += "";

        return nodeCode;
    }
}
