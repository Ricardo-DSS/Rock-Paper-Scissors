
public class User {

    private String input;
    private String name;
    private int score;

    User(String name, int score, String input) {
        this.name  = name;
        this.score = score;
        this.input = input;
    }

    public int getScore() { return this.score; }
    public void setScore(int score) { this.score = score; }

    public String getInput() {
        return this.input;
    }
    public void setInput(String input) {
        this.input = input;
    }

}
