package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "-", arity = 2)
public class SubCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() < 2) {
            throw new IllegalStateException("To SUB, you need at least 2 numbers in the stack.");
        }

        double b = context.pop();
        double a = context.pop();
        context.push(a - b);
    }
}