import java.io.IOException;

public class BullsAndCowsGame {
    private static final GameLogger.Log log = GameLogger.log;

    public static void main(String[] args) {
        try {
            GameLogger.setup();
            log.info("Game started");
            new Game().play();
        } catch (IOException e) {
            System.err.println("Could not setup logger: " + e.getMessage());
        }
    }
}
