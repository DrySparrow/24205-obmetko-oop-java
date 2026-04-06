package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "PRINT", arity = 1)
public class PrintCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() < 1) {
            throw new IllegalStateException("To PRINT, you need at least 1 number in the stack.");
        }

        System.out.println(context.peek());
    }
}