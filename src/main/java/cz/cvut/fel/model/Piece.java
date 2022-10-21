package cz.cvut.fel.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author quasi
 */
public abstract class Piece implements Serializable {
    
    private int row;
    private int col;
    private final boolean color;
    private boolean enPassant = false;

    public Piece(int row, int col, boolean color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

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

    public boolean getColor() {
        return color;
    }
    
    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }    

    public boolean move(int i, int j) {
        return true;
    }
    
    public boolean checkPath(int i, int j, ArrayList<Piece> pieces) {     
        for (Piece p : pieces) {
            if (p.getRow() == i && p.getCol() == j && p.getColor() == getColor()) {
                return false;
            }
        }        
        return true;
    }

    public ImageIcon getIcon() {
        if (this instanceof Pawn && color == Constants.BLACK) return Constants.BLACK_PAWN;
        if (this instanceof Pawn && color == Constants.WHITE) return Constants.WHITE_PAWN;
        if (this instanceof Castle && color == Constants.WHITE) return Constants.WHITE_CASTLE;
        if (this instanceof Castle && color == Constants.BLACK) return Constants.BLACK_CASTLE;
        if (this instanceof Knight && color == Constants.BLACK) return Constants.BLACK_KNIGHT;
        if (this instanceof Knight && color == Constants.WHITE) return Constants.WHITE_KNIGHT;
        if (this instanceof Bishop && color == Constants.WHITE) return Constants.WHITE_BISHOP;
        if (this instanceof Bishop && color == Constants.BLACK) return Constants.BLACK_BISHOP;
        if (this instanceof Queen && color == Constants.BLACK) return Constants.BLACK_QUEEN;
        if (this instanceof Queen && color == Constants.WHITE) return Constants.WHITE_QUEEN;
        if (this instanceof King && color == Constants.WHITE) return Constants.WHITE_KING;
        if (this instanceof King && color == Constants.BLACK) return Constants.BLACK_KING;
        return null;
    }
}
