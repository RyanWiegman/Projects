import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;
import java.awt.BasicStroke;

public class Mapgenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public Random rand = new Random();
    
    public Mapgenerator(int row, int col) {
        map = new int[row][col];

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = (700 - 2*80) / col;
        brickHeight = 100 / row;
    }

    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
