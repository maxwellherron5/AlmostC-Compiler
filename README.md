# Maxwell Herron's Micro-C Compiler

This project is to be used to compile and run Micro-C programs. It is to be comprised of four main packages:

1. scanner

2. parser
3. semantic analyzer
4. code generator
 
In its current state, only the scanner and recognizer are written. To run the scanner, follow these steps:

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
you modify the input string. Enjoy!


