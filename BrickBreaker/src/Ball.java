import javax.swing.ImageIcon;

public class Ball extends Sprite{
    private int xdir;
    private int ydir;

    Commons commons = new Commons();

    public Ball() {
        initBall();
    }

    private void initBall() {
        xdir = 1;
        ydir = -1;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {
        var ii = new ImageIcon("src/resources/ball.png");
        image = ii.getImage(); 
    }

    public void move() {
        x += xdir;
        y += ydir;

        if(x == 0)
            setXDir(1);
        if(x == commons.WIDTH - imageWidth) {
            System.out.println(imageWidth);
            setXDir(-1);
        }
        if(y == 0)
            setYDir(1);
    }

    private void resetState() {
        x = commons.INIT_BALL_X;
        y = commons.INIT_BALL_Y;
    }

    public void setXDir(int x) {
        xdir = x;
    }

    public int getXDir() {
        return xdir;
    }

    public void setYDir(int y) {
        ydir = y;
    }
    
    public int getYDir() {
        return ydir;
    }
}
