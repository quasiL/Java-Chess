package cz.cvut.fel.model;

import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class Pawn extends Piece {

    public Pawn(int row, int col, boolean color) {
        super(row, col, color);
    }
    
    @Override
    public boolean move(int i, int j) {
        if (this.getColor() == Constants.WHITE) {
            if (getRow() - i == 1 && (Math.abs(getCol() - j) == 1 
                    || Math.abs(getCol() - j) == 0)) {
                return true;
            }
            if (getRow() - i == 2 && getRow() == 6 
                    && Math.abs(getCol() - j) == 0) {
                return true;
            }
        }
        if (this.getColor() == Constants.BLACK) {
            if (i - getRow() == 1 && (Math.abs(getCol() - j) == 1 
                    || Math.abs(getCol() - j) == 0)) {
                return true;
            }
            if (i - getRow() == 2 && getRow() == 1 
                    && Math.abs(getCol() - j) == 0) {
                return true;
            }
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
        
        for (Piece p : pieces) {
            if (p.getRow() == i && p.getCol() == j && getCol() == j) {
                return false;
            }
        }

        if (Math.abs(getCol() - j) == 1) {
            for (Piece p : pieces) {
                if (p.getRow() == i && p.getCol() == j) {
                    return true;
                }
            }
            for (Piece p : pieces) {
                if (p.getRow() == i && Math.abs(getCol() - p.getCol()) == 1 && p.isEnPassant()) {
                    return true;
                }
            }
            return false;
        }        

        return true;
    }
}