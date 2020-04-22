package codegenerator;

import org.junit.jupiter.api.Test;
import parser.Parser;
import scanner.TokenType;
import syntaxtree.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests asm code generation on ProgramNode, StatementNode, and ExpressionNode
 * @author Maxwell Herron
 */
class CodeGeneratorTest {

    @Test
    public void writeCodeForProgramTest() {

        Parser p = new Parser("main() {\n" +
                "        int dollars, yen, bitcoins;\n" +
                "        int myFloat;\n" +
                "        myFloat = 1;\n" +
                "        dollars = 0;\n" +
                "        yen = 1 + 20;\n" +
                "        if (yen > 8) {\n" +
                "        yen = yen - 1;   " +
                "        dollars = dollars + 1;\n" +
                "        } else { yen = 2 + 2;};\n" +
                "        write(2 + 2);\n" +
                "     };", false);
        ProgramNode progNode = p.program();
        CodeGenerator c = new CodeGenerator();
        String expected = "#++++++\n" +
                "# Data Segment\n" +
                "#------\n" +
                ".data\n" +
                "newLine: .asciiz \"\\n\"\n" +
                "input: .asciiz \"Enter Value: \"\n" +
                "dollars:    .word    0\n" +
                "yen:    .word    0\n" +
                "bitcoins:    .word    0\n" +
                "myFloat:    .word    0\n" +
                "\n" +
                "\n" +
                "#++++++\n" +
                "# Program Segment\n" +
                "#------\n" +
                ".text\n" +
                "\n" +
                "\n" +
                "#++++++\n" +
                "# Main Function \n" +
                "#------\n" +
                "main:\n" +
                "addi  $sp, $sp, -4\n" +
                "sw    $ra, 0 ($sp)\n" +
                "\n" +
                "#++++++ Assignment ++++++\n" +
                "addi    $t1,  $zero, 1\n" +
                "sw    $t1, myFloat\n" +
                "\n" +
                "#++++++ Assignment ++++++\n" +
                "addi    $t1,  $zero, 0\n" +
                "sw    $t1, dollars\n" +
                "\n" +
                "#++++++ Assignment ++++++\n" +
                "addi    $t1,  $zero, 1\n" +
                "addi    $t2,  $zero, 20\n" +
                "add  $t1,  $t1,  $t2\n" +
                "sw    $t1, yen\n" +
                "\n" +
                "#++++++ If Statement ++++++\n" +
                "if0:\n" +
                "lw    $t0, yen\n" +
                "addi    $t1,  $zero, 8\n" +
                "sgt  $s0,  $t0,  $t1\n" +
                "beq    $s0, $zero, else1\n" +
                "#++++++ Assignment ++++++\n" +
                "lw    $t1, yen\n" +
                "addi    $t2,  $zero, 1\n" +
                "sub  $t1,  $t1,  $t2\n" +
                "sw    $t1, yen\n" +
                "\n" +
                "#++++++ Assignment ++++++\n" +
                "lw    $t1, dollars\n" +
                "addi    $t2,  $zero, 1\n" +
                "add  $t1,  $t1,  $t2\n" +
                "sw    $t1, dollars\n" +
                "j    endelse1\n" +
                "else1:\n" +
                "\n" +
                "#++++++ Assignment ++++++\n" +
                "addi    $t1,  $zero, 2\n" +
                "addi    $t2,  $zero, 2\n" +
                "add  $t1,  $t1,  $t2\n" +
                "sw    $t1, yen\n" +
                "endelse1:\n" +
                "\n" +
                "#------ End If Statement ------\n" +
                "\n" +
                "#++++++ Write Statement ++++++\n" +
                "addi    $t0,  $zero, 2\n" +
                "addi    $t1,  $zero, 2\n" +
                "add  $s0,  $t0,  $t1\n" +
                "addi   $v0,   $zero,   1\n" +
                "add   $a0,   $s0,   $zero\n" +
                "syscall\n" +
                "li   $v0,   4\n" +
                "la   $a0, newLine\n" +
                "syscall\n" +
                "\n" +
                "#------ End Write ------\n" +
                "\n" +
                "\n" +
                "lw    $s0, 4($sp)\n" +
                "lw    $ra, 0($sp)\n" +
                "addi  $sp, $sp, 8\n" +
                "jr    $ra\n" +
                "\n" +
                "#++++++\n" +
                "# End Program\n" +
                "#------\n";
        String actual = c.writeCodeForRoot(progNode);
        assertEquals(expected, actual);
    }

    @Test
    public void writeCodeForExpressionTest() {

        CodeGenerator c = new CodeGenerator();

        ValueNode lVal = new ValueNode("5");
        ValueNode rVal = new ValueNode("55");
        OperationNode opNode = new OperationNode(TokenType.MODULO, lVal, rVal);

        String expected = "addi    $t0,  $zero, 5\n" +
                "addi    $t1,  $zero, 55\n" +
                "div  $t0,  $t1\n" +
                "mfhi  $s0\n";
        String actual = c.writeCode(opNode, "$s0");
        assertEquals(expected, actual);
    }

    @Test
    public void writeCodeForStatementTest() {

        CodeGenerator c = new CodeGenerator();

        IfStatementNode ifNode = new IfStatementNode();

        ValueNode lVal = new ValueNode("5");
        ValueNode rVal = new ValueNode("55");
        OperationNode opNode = new OperationNode(TokenType.MODULO, lVal, rVal);

        WriteStatementNode writeOne = new WriteStatementNode();
        writeOne.setOutput(new ValueNode("5"));

        WriteStatementNode writeTwo = new WriteStatementNode();
        writeTwo.setOutput(new ValueNode("10"));

        ifNode.setTest(opNode);
        ifNode.setThenStatement(writeOne);
        ifNode.setElseStatement(writeTwo);

        String expected = "\n#++++++ If Statement ++++++\n" +
                "if0:\n" +
                "addi    $t0,  $zero, 5\n" +
                "addi    $t1,  $zero, 55\n" +
                "div  $t0,  $t1\n" +
                "mfhi  $s0\n" +
                "beq    $s0, $zero, else1\n" +
                "#++++++ Write Statement ++++++\n" +
                "addi    $s1,  $zero, 5\n" +
                "addi   $v0,   $zero,   1\n" +
                "add   $a0,   $s1,   $zero\n" +
                "syscall\n" +
                "li   $v0,   4\n" +
                "la   $a0, newLine\n" +
                "syscall\n" +
                "\n" +
                "#------ End Write ------\n" +
                "j    endelse1\n" +
                "else1:\n" +
                "\n" +
                "#++++++ Write Statement ++++++\n" +
                "addi    $s1,  $zero, 10\n" +
                "addi   $v0,   $zero,   1\n" +
                "add   $a0,   $s1,   $zero\n" +
                "syscall\n" +
                "li   $v0,   4\n" +
                "la   $a0, newLine\n" +
                "syscall\n" +
                "\n" +
                "#------ End Write ------\n" +
                "endelse1:\n" +
                "\n" +
                "#------ End If Statement ------\n";
        String actual = c.writeCode(ifNode);
        assertEquals(expected, actual);
    }
}