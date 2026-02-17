public class InputValidator {
    public static boolean isValid(String guess, int expectedLength) {
        if (guess.equals(Game.EXIT_GAME)) {
            System.out.println("GAME OVER");
            System.exit(0);
        }
        if (guess.length() != expectedLength) {
            System.out.println("invalid length of number!");
            return false;
        }
        if (!guess.matches("\\d+")) {
            System.out.println("it's not a number!");
            return false;
        }
        return true;
    }
}
