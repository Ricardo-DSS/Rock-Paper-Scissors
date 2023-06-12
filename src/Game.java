import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Game {

    boolean isPlaying = false;

    public void gaming() {
        Scanner scanner   = new Scanner(System.in);
        Computer computer = new Computer();

        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.println("Hello, " + name);
        int rating = Integer.parseInt(checkExistingUsers(getFileInfo(), name));
        User user = new User(name, rating, "");

        scanner.nextLine();
        String in = scanner.nextLine();

        if (in.isEmpty()) {
            System.out.println("Okay, let's start");
            while (!isPlaying) {
                user.setInput(scanner.next());
                if (!"!exit".equals(user.getInput())) {
                    result(user, computer.computerMove());
                } else {
                    System.out.println("Bye!");
                    break;
                }
            }
        } else {
            System.out.println("Okay, let's start");
            String[] extraOptions = in.split(",");

            while (!isPlaying) {
                user.setInput(scanner.next());
                int index = checkValidInput(user, extraOptions);
                if ("!rating".equals(user.getInput())) {
                    System.out.printf("Your rating: %d\n", user.getScore());
                } else if ("!exit".equals(user.getInput())) {
                    System.out.println("Bye!");
                    break;
                } else if (index >= 0){
                    customResult(reorganizeArray(index, extraOptions), user, computer.computerMoveCustom(extraOptions));
                } else if (index == -1) {
                    System.out.println("Invalid input");
                }
            }
        }
    }

    private void result(User user, String computer) {
        if (user.getInput().equals(computer)) {
            System.out.printf("There is a draw (%s)\n", computer);
            user.setScore(user.getScore() + 50);
        } else {
            switch (user.getInput()) {
                case "rock" -> {
                    if ("scissors".equals(computer)) {
                        System.out.printf("Well done. The computer chose %s and failed\n", computer);
                        user.setScore(user.getScore() + 100);
                    } else {
                        System.out.printf("Sorry, but the computer chose %s\n", computer);
                    }
                }
                case "paper" -> {
                    if ("rock".equals(computer)) {
                        System.out.printf("Well done. The computer chose %s and failed\n", computer);
                        user.setScore(user.getScore() + 100);
                    } else {
                        System.out.printf("Sorry, but the computer chose %s\n", computer);
                    }
                }
                case "scissors" -> {
                    if ("paper".equals(computer)) {
                        System.out.printf("Well done. The computer chose %s and failed\n", computer);
                        user.setScore(user.getScore() + 100);
                    } else {
                        System.out.printf("Sorry, but the computer chose %s\n", computer);
                    }
                }
                case "!rating" -> System.out.printf("Your rating: %d\n", user.getScore());
                default -> System.out.println("Invalid input");
            }
        }
    }

    private List<String> getFileInfo() {
        List<String> data = new ArrayList<>();
        try {
            String filePath = new File("src/rating.txt").getAbsolutePath();
            File pathFile = new File(filePath);
            Scanner file = new Scanner(pathFile);
            while (file.hasNextLine()) {
                String line = file.next();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    private String checkExistingUsers(List<String> data, String name) {
        for (int i = 0; i < data.size(); i += 2) {
            if (data.get(i).equals(name)) {
                return data.get(i+1);
            }
        }
        return "0";
    }

    private int checkValidInput(User user, String[] extraOptions) {
        for (int i = 0; i < extraOptions.length; i++) {
            if (extraOptions[i].equals(user.getInput())) {
                return i;
            }
        }
        return -1;
    }

    private List<String> reorganizeArray(int index, String[] extraOptions) {
        List<String> nextOptions = new ArrayList<>();
        int size = extraOptions.length;
        for (int i = index + 1; i < size; i++) {
            nextOptions.add(extraOptions[i]);
        }
        if (nextOptions.size() != extraOptions.length - 1) {
            for (int i = 0; i < index; i++) {
                nextOptions.add(extraOptions[i]);
            }
        }
        return nextOptions;
    }

    private void customResult(List<String> customOptions, User user, String computer) {
        int halfList = customOptions.size() / 2;
        if (user.getInput().equals(computer)) {
            System.out.printf("There is a draw (%s)\n", computer);
            user.setScore(user.getScore() + 50);
        } else {
            List<String> lose = customOptions.subList(0, halfList);
            List<String> win  = customOptions.subList(halfList, customOptions.size());

            for (String item : lose) {
                if (computer.equals(item)) {
                    System.out.printf("Sorry, but the computer chose %s\n", computer);
                    break;
                }
            }

            for (String item : win) {
                if (computer.equals(item)) {
                    System.out.printf("Well done. The computer chose %s and failed\n", computer);
                    user.setScore(user.getScore() + 100);
                }
            }
        }
    }
}
