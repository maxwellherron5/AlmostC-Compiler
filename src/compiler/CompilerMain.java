package compiler;

import parser.Recognizer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * CompilerMain.java
 * @author Maxwell Herron
 *
 */
public class CompilerMain {

    public static void main(String[] args) {
        Recognizer r = new Recognizer("src/compiler/firstProgram.ac", true);
        r.program();
        r.getTable().toString();
    }
}
