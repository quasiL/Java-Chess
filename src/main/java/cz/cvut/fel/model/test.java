package cz.cvut.fel.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class test implements Serializable {
    
    private final ArrayList<Piece> pieces;

    public test(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }
    
    
}
