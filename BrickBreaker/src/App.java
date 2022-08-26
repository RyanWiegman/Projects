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

    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        HomePage homePage = new HomePage();

        createFile();

        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(homePage);
        //obj.add(gameplay); 


    }
}
