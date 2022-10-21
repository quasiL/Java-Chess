package cz.cvut.fel.model;

import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class Bishop extends Piece {

    public Bishop(int row, int col, boolean color) {
        super(row, col, color);
    }
    
    @Override
    public boolean move(int i, int j) {
        int rowDelta = Math.abs(getRow() - i);
        int colDelta = Math.abs(getCol() - j);
        return rowDelta >= 1 && colDelta >= 1 && rowDelta == colDelta;
    }
    
    @Override
    public boolean checkPath(int i, int j, ArrayList<Piece> pieces) {
        
        for (Piece p : pieces) {
            if (p.getRow() == i && p.getCol() == j && p.getColor() == getColor()) {
                return false;
            }
        }        
        
        if (i > getRow() && j > getCol()) {
            for (int k=1; k < i-getRow(); k++) {
                for (Piece p : pieces) {
                    if (p.getRow() == getRow()+k && p.getCol() == getCol()+k) {
                        return false;
                    }
                }                 
            }
        }
        if (i > getRow() && j < getCol()) {
            for (int k=1; k < i-getRow(); k++) {
                for (Piece p : pieces) {
                    if (p.getRow() == getRow()+k && p.getCol() == getCol()-k) {
                        return false;
                    }              
                }                
            }
        }
        if (i < getRow() && j > getCol()) {
            for (int k=1; k < getRow()-i; k++) {
                for (Piece p : pieces) {
                    if (p.getRow() == getRow()-k && p.getCol() == getCol()+k) {
                        return false;
                    }              
                }                
            }
        }
        if (i < getRow() && j < getCol()) {
            for (int k=1; k < getRow()-i; k++) {
                for (Piece p : pieces) {
                    if (p.getRow() == getRow()-k && p.getCol() == getCol()-k) {
                        return false;
                    }              
                }                
            }
        }
        return true;
    }
}
