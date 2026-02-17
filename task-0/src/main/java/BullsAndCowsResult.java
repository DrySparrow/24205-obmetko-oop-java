public class BullsAndCowsResult {   // store the result of the guess
    private final int bulls;
    private final int cows;

    public BullsAndCowsResult(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }
    
    public int getBulls() { return bulls; }
    public int getCows() { return cows; }
}
