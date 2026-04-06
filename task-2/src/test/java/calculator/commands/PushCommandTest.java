package calculator.commands;

import calculator.core.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PushCommandTest {
    private final Context context = new Context();
    private PushCommand pushCommand;

    @BeforeEach
    void setUp() {
        pushCommand = new PushCommand();
    }

    @Test
    void testPushNumber() {
        pushCommand.execute(context, new String[]{"PUSH", "42"});
        assertEquals(1, context.getStackSize());
        assertEquals(42.0, context.pop());
    }

    @Test
    void testPushVariable() {
        context.define("X", 7.0);
        pushCommand.execute(context, new String[]{"PUSH", "X"});
        assertEquals(7.0, context.pop());
    }
}