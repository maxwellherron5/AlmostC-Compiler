package compiler;

import parser.Recognizer;

import java.io.*;

/**
 * CompilerMain.java
 * @author Maxwell Herron
 *
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
