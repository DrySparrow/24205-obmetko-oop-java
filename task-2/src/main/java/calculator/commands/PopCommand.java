package calculator.commands;

import calculator.core.Command;
import calculator.core.Context;

public class PopCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        context.pop();
    }
}