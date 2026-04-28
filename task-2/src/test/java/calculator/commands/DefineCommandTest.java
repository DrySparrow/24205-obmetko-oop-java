package calculator.commands;

import calculator.core.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefineCommandTest {
    private final Context context = new Context();
    private DefineCommand defineCommand;

    @BeforeEach
    void setUp() {
        defineCommand = new DefineCommand();
    }

    @Test
    void testDefineVariable() {
        // DEFINE A 10.5
        defineCommand.execute(context, new String[]{"DEFINE", "A", "10.5"});

        assertTrue(context.hasVariable("A"));
        assertEquals(10.5, context.getVariable("A"), 0.0001);
    }
}