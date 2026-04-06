package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "/", arity = 2)
public class DivCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() < 2) {
            throw new IllegalStateException("To DIV, you need at least 2 numbers in the stack.");
        }

        double b = context.pop();
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        double a = context.pop();
        context.push(a / b);
    }
}