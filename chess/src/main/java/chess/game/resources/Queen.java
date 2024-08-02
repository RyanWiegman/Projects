package chess.game.resources;

import javax.swing.JLabel;

public class Queen extends Piece {
  
  public Queen(int x, int y, Boolean color, JLabel label, String name) {
    super(x, y, color, label, name);
  }

  public boolean isValidMove(int x, int y, Piece p) {
    if((Math.abs(this.x - x) > 0) && (Math.abs(this.y - y) > 0)) {
      return true;
    }

    return false;
  }
}
