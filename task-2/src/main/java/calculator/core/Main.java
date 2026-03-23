package calculator;

import calculator.commands.*;
import calculator.core.Context;
import calculator.operations.OperationFactory;
import calculator.core.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя файла: ");
        final String RESOURCES_PATH = "src/main/resources/";
        String fileName = RESOURCES_PATH + scanner.nextLine();

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            Context context = new Context();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                String commandName = parts[0];

                try {
                    Command command = getBuiltInCommand(commandName);
                    if (command != null) {
                        command.execute(context, parts);
                    } else {
                        command = OperationFactory.getCommand(commandName);
                        command.execute(context, parts);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка выполнения команды '" + commandName + "': " + e.getMessage());
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + fileName);
        }

        scanner.close();
    }

    private static Command getBuiltInCommand(String name) {
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