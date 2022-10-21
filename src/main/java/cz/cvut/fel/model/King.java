package cz.cvut.fel.model;

import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class King extends Piece {
    
    private boolean wasMoving = false;

    public King(int row, int col, boolean color) {
        super(row, col, color);
    }

    public boolean isWasMoving() {
        return wasMoving;
    }

    public void setWasMoving(boolean wasMoving) {
        this.wasMoving = wasMoving;
    }
    
    @Override
    public boolean move(int i, int j) {
        if ((getRow() == i && Math.abs(getCol() - j) == 1) 
                || (getCol() == j && Math.abs(getRow() - i) == 1)) {
            return true;
        }
        if (Math.abs(getRow() - i) == 1 && Math.abs(getCol() - j) == 1) {
            return true;
        }
        // Castling
        if (this.getColor() == Constants.WHITE && !wasMoving 
                && i == 7 && (j == 2 || j == 6)) {
            return true;
        }
        if (this.getColor() == Constants.BLACK && !wasMoving 
                && i == 0 && (j == 2 || j == 6)) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean checkPath(int i, int j, ArrayList<Piece> pieces) {
        
        Piece target = null;       
        for (Piece p : pieces) {
            if (p.getRow() == i && p.getCol() == j && p.getColor() == getColor()) {
                return false;
            }
            if (p.getRow() == i && p.getCol() == j) {
                target = p;
            }
        }        
         
        for (Piece p : pieces) {
            if (p.getColor() != getColor() && (p instanceof King == false)) {
                if (p.move(i, j) && p.checkPath(i, j, pieces)) {
                    return false;
                }
                if (p instanceof Pawn && p.move(i, j) && p.getCol() != j) {
                    return false;
                }                
            }
        }
        
        if (target != null) {
            pieces.remove(target);
            for (Piece p : pieces) {
                if (p.move(i, j) && p.checkPath(i, j, pieces) && p.getColor() == getColor()) {
                    pieces.add(target);
                    return false;
                }                
            }
            pieces.add(target);
        }
        
        for (Piece p : pieces) {
            if (p.move(getRow(), getCol()) && p.checkPath(getRow(), getCol(), pieces) && p.getColor() != getColor() && i == p.getRow() && j == p.getCol()) {
                return false;
            }
        }         
        
        return true;
    }
}
