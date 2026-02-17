import java.io.IOException;
import java.util.logging.*;

public class GameLogger {
    private static final Logger logger = Logger.getLogger(GameLogger.class.getName());
    private static boolean isSetup = false;  // флаг, что уже настроено

    public static final Log log = new Log();

    public static class Log {
        public void info(String message) {
            logger.info(message);
        }
    }

    public static void setup() throws IOException {
        if (isSetup) return;  // если уже настроили - выходим

        FileHandler fileHandler = new FileHandler("game.log", false);  // true = дозапись
        fileHandler.setEncoding("UTF-8");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        isSetup = true;
    }
}
