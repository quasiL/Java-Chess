package cz.cvut.fel.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author quasi
 */
public class Bot {
    
    private int row;
    private int col;
    private final boolean side;
    private int count;
    private Random rand;

    public Bot(boolean side) {
        this.side = side;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
     
    public Piece selectPiece(ArrayList<Piece> pieces) {      
        rand = new Random();
        Piece piece = pieces.get(rand.nextInt(pieces.size()));
        while(piece.getColor() != side) {
            piece = pieces.get(rand.nextInt(pieces.size()));
        } 
        return piece;               
    }
    
    public int countPossibleMoves(int[][] possibleMoves) {        
        count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possibleMoves[i][j] == 1) {
                    count++;
                }
            }
        }      
        
        return count;       
    }
    
    public void selectMove(Piece piece, int count, int[][] possibleMoves) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, count);
        int attemptCount = 0;   
        
        System.out.println("count= "+count);
        System.out.println("random= "+randomNum);
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possibleMoves[i][j] == 1) {                        
                    if (attemptCount == randomNum) {
                        row = i;
                        col = j;                 
                    }
                    attemptCount++;                        
                }
            }
        }        
    }
}
