import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Rectangle;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private Random random = new Random();
    private int score = 0;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 330;
    private int ballposY = 530;
    private int[] xDir = {-2, -1, 1, 2};
    private int[] yDir = {-4, -3, -2, 2, 3, 4};
    private int xIndex = random.nextInt(4);
    private int ballXDir = xDir[xIndex];
    private int yIndex = random.nextInt(4);
    private int ballYDir = yDir[yIndex];
    private Mapgenerator map;
    String text = "";
    static File scoreFile = new File("BrickBreaker/src/HighScoreList.txt");
    static List<ScoreList> list = new ArrayList<>();
    private int col = random.nextInt(7) + 5;
    private int row = random.nextInt(8) + 3;
    private int totalBricks = col * row;
    private int deleteChar = 0;
    private int points = 100 / (row * col);

    public Gameplay() {
        map = new Mapgenerator(row, col);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        readFile();
    }

    static void readFile() {
        try {
            Scanner scan = new Scanner(scoreFile);
            while(scan.hasNext()) {
                String name = scan.next();
                int score = Integer.parseInt(scan.next());

                list.add(new ScoreList(name, score));
            }

            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int partition(List<ScoreList> array, int firstIndex, int lastIndex) {
        int pivot = array.get(lastIndex).getScore();    // int pivot = array[high];
        System.out.println("pivot: " + pivot);
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

    public static void write(List<ScoreList> list) {
        Collections.reverse(list);

        try {
            PrintWriter pw = new PrintWriter("BrickBreaker/src/HighScoreList.txt");

            for(ScoreList x : list) {
                System.out.println("Name: " + x.getName() + " Score: " + x.getScore());
                pw.println(x.getName() + " " + x.getScore());
            }

            pw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            System.out.println("an error occured.");
            e.printStackTrace();
        }
    }

    public String delString(String text) {
        String temp = "";
        temp = text.substring(0, text.length() - 1);

        return temp;
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
        g.fillOval(ballposX, ballposY, 15, 15);

        // GAME ENDS
        // WIN
        if(totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;

            for(int index = 0; index < map.map.length; index++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    map.setBrickValue(0, index, j);
                }
            }

            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won! Score: " + score, 150, 50);
            g.drawString("Enter Name: ", 150, 100);
            g.drawString(text, 400, 100);
            g.drawString("Name: ", 150, 200);
            g.drawString("Score: ", 400, 200);
            g.drawString("1: ", 50, 250);
            g.drawString("2: ", 50, 300);
            g.drawString("3: ", 50, 350);
            g.drawString("4: ", 50, 400);
            g.drawString("5: ", 50, 450);
            g.drawString("Press Enter to Play Again.", 150, 525);

            try {
                Scanner scan = new Scanner(scoreFile);
                int x = 100;
                int y = 250;
                int count = 0;

                while(scan.hasNext() && count < 5) {
                    String name = scan.next();
                    String userScore = scan.next();
                    g.drawString(name, x, y);
                    g.drawString(userScore, x + 300, y);
                    y += 50;
                    count++;
                }
                scan.close();
            } catch (Exception e) {
                System.out.println("an error occured");
                e.printStackTrace();
            } 
        }

        // LOSE
        if(ballposY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;

            for(int index = 0; index < map.map.length; index++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    map.setBrickValue(0, index, j);
                }
            }

            g.setColor(Color.white);
            g.drawString("Game Over, Score: " + score, 150, 50);
            g.drawString("Enter Name: ", 150, 100);
            g.drawString(text, 400, 100);

            g.drawString("Name: ", 150, 200);
            g.drawString("Score: ", 400, 200);
            g.drawString("1: ", 50, 250);
            g.drawString("2: ", 50, 300);
            g.drawString("3: ", 50, 350);
            g.drawString("4: ", 50, 400);
            g.drawString("5: ", 50, 450);
            g.drawString("Press Enter to Restart.", 150, 525);

            try {
                Scanner scanner = new Scanner(scoreFile);
                int x = 100;
                int y = 250;
                int count = 0;

                while(scanner.hasNext() && count < 5) {
                    String name = scanner.next();
                    String userScore = scanner.next();
                    g.drawString(name, x, y);
                    g.drawString(userScore, x + 300, y);
                    y += 50;
                    count++;
                }
                scanner.close();
            } catch (Exception e) {
                System.out.println("an error occured");
                e.printStackTrace();
            }   
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
            }

            if(playerX >= 600)
                playerX = 600;
            else
                moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            if(!play && (ballposY > 570 || totalBricks <= 0)){
                text += e.getKeyChar();
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
                    System.out.println("deletecount: " + deleteChar);
                    list.add(tempScore);
                    int lastIndex = list.size() - 1;
                    quickSort(list, 0, lastIndex);

                    text = "";
                    // WRITE TO FILE.
                    write(list);
                }
                play = true;
                ballposX = 330;
                ballposY = 530;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = row * col;
                col = random.nextInt(10) + 5;
                row = random.nextInt(8) + 3;
                points = 100 / (row * col);
                System.out.println("points: " + points);
                map = new Mapgenerator(row, col);

                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            System.out.println("Backspace pressed.");

            text = delString(text);
        }
        if(!play && (ballposY > 570 || totalBricks <= 0) && (e.getKeyCode() != KeyEvent.VK_BACK_SPACE)) {
            text += e.getKeyChar();
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
                            score += points;

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
