package cz.cvut.fel.model;

import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class Castle extends Piece {
    
    private boolean wasMoving = false;

    public Castle(int row, int col, boolean color) {
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
        return getRow() == i || getCol() == j;       
    }
    
    @Override
    public boolean checkPath(int i, int j, ArrayList<Piece> pieces) {
        
        for (Piece p : pieces) {
            if (p.getRow() == i && p.getCol() == j && p.getColor() == getColor()) {
                return false;
            }
        }        
        
        if (getRow() == i) {
            if (j > getCol()) {
                for (int k = getCol()+1; k < j; k++) {
                    for (Piece p : pieces) {
                        if (p.getRow() == i && p.getCol() == k) {
                            return false;
                        }
                    }                     
                }
            } else {
                for (int k = getCol()-1; k > j; k--) {
                    for (Piece p : pieces) {
                        if (p.getRow() == i && p.getCol() == k) {
                            return false;
                        }
                    }                     
                }                
            }         
        }
        if (getCol() == j) {
            if (i > getRow()) {
                for (int k = getRow()+1; k < i; k++) {
                    for (Piece p : pieces) {
                        if (p.getCol() == j && p.getRow() == k) {
                            return false;
                        }
                    }                     
                }
            } else {
                for (int k = getRow()-1; k > i; k--) {
                    for (Piece p : pieces) {
                        if (p.getCol() == j && p.getRow() == k) {
                            return false;
                        }
                    }                     
                }                
            }           
        }
        return true;
    }
}