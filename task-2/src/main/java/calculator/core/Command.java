package calculator.core;


import calculator.commands.DefineCommand;
import calculator.commands.PopCommand;
import calculator.commands.PrintCommand;
import calculator.commands.PushCommand;

public interface Command {
    void execute(Context context, String[] args);

    public static Command getBuiltInCommand(String name) {
        switch (name) {
            case "DEFINE":
                return new DefineCommand();
            case "PUSH":
                return new PushCommand();
            case "POP":
                return new PopCommand();
            case "PRINT":
                return new PrintCommand();
            default:
                return null;
        }
    }
}