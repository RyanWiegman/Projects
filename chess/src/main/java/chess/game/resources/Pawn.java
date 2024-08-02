package chess.game.resources;

import javax.swing.JLabel;

public class Pawn extends Piece {
  protected boolean firstTurn;

  public Pawn(int x, int y, boolean color, JLabel label, String name) {
    super(x, y, color, label, name);
    firstTurn = true;
  }

  @Override
  public boolean isValidMove(int x, int y, Piece p) {
    if(firstTurn && (Math.abs(this.y - y) == 2) && (this.x - x == 0)) {
      firstTurn = false;
      return true;
    }
    else if((p != null) && (Math.abs(this.x - x) == 1) && (Math.abs(this.y - y) == 1)) {
      firstTurn = false;
      return true;
    }
    else if ((Math.abs(this.y - y) == 1) && (this.x - x == 0) && (p == null)) {
      firstTurn = false;
      return true;
    }

    return false;
  }
  
} 
// TODO - IMPL NOT MOVING BACKWARDS FOR PAWNS
// TODO - IMPL NOT JUMP OVER PIECES