package calculator.core;

public class Main {
    public static void main(String[] args) {
        Runner runner = new Runner();

        if (args.length == 1) {
            runner.runFromFile(args[0]);
        } else if (args.length == 0){
            runner.runInteractive();
        } else {
            System.out.println("unknown command. using: ./Main in.txt or ./Main");
        }
    }   
}