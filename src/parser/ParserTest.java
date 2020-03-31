package parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import syntaxtree.ExpressionNode;

import java.util.ArrayList;

/**
 *
 * @author Maxwell Herron
 */
class ParserTest {

    @Test
    void testOperator() {

    }

    @Test
    void expressionListTest() {
        Parser p = new Parser("2+2, 3+3", false);
        ArrayList<ExpressionNode> e = new ArrayList<>();
        e = p.expressionList();
        for (int i = 0; i < e.size(); i++) {
            System.out.println(e.get(i));
        }
    }
}