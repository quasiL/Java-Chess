package cz.cvut.fel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quasi
 */
public class GameLogic implements Serializable {
    
    private int row;
    private int col;
    private boolean moveProviding = false;
    private boolean side = Constants.WHITE; 
    //private Logger log = Logger.getLogger(GameLogic.class.getName());
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isMoveProviding() {
        return moveProviding;
    }

    public void setMoveProviding(boolean moveProviding) {
        this.moveProviding = moveProviding;
    }

    public boolean getSide() {
        return side;
    }

    public void setSide(boolean side) {
        this.side = side;
    }
    
    public boolean isValidMove(int i, int j, Piece piece, ArrayList<Piece> pieces) {              
        
        boolean result = piece.move(i, j) && piece.checkPath(i, j, pieces);
        
        if (piece instanceof Pawn && Math.abs(piece.getCol() - i) == 1) {
            for (Piece p : pieces) {
                if (p.getRow() == piece.getRow() && Math.abs(piece.getCol() - p.getCol()) == 1 && p.isEnPassant()) {                    
                    pieces.remove(p);
                    //log.log(Level.INFO, "Piece was removed from the board (en passant)");                    
                    return true;
                }
            }            
        } else {
            for (Piece p : pieces) {
                if (p instanceof Pawn) {
                    p.setEnPassant(false);
                }
            }             
        }
        
        if (piece instanceof Pawn) {
            for (Piece p : pieces) {
                if (p.getRow() == i && p.getCol() == j && result) {
                    if (piece.getCol() == p.getCol()) {
                        return false;
                    }
                }
            }            
        }
        
        if (piece instanceof Pawn && Math.abs(piece.getRow() - i) == 2 && result) {
            piece.setEnPassant(true);
        }
               
        return result;
    }
    
    public Piece checkToTheKing(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            if (p instanceof King) {
                for (Piece f : pieces) {
                    if (f.getColor() != p.getColor() && f.move(p.getRow(), p.getCol()) && f.checkPath(p.getRow(), p.getCol(), pieces)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }
    
    public Piece whoIsAThreatForTheKing(ArrayList<Piece> pieces, Piece king) {
        for (Piece p : pieces) {
            if (p.getColor() != king.getColor()) {
                if (isValidMove(king.getRow(), king.getCol(), p, pieces)) {
                    return p;
                }
            }
        }
        return null;        
    }
    
    public boolean canSomeoneSaveTheKing(ArrayList<Piece> pieces, Piece threat) {
        for (Piece p : pieces) {
            if (p.getColor() != threat.getColor() && p instanceof King == false) {
                if (isValidMove(threat.getRow(), threat.getCol(), p, pieces)) {
                    return true;
                }              
            }
        }
        return false;
    }
}