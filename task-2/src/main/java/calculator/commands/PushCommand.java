package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "PUSH", arity = 1)
public class PushCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("PUSH requires 1 argument.");
        }
        String arg = args[1];
        double value;
        if (context.hasVariable(arg)) {
            value = context.getVariable(arg);
        } else {
            try {
                value = Double.parseDouble(arg);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid argument PUSH: " + arg);
            }
        }
        context.push(value);
    }
}