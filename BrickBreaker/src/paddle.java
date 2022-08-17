import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class paddle extends Sprite{
    Commons commons = new Commons();
    private int dx;

    public paddle() {
        initPaddle();
    }

    private void initPaddle() {
        loadImage();
        getImageDimensions();

        resetState();
    }

    private void loadImage() {
        var ii = new ImageIcon("src/resources/paddle.png");
        image = ii.getImage();
    }

    public void move() {
        x += dx;

        if(x <= 0)
            x = 0;
        if(x >= commons.WIDTH - imageWidth)
            x = commons.WIDTH - imageWidth;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT)
            dx = -1;
        if(key == KeyEvent.VK_RIGHT)
            dx = 1;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT)
            dx = 0;
        if(key == KeyEvent.VK_RIGHT)
            dx = 0;
    }

    private void resetState() {
        x = commons.INIT_PADDLE_X;
        y = commons.INIT_PADDLE_Y;
    }
}
