package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "SQRT", arity = 1)
public class SqrtCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() < 1) {
            throw new IllegalStateException("To SQRT, you need at least 1 number in the stack.");
        }

        double x = context.pop();
        if (x < 0) {
            throw new ArithmeticException("Square root of a negative number");
        }
        context.push(Math.sqrt(x));
    }
}