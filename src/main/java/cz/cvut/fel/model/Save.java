package cz.cvut.fel.model;

import java.util.ArrayList;

/**
 *
 * @author quasi
 */
public class Save {
    //\d\S\s[KQRNB]?[a-h][1-8]\s[KQRNB]?[a-h][1-8]
    private String moves;
    private int countMoves;

    public Save() {
        this.moves = "";
        this.countMoves = 1;
    }

    public String getMoves() {
        return moves;
    }
    
    public void saveMoves(Piece piece, int row, int col, ArrayList<Piece> pieces, boolean isCheck) {    
        String check = (isCheck) ? "+" : "";

        if (piece.getColor() == Constants.WHITE) {
            moves += countMoves 
                    + ". " 
                    + getName(piece) 
                    + isCapturing(row, col, pieces) 
                    + getCol(col) 
                    + getRow(row) 
                    + check
                    + " ";
        } else {
            moves += getName(piece) 
                    + isCapturing(row, col, pieces) 
                    + getCol(col) 
                    + getRow(row) 
                    + check
                    + " ";
            if (countMoves % 3 == 0) {
                moves += "\n";
            }                
            countMoves++;
        }                                    
    }
    
    public String getName(Piece piece) {
        return switch (piece.getClass().getName()) {
            case "cz.cvut.fel.model.Castle" -> "R";
            case "cz.cvut.fel.model.Bishop" -> "B";
            case "cz.cvut.fel.model.Knight" -> "N";
            case "cz.cvut.fel.model.Queen" -> "Q";
            case "cz.cvut.fel.model.King" -> "K";
            default -> "";
        };
    }
    
    public String getRow(int row) {
        return switch (row) {
            case 0 -> "1";
            case 1 -> "2";
            case 2 -> "3";
            case 3 -> "4";
            case 4 -> "5";
            case 5 -> "6";
            case 6 -> "7";
            case 7 -> "8";
            default -> "";
        };        
    }
    
    public String getCol(int col) {
        return switch (col) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "";
        };        
    }
    
    public String isCapturing(int row, int col, ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            if ((p.getRow() == row) && (p.getCol() == col)) {
                return "x";
            } 
        }
        return "";
    }
    
    public void endOfTheGame() {
        moves = moves.substring(0, moves.length() - 1);
        moves += "# 1-0";
    }
}
