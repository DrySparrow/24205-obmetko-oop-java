package calculator.core;

import calculator.operations.OperationFactory;
import java.io.File;
import java.util.Scanner;

public class Runner {
    private final Context context;

    public Runner() {
        this.context = new Context();
    }

    // Режим работы с файлом
    public void runFromFile(String filePath) {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                executeLine(scanner.nextLine());
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Интерактивный режим (терминал)
    public void runInteractive() {
        System.out.println("program is running, to exit, enter Q");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("EXIT") || line.equalsIgnoreCase("Q")) {
                break;
            }
            executeLine(line);
        }
    }

    // Логика обработки одной строки
    public void executeLine(String line) {
        if (line.isEmpty() || line.startsWith("#")) return;

        String[] parts = line.split("\\s+");
        String commandName = parts[0];

        try {
            Command cmd = OperationFactory.getCommand(commandName);
            if (cmd != null) {
                cmd.execute(context, parts);
            } else {
                System.out.println("Error: Command '" + commandName + "' not registered\n.");
            }
        } catch (Exception e) {
            System.out.println("Runtime error: " + e.getMessage());
        }
    }
}