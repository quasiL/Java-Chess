package cz.cvut.fel.model;

import javax.management.remote.MBeanServerForwarder;

/**
 *
 * @author quasi
 */
public class Knight extends Piece {

    public Knight(int row, int col, boolean color) {
        super(row, col, color);
    }
    
    @Override
    public boolean move(int i, int j) {
                      
        int rowDelta = Math.abs(i - getRow());
        int colDelta = Math.abs(j - getCol());
        
        if ((rowDelta == 1) && (colDelta == 2)) {
            return true;
        }
        if ((rowDelta == 2) && (colDelta == 1)) {
            return true;
        }
        return false;
    }
}
