import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class BullsAndCowsGame {
    final static Logger logger = LoggerFactory.getLogger(BullsAndCowsGame.class);

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 10;
    public static final String SHOW_HIDDEN_NUM = "0";
    public static final String EXIT_GAME = "00";

    private final Scanner scanner;
    private final BullsAndCowsEvaluator evaluator;

    public BullsAndCowsGame() {
        scanner = new Scanner(System.in);
        evaluator = new BullsAndCowsEvaluator();
    }

    public void play() {
        System.out.println("""
               ---------------Game rules:---------------
               Bulls - number of digits in correct positions
               Cows - number of digits not in correct positions
               enter 0 - show the number and start new game
               enter 00 - exit game
               """);

        while (true) {
            System.out.print("set the length of the number:  ");
            String input = scanner.nextLine();
            logger.info("User input: {}", input);
            
            if (input.equals(EXIT_GAME)) {
                System.out.println("GAME OVER");
                return;
            }
            
            int length;
            try {
                length = Integer.parseInt(input);
                while (length < MIN_LENGTH || length > MAX_LENGTH) {
                    System.out.printf("Invalid length! must be %d <= length <= %d%n", MIN_LENGTH, MAX_LENGTH);
                    System.out.print("set the length of the number:  ");
                    length = Integer.parseInt(scanner.nextLine());
                }
                logger.info("user set length: {}", length);
            } catch (NumberFormatException e) {
                System.out.println("invalid length!");
                continue;
            }

            SecretNumber secret = new SecretNumber(length);
            logger.info("SecretNumber is {}", secret.getValue());
            int tries = 0;
            boolean victory = false;

            while (!victory) {
                tries++;
                System.out.print("enter your guess:  ");
                String guess = scanner.nextLine();
                logger.info("user guess {}", guess);

                if (guess.equals(EXIT_GAME)) {
                    System.out.println("GAME OVER");
                    return;
                }
                if (guess.equals(SHOW_HIDDEN_NUM)) {
                    System.out.println("Secret number: " + secret.getValue());
                    break;
                }
                if (!InputValidator.isValid(guess, secret.getLength())) {
                    tries--;
                    continue;
                }

                logger.info("evaluating result...");
                BullsAndCowsResult result = evaluator.evaluate(guess, secret.getValue());
                System.out.println("Bulls:  " + result.bulls());
                System.out.println("Cows: " + result.cows());
                logger.info("Bulls: {} Cows: {}", result.bulls(), result.cows());
                
                if (result.bulls() == secret.getLength()) {
                    logger.info("victory");
                    victory = true;
                }
            }

            if (victory) {
                System.out.println();
                System.out.println("Victory!!!");
                System.out.println("Number of attempts: " + tries);
            }
        }
    }
}
