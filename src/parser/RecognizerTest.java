package parser;

import org.junit.jupiter.api.Test;
import scanner.*;

import static org.junit.jupiter.api.Assertions.*;

class RecognizerTest {

    @Test
    void factor() {
        Recognizer r = new Recognizer("myVariable123", false);
        try {
            r.factor();
        } catch (Exception e) {
            fail();
        }
        assertNull(r.getLookahead().getType());
    }
}