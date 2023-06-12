import java.util.Random;
public class Computer {

    Random random = new Random();

    public String computerMove() {
        int rock = 0;
        int paper = 1;

        int number = random.nextInt(3);

        if (number == rock) {
            return "rock";
        } else if (number == paper) {
            return "paper";
        }
        return "scissors";
    }

    public String computerMoveCustom(String[] extraOptions) {
        int number = random.nextInt(extraOptions.length);
        return extraOptions[number];
    }
}
