package calculator.core;

import calculator.operations.OperationFactory;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();

        if (args.length > 0) {
            runFromFile(new File(args[0]), context);
        } else {
            runInteractive(context);
        }
    }

    private static void runFromFile(File file, Context context) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                executeLine(scanner.nextLine(), context);
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static void runInteractive(Context context) {
        System.out.println("program is running, to exit, enter Q");
        Scanner scanner = new Scanner(System.in);
        // Цикл будет работать, пока не введут EXIT или не закроют поток
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("EXIT") || line.equalsIgnoreCase("Q")) {
                break;
            }

            // Вызываем выполнение. Ошибки внутри executeLine не прервут этот цикл.
            executeLine(line, context);
        }
    }

    private static void executeLine(String line, Context context) {
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        String[] parts = line.split("\\s+");
        String commandName = parts[0];

        try {
            Command cmd = OperationFactory.getCommand(commandName);

            if (cmd != null) {
                // Если команда что-то выбросит (например, EmptyStackException),
                // мы поймаем это ниже.
                cmd.execute(context, parts);
            } else {
                // Это сработает, если фабрика вернет null (хотя обычно она бросает исключение)
                System.err.println("Ошибка: Команда '" + commandName + "' не зарегистрирована.");
            }

        } catch (IllegalArgumentException e) {
            // Ошибки аргументов (например, PUSH без числа)
            System.err.println("Ошибка аргументов: " + e.getMessage());
        } catch (IllegalStateException e) {
            // Ошибки состояния (например, стек пуст)
            System.err.println("Ошибка состояния: " + e.getMessage());
        } catch (ArithmeticException e) {
            // Математические ошибки (деление на ноль)
            System.err.println("Математическая ошибка: " + e.getMessage());
        } catch (Exception e) {
            // Любая другая непредвиденная ошибка
            System.err.println("Непредвиденная ошибка выполнения '" + commandName + "': " + e.getMessage());
        }
    }
}