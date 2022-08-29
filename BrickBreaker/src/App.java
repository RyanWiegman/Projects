import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;

public class App {
    

    public static void createFile() {
        try {
            File highScore = new File("BrickBreaker/src/HighScoreList.txt");
            if(highScore.createNewFile())
                System.out.println("File Created: " + highScore.getName());
            else
                System.out.println("File already exists.");
        } catch(IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        JFrame game = new JFrame();
        Gameplay gameplay = new Gameplay();
        HomePage homePage = new HomePage();

        createFile();

        game.setSize(700, 600);
        game.setTitle("Brick Breaker Game");
        game.setResizable(false);
        game.setVisible(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.add(gameplay);
        // while(!homePage.getgoTo())
        //     System.out.println("playing");

    }
}
