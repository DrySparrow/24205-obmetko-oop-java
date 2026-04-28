package calculator.commands;

import calculator.core.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest {
    private final Context context = new Context();
    private AddCommand addCommand;

    @BeforeEach
    void setUp() {
        addCommand = new AddCommand();

        context.push(2);
        context.push(3);
    }

    @Test
    void addNormal() {
        addCommand.execute(context, new String[]{"+"});

        assertEquals(1, context.getStackSize());
        assertEquals(5.0, context.pop(), 0.00001);
    }

    @Test
    void addWithEmptyStack() {
        context.pop();
        context.pop();

        assertThrows(RuntimeException.class, () -> {
            addCommand.execute(context, new String[]{"+"});
        }, "must be an error with empty stack");
    }
}