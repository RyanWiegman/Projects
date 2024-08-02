package chess.game.resources;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
public class Piece extends JPanel{
  @Getter @Setter protected int x;
  @Getter @Setter protected int y;
  @Getter @Setter protected boolean whitePiece = false;
  @Getter @Setter private JLabel labelName;
  @Getter @Setter private String name;
  @Getter @Setter protected boolean isAlive;

  public Piece(int x, int y, boolean color, JLabel label, String name) {
    this.x = x;
    this.y = y;
    this.whitePiece = color;
    this.labelName = label;
    this.name = name;
    this.isAlive = true;
  }

  public boolean isValidMove(int x, int y, Piece p) {
    return false;
  }

  public void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void killed(boolean killed) {
    this.isAlive = killed;
  }
}