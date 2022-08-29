import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Rectangle;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private Mapgenerator map;
    String text = "";
    static File scoreFile = new File("BrickBreaker/src/HighScoreList.txt");
    List<ScoreList> list = new ArrayList<>();

    public Gameplay() {
        map = new Mapgenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    static int partition(List<ScoreList> array, int firstIndex, int lastIndex) {
        int pivot = array.get(lastIndex).getScore();    // int pivot = array[high];
        int i = (firstIndex - 1);// int i = (low - 1);

        for(int index = firstIndex; index < lastIndex; index++) {
            if(array.get(index).getScore() <= pivot) {
                i++;

                ScoreList temp = array.get(i);
                array.set(i, array.get(index));
                array.set(index, temp);
            }
        }

        ScoreList temp = array.get(i + 1);
        array.set((i + 1), array.get(lastIndex));
        array.set(lastIndex, temp);

        return (i + 1);
    }

    public void quickSort(List<ScoreList> array, int low, int high) {
        if(low < high) {
            int pi = partition(array, low, high);

            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    public static void write(String text, int score) {
        try {
            PrintWriter pw = new PrintWriter("BrickBreaker/src/HighScoreList.txt");
            Scanner scan = new Scanner(scoreFile);
            System.out.println("IN WRITE FUNC: " + text);

            if(scoreFile.length() == 0)
                pw.println(text + " " + score);
            else {

            }

            pw.println(text + " " + score);
            pw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            System.out.println("an error occured.");
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        // BACKGROUND
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // DRAWING MAP
        map.draw((Graphics2D) g);

        // SCORE
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // BORDERS
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // THE PADDLE
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // THE BALL
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        // GAME ENDS
        if(totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));

            g.drawString("You Won, Score: " + score, 150, 300);
            g.drawString("Enter Name: ", 250, 350);
            g.drawString("Press Enter to Restart.", 350, 350);
        }

        if(ballposY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.white);
            
            g.drawString("Game Over, Score: " + score, 150, 300);
            g.drawString("Enter Name: ", 150, 350);
            g.drawString(text, 400, 350);
            g.drawString("Press Enter to Restart.", 150, 450);
        }

        g.dispose();
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            if(!play && (ballposY > 570 || totalBricks <= 0)){
                text += e.getKeyChar();

                System.out.println(text);
                //write(text, score);
            }

            if(playerX >= 600)
                playerX = 600;
            else
                moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            if(!play && (ballposY > 570 || totalBricks <= 0)){
                text += e.getKeyChar();

                System.out.println(text);
                //write(text, score);
            }
            
            if(playerX < 10)
                playerX = 10;
            else
                moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                if(text.length() != 0){
                    ScoreList tempScore = new ScoreList(text, score);
                    list.add(new ScoreList(text, score));
                    int lastIndex = list.size() - 1;
                    quickSort(list, 0, lastIndex);

                    text = "";
                    // WRITE TO FILE.
                }
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new Mapgenerator(3, 7);

                repaint();
            }
        }
        if(!play && (ballposY > 570 || totalBricks <= 0)) {
            text += e.getKeyChar();

            System.out.println(text);
            //write(text, score);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(play) {
            ballposX += ballXDir;
            ballposY += ballYDir;

            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }

            A: for(int index = 0; index < map.map.length; index++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[index][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = index * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, index, j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXDir = -ballXDir;
                            }
                            else
                                ballYDir = -ballYDir;

                            break A;
                        }
                    }
                }
            }

            if(ballposX < 0)
                ballXDir = -ballXDir;
            if(ballposY < 0)
                ballYDir = -ballYDir;
            if(ballposX > 670)
                ballXDir = -ballXDir;

        }

        repaint();
    }
    
}
