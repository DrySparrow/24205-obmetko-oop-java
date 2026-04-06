package calculator.commands;

import calculator.core.Context;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SqrtCommandTest {
    @Test
    void testSqrtNormal() {
        Context context = new Context();
        context.push(16.0);
        new SqrtCommand().execute(context, new String[]{"SQRT"});
        assertEquals(4.0, context.pop(), 0.0001);
    }

    @Test
    void testSqrtNegative() {
        Context context = new Context();
        context.push(-1.0);
        assertThrows(ArithmeticException.class, () -> {
            new SqrtCommand().execute(context, new String[]{"SQRT"});
        });
    }
}