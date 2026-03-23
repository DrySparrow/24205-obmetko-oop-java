package calculator.operations.builtin;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "+", arity = 2)
public class AddCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        double b = context.pop();
        double a = context.pop();
        context.push(a + b);
    }
}