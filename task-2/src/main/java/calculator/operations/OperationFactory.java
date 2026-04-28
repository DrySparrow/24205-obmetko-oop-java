package calculator.operations;

import calculator.annotations.Operation;
import calculator.core.Command;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class OperationFactory {
    private static final Map<String, Command> commands = new HashMap<>();

    public static void init() {}    // для запуска кода и вывода загруженных функций сразу после старта программы

    static {
        try (InputStream is = OperationFactory.class.getResourceAsStream("calculator.config")) {
            if (is == null) {
                throw new FileNotFoundException("Файл calculator.config не найден в ресурсах");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String jarPath;

            while ((jarPath = reader.readLine()) != null) {
                jarPath = jarPath.trim();
                if (jarPath.isEmpty() || jarPath.startsWith("#")) {
                    continue; // Пропускаем пустые строки и комментарии
                }

                try {
                    loadJar(jarPath);
                } catch (Exception e) {
                    System.err.println("Не удалось загрузить JAR: " + jarPath + ". Ошибка: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadJar(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не существует по пути: " + path);
        }

        // Создаем загрузчик для конкретного JAR
        URLClassLoader loader = new URLClassLoader(
                new URL[]{file.toURI().toURL()},
                OperationFactory.class.getClassLoader()
        );

        try (JarFile jar = new JarFile(file)) {
            jar.stream().forEach(entry -> {
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    try {
                        String className = entryName.replace("/", ".").replace(".class", "");
                        Class<?> clazz = loader.loadClass(className);

                        if (clazz.isAnnotationPresent(Operation.class) && Command.class.isAssignableFrom(clazz)) {
                            Operation ann = clazz.getAnnotation(Operation.class);

                            Command cmd = (Command) clazz.getDeclaredConstructor().newInstance();
                            commands.put(ann.name(), cmd);

                            System.out.println("Загружена команда [" + ann.name() + "] из " + path);
                        }
                    } catch (Throwable ignored) {
                    }
                }
            });
        }
    }

    public static Command getCommand(String name) {
        return commands.get(name);
    }
}