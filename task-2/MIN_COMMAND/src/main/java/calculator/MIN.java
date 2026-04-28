package calculator.commands; // Убедись, что пакет соответствует твоей структуре

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;
import java.util.ArrayDeque;
import java.util.Deque;

@Operation(name = "MIN", arity = 1)
public class MIN implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (context.getStackSize() == 0) {
            throw new IllegalStateException("To MIN, you need at least 1 number in the stack.");
        }
        Deque<Double> tempStack = new ArrayDeque<>();

        double minEl = context.pop();
        tempStack.push(minEl);

        while (context.getStackSize() > 0) {
            double current = context.pop();
            minEl = Math.min(minEl, current);
            tempStack.push(current);
        }

        while (!tempStack.isEmpty()) {
            context.push(tempStack.pop());
        }

        System.out.println(minEl);
    }
}