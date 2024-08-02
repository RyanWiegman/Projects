package chess.game.resources;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;

public class Board extends JPanel{
    protected JFrame gameBoard = new JFrame("Chess");
    protected JPanel[][] squares = new JPanel[8][8];
    @Getter @Setter protected Piece[][] piecesBoard = new Piece[8][8];
    protected ArrayList<Piece> pieces = new ArrayList<>();
    @Getter @Setter protected int x;
    @Getter @Setter protected int y;

    
    public Board() {
        System.out.println("Board initilized...");
        createBackround();
        initializePieces();
        drawBoard();
        gameBoard.setSize(600, 600);
        gameBoard.setLayout(new GridLayout(8, 8));
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setVisible(true);
    }

    protected void createBackround() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                squares[i][j] = new JPanel();

                if((i + j) % 2 == 0) {
                    squares[i][j].setBackground(Color.black);
                }
                else {
                    squares[i][j].setBackground(Color.white);
                }

                gameBoard.add(squares[i][j]);
            }
        }
    }

    protected void initializePieces() {
        pieces.add(new Rook(0 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackRook.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "ROOK"));
        pieces.add(new Knight(1 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackKnight.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "KNIGHT"));
        pieces.add(new Bishop(2 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackBishop.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "BISHOP"));
        pieces.add(new Queen(3 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackQueen.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "QUEEN"));
        pieces.add(new King(4 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackKing.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "KING"));
        pieces.add(new Bishop(5 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackBishop.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "BISHOP"));
        pieces.add(new Knight(6 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackKnight.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "KNIGHT"));
        pieces.add(new Rook(7 , 0, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackRook.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "ROOK"));
        
        pieces.add(new Rook(0 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteRook.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "ROOK"));
        pieces.add(new Knight(1 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteKnight.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "KNIGHT"));
        pieces.add(new Bishop(2 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteBishop.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "BISHOP"));
        pieces.add(new Queen(3 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteQueen.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "QUEEN"));
        pieces.add(new King(4 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteKing.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "KING"));
        pieces.add(new Bishop(5 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteBishop.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "BISHOP"));
        pieces.add(new Knight(6 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteKnight.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "KNIGHT"));
        pieces.add(new Rook(7 , 7, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhiteRook.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "ROOK"));
        
        for(int i = 0; i < 8; i++) {
            pieces.add(new Pawn(i , 1, false, new JLabel(new ImageIcon(new ImageIcon("Images/BlackPawn.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "PAWN"));
            pieces.add(new Pawn(i , 6, true, new JLabel(new ImageIcon(new ImageIcon("Images/WhitePawn.png").getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH))), "PAWN"));
        }

        // pieces.add(new Piece(0 , 0, false, new JLabel("ROOK")));
        // pieces.add(new Piece(1 , 0, false, new JLabel("KNIT")));
        // pieces.add(new Piece(2 , 0, false, new JLabel("BISH")));
        // pieces.add(new Piece(3 , 0, false, new JLabel("QUEN")));
        // pieces.add(new Piece(4 , 0, false, new JLabel("KING")));
        // pieces.add(new Piece(5 , 0, false, new JLabel("BISH")));
        // pieces.add(new Piece(6 , 0, false, new JLabel("KNIT")));
        // pieces.add(new Piece(7 , 0, false, new JLabel("ROOK")));
        
        // pieces.add(new Piece(0 , 7, true, new JLabel("ROOK")));
        // pieces.add(new Piece(1 , 7, true, new JLabel("KNIT")));
        // pieces.add(new Piece(2 , 7, true, new JLabel("BISH")));
        // pieces.add(new Piece(3 , 7, true, new JLabel("QUEN")));
        // pieces.add(new Piece(4 , 7, true, new JLabel("KING")));
        // pieces.add(new Piece(5 , 7, true, new JLabel("BISH")));
        // pieces.add(new Piece(6 , 7, true, new JLabel("KNIT")));
        // pieces.add(new Piece(7 , 7, true, new JLabel("ROOK")));
      
        // for(int i = 0; i < 8; i++) {
        //     pieces.add(new Pawn(i , 1, false, new JLabel("pawn")));
        //     pieces.add(new Pawn(i , 6, true, new JLabel("pawn")));
        // }
     
    }

    protected void drawBoard() {
        System.out.println("drawing board...");
        System.out.println("*********************");
        
        // PRINT BOARD TO CONSOLE 
        // for(Piece p : pieces) {
        //     if(p.isAlive()) {
        //         // System.out.println("pieces location: " + p.getX() + ", " + p.getY() + " is alive: " + p.isAlive());
        //         piecesBoard[p.getX()][p.getY()] = p;
        //     }
        //     else {
        //         System.out.println(p.getPieceName().getText() + " is dead...");
        //     }
        // }

        // for (int i = 0; i < 8; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         if(piecesBoard[j][i] != null) {
        //             System.out.print("[" + piecesBoard[j][i].getPieceName().getText() + "]"); 
        //         }
        //         else {
        //             System.out.print("[null]");
        //         }
        //     }
        //     System.out.println();
        // }

        // DRAWING FOR JFRAME/JLABEL
        for(Piece p : pieces) {
            if(p.isAlive()) {
                squares[p.getY()][p.getX()].add(p.getLabelName());
                // System.out.println("panel: " + squares[p.getY()][p.getX()].getName());
            }
            else {
                squares[p.getY()][p.getX()].remove(p.getLabelName());
            }
        }

        gameBoard.revalidate();
        gameBoard.repaint();
    }
}
