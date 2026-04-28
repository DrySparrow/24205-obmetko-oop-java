package calculator.commands; // Убедись, что пакет соответствует твоей структуре

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "MIN", arity = 1)
public class MIN implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() == 0) {
            throw new IllegalStateException("To MIN, you need at least 1 number in the stack.");
        }

        double A = context.pop();
        double B = context.pop();

        context.push(Math.min(A, B));
    }
}