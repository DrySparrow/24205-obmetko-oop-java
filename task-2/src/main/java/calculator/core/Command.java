package calculator.core;

public interface Command {
    void execute(Context context, String[] args);
}