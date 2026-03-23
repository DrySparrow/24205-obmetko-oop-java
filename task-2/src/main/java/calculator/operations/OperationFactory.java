package calculator.operations;

import calculator.annotations.Operation;
import calculator.core.Command;
import calculator.operations.builtin.*;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OperationFactory {
    private static final Map<String, Command> operations = new HashMap<>();

    static {
        registerBuiltInOperations();
    }

    private static void registerBuiltInOperations() {
        register("+", new AddCommand(), 2);
        register("-", new SubCommand(), 2);
        register("*", new MulCommand(), 2);
        register("/", new DivCommand(), 2);
        register("SQRT", new SqrtCommand(), 1);
    }

    private static void register(String name, Command command, int arity) {
        operations.put(name, command);
    }

    public static void loadJar(String jarPath) throws Exception {
        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            throw new IllegalArgumentException("JAR не найден: " + jarPath);
        }

        try (URLClassLoader loader = new URLClassLoader(
                new URL[]{jarFile.toURI().toURL()},
                Thread.currentThread().getContextClassLoader())) {

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName()
                                .replace("/", ".")
                                .replace(".class", "");
                        try {
                            Class<?> clazz = loader.loadClass(className);
                            Operation opAnnotation = clazz.getAnnotation(Operation.class);
                            if (opAnnotation != null && Command.class.isAssignableFrom(clazz)) {
                                Command command = (Command) clazz.getDeclaredConstructor().newInstance();
                                operations.put(opAnnotation.name(), command);
                                System.out.println("Загружена операция: " + opAnnotation.name());
                            }
                        } catch (Exception e) {
                            System.err.println("Не удалось загрузить класс " + className + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public static Command getCommand(String name) {
        Command cmd = operations.get(name);
        if (cmd == null) {
            throw new IllegalArgumentException("Неизвестная операция: " + name);
        }
        return cmd;
    }
}