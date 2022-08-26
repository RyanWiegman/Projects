import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java .awt.Graphics;
import javax.swing.JPanel;
import java.io.File;
import java.util.Scanner;

public class HomePage extends JPanel{
    public void paint(Graphics g) {
        // BACKGROUND
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 700, 600);
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Name: ", 150, 50);
        g.drawString("Score: ", 400, 50);
        g.drawString("1: ", 50, 100);
        g.drawString("2: ", 50, 200);
        g.drawString("3: ", 50, 300);
        g.drawString("4: ", 50, 400);
        g.drawString("5: ", 50, 500);
        

        try {
            File file = new File("BrickBreaker/src/HighScoreList.txt");
            Scanner scan = new Scanner(file);
            int x = 100;
            int y = 100;

            while(scan.hasNextLine()) {
                String data = scan.nextLine();
                g.drawString(data, x, y);
                System.out.println(data);
                y += 100;

                scan.close();
            }
        } catch (Exception e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
    }
}
