import java.util.*;

public class Game {
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 10;
    public static final String SHOW_HIDDEN_NUM = "0";
    public static final String EXIT_GAME = "00";

    private Scanner scanner;
    private SecretNumber secret;
    private BullsAndCowsEvaluator evaluator;
    private int tries;
    private boolean victory;

    public Game() {
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
            
            if (input.equals(EXIT_GAME)) {
                System.out.println("GAME OVER");
                return;
            }
            
            int length;
            try {
                length = Integer.parseInt(input);
                while (length < MIN_LENGTH || length > MAX_LENGTH) {
                    System.out.println(String.format("Invalid length! must be %d <= length <= %d", MIN_LENGTH, MAX_LENGTH));
                    System.out.print("set the length of the number:  ");
                    length = Integer.parseInt(scanner.nextLine());
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid length!");
                continue;
            }

            secret = new SecretNumber(length);
            tries = 0;
            victory = false;

            while (!victory) {
                tries++;
                System.out.print("enter your guess:  ");
                String guess = scanner.nextLine();

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

                BullsAndCowsResult result = evaluator.evaluate(guess, secret.getValue());
                System.out.println("Bulls:  " + result.getBulls());
                System.out.println("Cows: " + result.getCows());

                if (result.getBulls() == secret.getLength()) {
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
