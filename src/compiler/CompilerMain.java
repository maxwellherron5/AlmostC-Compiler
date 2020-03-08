package compiler;

import java.io.FileWriter;
import java.io.IOException;
import parser.Recognizer;

/**
 * CompilerMain.java
 * @author Maxwell Herron
 * Constructs and builds a Recognizer to be ran on a sample AlmostC program. It then
 * writes the tabular form of the symbol table to 'output.st'.
 */
public class CompilerMain {

    public static void main(String[] args) {
        // Takes the name of the file as a command line argument
        Recognizer r = new Recognizer(args[0], true);
        r.program();
        try {
            FileWriter writer = new FileWriter("src/compiler/output.st");
            writer.write(r.getTable().toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("File not found :(");
        }
    }
}
