package cz.cvut.fel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quasi
 */
public class GameField implements Serializable {
    
    private final ArrayList<Piece> pieces;
    //private final Logger log = Logger.getLogger(GameLogic.class.getName());

    public GameField() {
        this.pieces = new ArrayList<>();
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    
    public void addPiecesOnTheField() {
        
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn(1, i, Constants.BLACK));
            pieces.add(new Pawn(6, i, Constants.WHITE));
        }
        
        pieces.add(new Castle(0, 0, Constants.BLACK));
        pieces.add(new Castle(0, 7, Constants.BLACK));
        pieces.add(new Castle(7, 0, Constants.WHITE));
        pieces.add(new Castle(7, 7, Constants.WHITE));
        
        pieces.add(new Knight(0, 1, Constants.BLACK));
        pieces.add(new Knight(0, 6, Constants.BLACK));
        pieces.add(new Knight(7, 1, Constants.WHITE));
        pieces.add(new Knight(7, 6, Constants.WHITE));
        
        pieces.add(new Bishop(0, 2, Constants.BLACK));
        pieces.add(new Bishop(0, 5, Constants.BLACK));
        pieces.add(new Bishop(7, 2, Constants.WHITE));
        pieces.add(new Bishop(7, 5, Constants.WHITE));
        
        pieces.add(new Queen(0, 3, Constants.BLACK));
        pieces.add(new Queen(7, 3, Constants.WHITE));
        
        pieces.add(new King(0, 4, Constants.BLACK));
        pieces.add(new King(7, 4, Constants.WHITE));
    }
    
    public Piece getPieceByCoordinates(int i, int j) {
        for (Piece p : pieces) {
            if ((p.getRow() == i) && (p.getCol() == j)) {
                return p;
            }
        }
        return null;
    }
    
    public void removePieceFromPieces(int i, int j) throws Exception {
        Piece removedPiece = getPieceByCoordinates(i, j);
        if (removedPiece instanceof King) {
            throw new Exception("King cannot be removed!");
        }
        //log.log(Level.INFO, "Piece was removed from the board");
        pieces.remove(removedPiece);
    }
    
    public int[][] getAllPossibleMoves(Piece piece) {
        int possibleMoves[][] = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece.move(i, j) && piece.checkPath(i, j, pieces)) {
                    possibleMoves[i][j] = 1;
                }
            }
        }
        return possibleMoves;
    }
    
    public boolean canKingMoveSomewhere(Piece king) {
        int possibleKingMoves[][] = getAllPossibleMoves(king);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possibleKingMoves[i][j] == 1) {
                    return true;
                }
            }
        } 
        return false;
    }
}