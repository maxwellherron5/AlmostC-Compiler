package compiler;

import parser.Recognizer;
import java.io.FileWriter;
import java.io.IOException;

/**
 * CompilerMain.java
 * @author Maxwell Herron
 * Constructs and builds a Recognizer to be ran on a sample AlmostC program. It then
 * writes the tabular form of the symbol table to 'output.st'.
 */
public class CompilerMain {

    public static void main(String[] args) {
        Recognizer r = new Recognizer("src/compiler/firstProgram.ac", true);
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
