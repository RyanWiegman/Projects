package chess.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import chess.game.resources.Board;
import chess.game.resources.Piece;

public class GamePlay extends Board {
  protected Piece selectedPiece;
  protected Piece pieceFound;
  protected boolean isPieceSelected = false;
  protected boolean checkmate = false;

  protected Map<String, Boolean> gameState = Map.ofEntries(
    Map.entry("playing", true),
    Map.entry("checkmate", false)
  );

  public GamePlay() {
    System.out.println("Initializing Gameplay...");
    gameBoard.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent e) {
        x = e.getX() / 75;
        y = e.getY() / 75;
        // System.out.println("mouse click X: " + x + " Y: " + y);
        if(!isPieceSelected) {
          getSelectedPiece();
        }
        else {
          moveSelectedPiece();
          drawBoard();
        }
        
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
      }
      
    });
  }

  public void getSelectedPiece() {
    for(Piece p : pieces) {
      if(x == p.getX() && y == p.getY() && p.isAlive()) {
        System.out.println(p.getName() + " selected");
        piecesBoard[x][y] = null;
        selectedPiece = p;
        isPieceSelected = true;
      }
    }

    if(selectedPiece == null) {
      System.out.println("no piece selected...");
    }
  }

  public Piece findPiece() {
    for(Piece p : pieces) {
      if(p.isAlive() && (x == p.getX() && y == p.getY())) {
        pieceFound = p;
        return pieceFound; 
      }
    }

    return null;
  }

  public void moveSelectedPiece() {
    Piece p = findPiece();
    
    if(selectedPiece != null && selectedPiece.isValidMove(x, y, p)) {
      if(p == null) {
        selectedPiece.move(x, y);
        System.out.println(selectedPiece.getName() + " moved...");
      }
      else if(p.isWhitePiece() == selectedPiece.isWhitePiece()) {
        System.out.println("cant take own piece...");
        selectedPiece = null;
        isPieceSelected = false;
        return;
      }
      else {
        p.setAlive(false);
        piecesBoard[x][y] = null;
        selectedPiece.move(x, y);
        System.out.println(p.getName() + " taken...");
      }

      selectedPiece = null;
      isPieceSelected = false;
    }
    else {
      selectedPiece = null;
      isPieceSelected = false;
      System.out.println("invalid move");
    }
  }
}
