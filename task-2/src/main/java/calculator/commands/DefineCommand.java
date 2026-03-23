package calculator.commands;

import calculator.core.Command;
import calculator.core.Context;

public class DefineCommand implements Command {
    @Override
    public void execute(Context context, String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("DEFINE требует имя и значение");
        }
        String name = args[1];
        double value;
        try {
            value = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное значение для DEFINE: " + args[2]);
        }
        context.define(name, value);
    }
}