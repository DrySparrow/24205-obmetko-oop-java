package calculator.commands;

import calculator.core.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DivCommandTest {
    private final Context context = new Context();
    private DivCommand divCommand;

    @BeforeEach
    void setUp() {
        divCommand = new DivCommand();

        context.push(2);
        context.push(3);
    }

    @Test
    void divNormal() {
        divCommand.execute(context, new String[]{"/"});

        assertEquals(1, context.getStackSize());
        assertEquals(0.666666, context.pop(), 0.00001);
    }

    @Test
    void divWithEmptyStack() {
        context.pop();
        context.pop();

        assertThrows(RuntimeException.class, () -> {
            divCommand.execute(context, new String[]{"/"});
        }, "must be an error with empty stack");
    }
}