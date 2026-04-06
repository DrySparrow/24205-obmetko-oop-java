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

    static {
        try {
            // Читаем конфиг через getResourceAsStream
            InputStream is = OperationFactory.class.getResourceAsStream("calculator.config");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String jarPath = reader.readLine(); // Читаем первую строку с путем к JAR

            loadJar(jarPath.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadJar(String path) throws Exception {
        File file = new File(path);
        URLClassLoader loader = new URLClassLoader(
                new URL[]{file.toURI().toURL()},
                OperationFactory.class.getClassLoader() // Передаем родительский загрузчик
        );
        JarFile jar = new JarFile(file);

        jar.stream().forEach(entry -> {
            if (entry.getName().endsWith(".class")) {
                try {
                    String className = entry.getName().replace("/", ".").replace(".class", "");
                    Class<?> clazz = loader.loadClass(className);

                    if (clazz.isAnnotationPresent(Operation.class) && Command.class.isAssignableFrom(clazz)) {
                        Operation ann = clazz.getAnnotation(Operation.class);
                        Command cmd = (Command) clazz.getDeclaredConstructor().newInstance();
                        commands.put(ann.name(), cmd);
                    }
                } catch (Exception ignored) {}
            }
        });
        jar.close();
    }

    public static Command getCommand(String name) {
        return commands.get(name);
    }
}