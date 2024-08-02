package chess.game.resources;

import javax.swing.JLabel;

public class Knight extends Piece {
  
  public Knight(int x, int y, boolean color, JLabel label, String name) {
    super(x, y, color, label, name);
  }

  public boolean isValidMove(int x, int y, Piece p) {
    if((Math.abs(this.y - y) == 2) && (Math.abs(this.x - x) == 1)) {
      return true;
    }
    if((Math.abs(this.y - y) == 1) && (Math.abs(this.x - x) == 2)) {
      return true;
    }

    return false;
  }
}
