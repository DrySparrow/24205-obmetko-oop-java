package calculator.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Deque<Double> stack = new ArrayDeque<>();
    private final Map<String, Double> variables = new HashMap<>();

    public void push(double value) {
        stack.push(value);
    }

    public double pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }
        return stack.pop();
    }

    public double peek() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Стек пуст");
        }
        return stack.peek();
    }

    public void define(String name, double value) {
        variables.put(name, value);
    }

    public Double getVariable(String name) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Переменная не определена: " + name);
        }
        return variables.get(name);
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }
}