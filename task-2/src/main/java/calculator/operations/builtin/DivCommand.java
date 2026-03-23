package calculator.operations.builtin;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "/", arity = 2)
public class DivCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        double b = context.pop();
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double a = context.pop();
        context.push(a / b);
    }
}