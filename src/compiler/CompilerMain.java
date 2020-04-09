package compiler;

import java.io.FileWriter;
import java.io.IOException;
import parser.Parser;
import semanticanalyzer.SemanticAnalyzer;
import syntaxtree.ProgramNode;

/**
 * CompilerMain.java
 * @author Maxwell Herron
 * Constructs and builds a Parser to be ran on a sample AlmostC program. It then
 * writes the tabular form of the symbol table to 'output.st', along with the printed syntax tree.
 */
public class CompilerMain {

    public static void main(String[] args) {
        // Takes the name of the file as a command line argument
        Parser p = new Parser(args[0], true);
        ProgramNode pr = p.program();
        SemanticAnalyzer sa = new SemanticAnalyzer(pr, p.getTable());
        try {
            FileWriter writer = new FileWriter("compiler/symbol_table_output.st");
            writer.write("\n");
            writer.write(p.getTable().toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            FileWriter writer = new FileWriter("compiler/syntax_tree_output.st");
            writer.write("*****PRINTING SYNTAX TREE*****");
            writer.write("\n");
            writer.write(pr.indentedToString(0));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            FileWriter writer = new FileWriter("compiler/semantic_analyzer_tree_.sa");
            sa.checkIdentifiersDeclaration();
            sa.assignDatatypes();
            sa.checkAssignmentTypes();
            writer.write(pr.indentedToString(0));
            if (sa.getCanWriteAssembly()) {
                writer.write("\nThis program can produce assembly!");
            } else {
                writer.write("\nThis program cannot produce assembly :(");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
