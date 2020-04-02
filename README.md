# Maxwell Herron's Micro-C Compiler

This project is to be used to compile and run Micro-C programs. It is to be comprised of four main packages:

1. scanner
2. parser
3. symboltable
4. semantic analyzer
5. code generator
6. compiler
 
In its current state, only the scanner, recognizer, and symbol table are written. However, the 
symbol table is not yet integrated with the recognizer. To run the scanner, follow these steps:

1. Clone the repo
2. In your terminal, enter the src folder, then enter the scanner package and run the command
`java -jar jflex.jar scanner.jflex`.
3. Now compile the generated file by entering `javac MyScanner.java`.
4. To run the test of it in main, simply enter `javac Main.java` followed by
`java Main`.
5. To run unit tests on the scanner, repeat step four, but replace `Main.java` with `ScannerTest.java`.
6. Enjoy!

The best way to run the recognizer in its current state, is to navigate to the file `RecognizerTest.java`.
Here, you can selectively run individual tests through your IDE, or you can run them all at once
to see if they pass the tests. In its current state, all tests are happy tests, so none will fail unless
you modify the input string. The parser can be run in two different ways: through the command line using `CompilerMain.java`,
or by running tests on it through your IDE in `ParserTest.java` . If you run it in main, you can enter your very own AlmostC
program as an input through the command prompt, and it will write out the generated symbol table and syntax tree in an output file. Enjoy!

Similarly to the recognizer, the only way to interact with the symbol table in the current state of this compiler is to
run the tests that have been written for it. To do so, navigate to `SymbolTableTest.java`, which can be found in the
parser package. Once in this file, run the tests just as you have done with the other test classes.

To mess around with the syntax tree, the best way to do so is to navigate to `SyntaxTreeTest.java`. Here, there are tests available
to be ran, and you can play around and verify that correct syntax trees are being generated based on a given input.

The compiler package is the easiest way to run the Compiler in its entirety. To do so, navigate to `CompilerMain.java`,
compile the file, and run it, providing the name of the input file as a command line argument. This will run the recognizer on an input file in the same package, 
and then write the 
tabular form of the generated symbol table to an output file.
