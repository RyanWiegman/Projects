import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java .awt.Graphics;
import javax.swing.JPanel;
import java.io.File;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HomePage extends JPanel implements KeyListener{
    private boolean goTo;

    public HomePage() {
        this.goTo = false;
    }

    public void setgoTo(boolean value) {
        this.goTo = value;
    }

    public boolean getgoTo() {
        return goTo;
    }

    public void paint(Graphics g) {
        // BACKGROUND
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 700, 600);
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Name: ", 150, 50);
        g.drawString("Score: ", 400, 50);
        g.drawString("1: ", 50, 100);
        g.drawString("2: ", 50, 175);
        g.drawString("3: ", 50, 250);
        g.drawString("4: ", 50, 325);
        g.drawString("5: ", 50, 400);
        g.drawString("Press Enter to Start.", 200, 500);

        try {
            File file = new File("BrickBreaker/src/HighScoreList.txt");
            Scanner scan = new Scanner(file);
            int x = 100;
            int y = 100;
            int count = 0;

            while(scan.hasNext() && count < 5) {
                String name = scan.next();
                String userScore = scan.next();
                g.drawString(name, x, y);
                g.drawString(userScore, x + 300, y);
                System.out.println(name);
                y += 75;
                count++;
            }
            scan.close();
        } catch (Exception e) {
            System.out.println("an error occured");
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("ENTER PRESSED");
            goTo = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
