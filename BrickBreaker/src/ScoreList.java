public class ScoreList {
    public String name;
    public int score;

    ScoreList(String username, int userScore) {
        this.name = username;
        this.score = userScore;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}
