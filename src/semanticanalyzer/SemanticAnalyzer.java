package semanticanalyzer;

import symboltable.SymbolTable.DataType;
import symboltable.SymbolTable;
import syntaxtree.*;
import java.util.ArrayList;

/**
 * This class builds the Semantic Analyzer for the compiler. It relies on three
 * primary methods: checkIdentifierDeclaration(), assignDatatypes(), and
 * checkAssignmentTypes(). The first and last of those methods may set the boolean
 * flag canWriteAssembly to false if a semantic error is encountered, while the second
 * method simply assigns a type to all expression nodes.
 * @author Maxwell Herron
 */
public class SemanticAnalyzer {

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    /** This flag will be set to false if a semantic error is detected. */
    private boolean canWriteAssembly = true;

    /** The root of the syntax tree that is being analyzed. */
    private ProgramNode progNode;

    /** The symbol table generated by the syntax tree. */
    private SymbolTable table;

    /////////////////////////
    //    CONSTRUCTOR
    /////////////////////////

    /**
     * Base constructor.
     * @param progNode input syntax tree.
     * @param table symbol table generated by syntax tree.
     */
    public SemanticAnalyzer(ProgramNode progNode, SymbolTable table) {
        this.progNode = progNode;
        this.table = table;
    }

    /////////////////////////
    //      METHODS
    /////////////////////////

    /**
     * Scans for all appearances of identifiers, and compares that list to the list
     * of all declared identifiers. If a variable exists that has not been declared,
     * it will print out the name of that variable, and set the canWriteAssembly flag to false.
     */
    public void checkIdentifiersDeclaration() {

        ArrayList<String> variables = getVariables(progNode.getMain());
        ArrayList<String> allVariables = new ArrayList();
        String[] stringArray = progNode.indentedToString(0).split(" ");
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals("Name:")) {
                String var = stringArray[i+1];
                var = var.split("\n")[0];
                if (!allVariables.contains(var)) {
                    allVariables.add(var);
                }
            }
        }
        for (String s : allVariables) {
            if (!variables.contains(s)) {
                System.out.println("Undeclared variable: " + s + ".\tPlease declare this variable.");
                canWriteAssembly = false;
            }
        }
    }

    /**
     * Assigns data types to all ExpressionNodes. It does so by recursively calling the
     * method assignDataType(), which is overloaded by having an ExpressionNode param
     * or a StatementNode param.
     */
    public void assignDatatypes() {

        ArrayList<StatementNode> stateList = progNode.getMain().getStatements();

        for (StatementNode statement : stateList) {
            assignDataType(statement);
        }
    }

    /**
     * Iterates through all StatementNodes and determines if all AssignmentStatementNodes
     * are of a legal assignment. This means floats and ints cannot intermingle.
     */
    public void checkAssignmentTypes() {

        ArrayList<StatementNode> stateList = progNode.getMain().getStatements();

        for (StatementNode statement : stateList) {
            if (statement instanceof AssignmentStatementNode) {
                VariableNode lNode = ((AssignmentStatementNode) statement).getLvalue();
                ExpressionNode expNode = ((AssignmentStatementNode) statement).getExpression();
                if (lNode.getType() != expNode.getType()) {
                    canWriteAssembly = false;
                }
            }
        }
    }

    /**
     * Sets the data type of the input ExpressionNode
     * @param expNode ExpressionNode that is having its type set
     */
    private void assignDataType(ExpressionNode expNode) {

        if(expNode instanceof OperationNode) {
            assignDataType(((OperationNode) expNode).getLeft());
            assignDataType(((OperationNode) expNode).getRight());
            if (((OperationNode) expNode).getLeft().getType() == DataType.FLOAT |
            ((OperationNode) expNode).getRight().getType() == DataType.FLOAT) {
                expNode.setType(DataType.FLOAT);
            } else {
                expNode.setType(DataType.INT);
            }
        }
        else if (expNode instanceof ValueNode) {
            if (((ValueNode) expNode).getAttribute().contains(".")) {
                expNode.setType(DataType.FLOAT);
            } else {
                expNode.setType(DataType.INT);
            }
        }
        else if (expNode instanceof VariableNode) {
            // If variable is undeclared, assume it is an INT to prevent null pointer exception
            if (table.exists(((VariableNode) expNode).getName())) {
                expNode.setType(table.get(((VariableNode) expNode).getName()).getType());
            } else {
                expNode.setType(DataType.INT);
            }
        }
        else if (expNode instanceof FunctionCallNode) {
            ArrayList<ExpressionNode> expList = ((FunctionCallNode) expNode).getParameters();
            for (ExpressionNode exp : expList) {
                assignDataType(exp);
            }
            // If function is undeclared, assume it is an INT to prevent null pointer exception
            if (table.exists(((FunctionCallNode) expNode).getName())) {
                expNode.setType(table.get(((FunctionCallNode) expNode).getName()).getType());
            } else {
                expNode.setType(DataType.INT);
            }
        }
    }

    /**
     * Sets the datatype of the ExpressionNode associated with the input StatementNode
     * @param stateNode StatementNode that is having its ExpressionNodes' type set
     */
    private void assignDataType(StatementNode stateNode) {

        if (stateNode instanceof AssignmentStatementNode) {
            assignDataType(((AssignmentStatementNode) stateNode).getLvalue());
            assignDataType(((AssignmentStatementNode) stateNode).getExpression());
        }
        else if (stateNode instanceof CompoundStatementNode) {
            for (StatementNode state : ((CompoundStatementNode) stateNode).getStatements()) {
                assignDataType(state);
            }
        }
        else if (stateNode instanceof IfStatementNode) {
            assignDataType(((IfStatementNode) stateNode).getTest());
            assignDataType(((IfStatementNode) stateNode).getThenStatement());
            if (((IfStatementNode) stateNode).getElseStatement() != null) {
                assignDataType(((IfStatementNode) stateNode).getElseStatement());
            }
        }
        else if (stateNode instanceof ProcedureStatementNode) {
            ArrayList<ExpressionNode> expList = ((ProcedureStatementNode) stateNode).getParameters();
            for (ExpressionNode exp : expList) {
                assignDataType(exp);
            }
        }
        else if (stateNode instanceof ReturnStatementNode) {
            assignDataType(((ReturnStatementNode) stateNode).getReturnValue());
        }
        else if (stateNode instanceof WhileStatementNode) {
            assignDataType(((WhileStatementNode) stateNode).getTest());
            assignDataType(((WhileStatementNode) stateNode).getDoStatement());
        }
        else if (stateNode instanceof WriteStatementNode) {
            assignDataType(((WriteStatementNode) stateNode).getOutput());
        }
    }

    /**
     * Returns an ArrayList of declared variables.
     * @param body
     * @return
     */
    private ArrayList<String> getVariables(CompoundStatementNode body) {

        ArrayList<String> variables = new ArrayList();
        ArrayList<VariableNode> variableNodes = body.getVariables().getVars();
        for (VariableNode node : variableNodes) {
            variables.add(node.getName());
        }
        return variables;
    }

    // Getters
    public boolean getCanWriteAssembly() { return canWriteAssembly; }
}
