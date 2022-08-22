import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Timer;
import java.awt.Rectangle;

import javax.swing.JPanel;

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

    public Gameplay() {
        map = new Mapgenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
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
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // THE PADDLE
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // THE BALL
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart.", 230, 350);
        }

        if(ballposY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart.", 220, 350);
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
            if(playerX >= 600)
                playerX = 600;
            else
                moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            if(playerX < 10)
                playerX = 10;
            else
                moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
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
