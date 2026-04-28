import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BullsAndCowsEvaluatorTest {
    @Test
    void evaluate0b0c() {
        final BullsAndCowsEvaluator evaluator;
        evaluator = new BullsAndCowsEvaluator();
        BullsAndCowsResult res = evaluator.evaluate("1234", "5678");

        assertEquals(0, res.bulls(), "must be 0 bulls");
        assertEquals(0, res.cows(), "must be 0 cows");
    }

    @Test
    void evaluate0b3c() {
        final BullsAndCowsEvaluator evaluator;
        evaluator = new BullsAndCowsEvaluator();
        BullsAndCowsResult res = evaluator.evaluate("1234", "2340");

        assertEquals(0, res.bulls(), "must be 0 bulls");
        assertEquals(3, res.cows(), "must be 3 cows");
    }

    @Test
    void evaluateVictory() {
        final BullsAndCowsEvaluator evaluator;
        evaluator = new BullsAndCowsEvaluator();
        BullsAndCowsResult res = evaluator.evaluate("1234", "1234");

        assertEquals(4, res.bulls(), "must be 4 bulls");
        assertEquals(0, res.cows(), "must be 0 cows");
    }

    @Test
    void evaluate2b2c() {
        final BullsAndCowsEvaluator evaluator;
        evaluator = new BullsAndCowsEvaluator();
        BullsAndCowsResult res = evaluator.evaluate("1234", "1243");

        assertEquals(2, res.bulls(), "must be 2 bulls");
        assertEquals(2, res.cows(), "must be 2 cows");
    }

    @Test
    void evaluate1b3c() {
        final BullsAndCowsEvaluator evaluator;
        evaluator = new BullsAndCowsEvaluator();
        BullsAndCowsResult res = evaluator.evaluate("1234", "1342");

        assertEquals(1, res.bulls(), "must be 1 bulls");
        assertEquals(3, res.cows(), "must be 3 cows");
    }
}
