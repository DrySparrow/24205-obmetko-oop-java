package calculator.commands;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.core.Context;

@Operation(name = "DEFINE", arity = 2)
public class DefineCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("DEFINE requires 2 arguments.");
        }
        String name = args[1];
        double value;
        try {
            value = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect value for DEFINE: " + args[2]);
        }
        context.define(name, value);
    }
}