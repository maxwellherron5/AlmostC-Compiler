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

    /**
     * Generates assembly for the given ExpressionNode
     * @param node
     * @param reg
     * @return
     */
    public String writeCode(ExpressionNode node, String reg) {

        String nodeCode = null;

        if (node instanceof FunctionCallNode) {

        }
        else if(node instanceof VariableNode) {

        }
        else if (node instanceof ValueNode) {

        }

        return nodeCode;
    }

    /**
     * Generates the assembly for the given VariableNode
     * @param varNode
     * @param resultRegister
     * @return
     */
    public String writeCode(VariableNode varNode, String resultRegister) {

        String name = varNode.getName();
        String code = "lw\t" + name + ", " + 
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
