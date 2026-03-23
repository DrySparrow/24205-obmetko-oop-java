package calculator.operations.builtin;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "SQRT", arity = 1)
public class SqrtCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        double x = context.pop();
        if (x < 0) {
            throw new ArithmeticException("Квадратный корень из отрицательного числа");
        }
        context.push(Math.sqrt(x));
    }
}