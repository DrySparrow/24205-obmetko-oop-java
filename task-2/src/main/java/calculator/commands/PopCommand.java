package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "POP", arity = 1)
public class PopCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() < 1) {
            throw new IllegalStateException("To POP, you need at least 1 number in the stack.");
        }

        context.pop();
    }
}