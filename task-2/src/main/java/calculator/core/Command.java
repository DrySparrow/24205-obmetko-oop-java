package calculator.core;

// НИКАКИХ импортов из calculator.commands здесь быть не должно!
public interface Command {
    void execute(Context context, String[] args);
}