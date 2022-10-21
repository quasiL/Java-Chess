package cz.cvut.fel.model;

import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class Queen extends Piece {

    public Queen(int row, int col, boolean color) {
        super(row, col, color);
    }
    
    @Override
    public boolean move(int i, int j) {
        int rowDelta = Math.abs(getRow() - i);
        int colDelta = Math.abs(getCol() - j);
        if (rowDelta >= 1 && colDelta >= 1 && rowDelta == colDelta) {
            return true;
        }
        if (getRow() == i || getCol() == j) {
            return true;
        }
        return false;
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