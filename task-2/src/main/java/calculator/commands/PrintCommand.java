package calculator.commands;

import calculator.core.Command;
import calculator.core.Context;

public class PrintCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        System.out.println(context.peek());
    }
}