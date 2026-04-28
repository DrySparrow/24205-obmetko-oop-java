package calculator.commands;

import calculator.core.Context;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PopCommandTest {
    @Test
    void testPop() {
        Context context = new Context();
        context.push(10.0);
        new PopCommand().execute(context, new String[]{"POP"});
        assertEquals(0, context.getStackSize());
    }
}