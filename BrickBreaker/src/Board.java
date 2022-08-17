import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel{
    private Timer timer;
    private String message = "Game Over";
    private Ball ball;
    private paddle paddle;
    private Brick[] bricks;
    private boolean inGame = true;
    Commons commons = new Commons();

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(commons.WIDTH, commons.HEIGHT));

        gameInit();
    }

    private void gameinit() {
        bricks = new Brick[commons.N_OF_BRICKS];
        ball = new Ball();
        paddle = new paddle();

        int k = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++){
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }

        timer = new Timer(commons.PERIOD, new GameCycle());
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if(inGame)
            drawObjects(g2d);
        else
            gameFinished(g2d);

        Toolkit.getDefaultToolKit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getImageWidth(), paddle.getImageHeight(), this);

        for(int index = 0; index < commons.N_OF_BRICKS; index++) {
            if(!bricks[index].isDestroyed()) {
                g2d.drawImage(bricks[index].getImage(), bricks[index].getX(), bricks[index].getY(), bricks[index].getImageWidth(), bricks[index].getImageHeight(), this);
            }
        }
    }

    private void gameFinished(Graphics2D g2d) {
        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message, (commons.WIDTH - fontMetrics.stringWidth(message)) / 2, commons.WIDTH / 2);

    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private void doGameCycle() {
        ball.move();
        paddle.move();
        checkCollision();
        repaint();
    }

    private void stopGame() {
        inGame = false;
        timer.stop();
    }

    private void checkCollision() {
        if(ball.getRect().getMaxY() > commons.BOTTOM_EDGE)
            stopGame();
        for(int i = 0, j = 0; i < commons.N_OF_BRICKS; i++){
            if(bricks[i].isDestroyed())
                j++;
            if(j == commons.N_OF_BRICKS) {
                message = "Victory";
                stopGame();
            }
        }

        if((ball.getRect().intersects(paddle.getRect()))) {
            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();
            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos  + 24;
            int fourth = paddleLPos + 32;

            if(ballLPos < first) {
                ball.setXDir(-1);
                ball.setYDir(-1);
            }
            if(ballLPos >= first && ballLPos < second) {
                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }
            if(ballLPos >= second && ballLPos < third) {
                ball.setXDir(0);
                ball.setYDir(-1);
            }
            if(ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }
            if(ballLPos > fourth) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }

        for(int index = 0; index < commons.N_OF_BRICKS; index++) {
            if((ball.getRect()).intersects(bricks[index].getRect())) {
                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if(!bricks[index].isDestroyed()) {
                    if(bricks[index].getRect().contains(pointRight))
                        ball.setXDir(-1);
                    else if(bricks[index].getRect().contains(pointLeft))
                        ball.setXDir(1);
                    if(bricks[index].getRect().contains(pointTop))
                        ball.setYDir(1);
                    else if(bricks[index].getRect().contains(pointBottom))
                        ball.setYDir(-1);
                    
                    bricks[index].setDestroyed(true);
                }
            }
        }
    }
}
