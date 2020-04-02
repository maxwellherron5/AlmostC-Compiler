package compiler;

import java.io.FileWriter;
import java.io.IOException;
import parser.Recognizer;
import parser.Parser;
import syntaxtree.ExpressionNode;
import syntaxtree.ProgramNode;

/**
 * CompilerMain.java
 * @author Maxwell Herron
 * Constructs and builds a Recognizer to be ran on a sample AlmostC program. It then
 * writes the tabular form of the symbol table to 'output.st'.
 */
public class CompilerMain {

    public static void main(String[] args) {
        // Takes the name of the file as a command line argument
        Parser p = new Parser(args[0], true);
        ProgramNode pr = p.program();
        try {
            FileWriter writer = new FileWriter("compiler/output.st");
            writer.write("*****PRINTING SYMBOL TABLE*****");
            writer.write("\n");
            writer.write(p.getTable().toString());
            writer.write("\n\n");
            writer.write("*****PRINTING SYNTAX TREE*****");
            writer.write("\n");
            writer.write(pr.indentedToString(0));
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        Parser p = new Parser("int sum(int x, int y);\n" +
//                "main() {\n" +
//                "    int number1, number2;\n" +
//                "    number1 = 2;\n" +
//                "    number2 = 4;\n" +
//                "    if(sum(number1 + number2) == 6) {\n" +
//                "        // celebrate\n" +
//                "    } else {\n" +
//                "        // cry to sleep\n" +
//                "    };\n" +
//                "    return 0;\n" +
//                "}\n" +
//                "int sum(int x, int y) {\n" +
//                "    return x + y;\n" +
//                "}", false);
//        System.out.println(p.program().indentedToString(0));
    }
}
