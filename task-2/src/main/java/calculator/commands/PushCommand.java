package calculator.commands;

import calculator.core.Command;
import calculator.core.Context;

public class PushCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PUSH требует один аргумент");
        }
        String arg = args[1];
        double value;
        if (context.hasVariable(arg)) {
            value = context.getVariable(arg);
        } else {
            try {
                value = Double.parseDouble(arg);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректный аргумент PUSH: " + arg);
            }
        }
        context.push(value);
    }
}