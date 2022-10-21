package cz.cvut.fel.chessgame;

import cz.cvut.fel.model.*;
import cz.cvut.fel.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quasi
 */
public class ChessGame implements Serializable{

    private GameLogic gameLogic;
    private GameField gameField;
    private final GameFrame gameFrame;
    private final ButtonHandler buttonHandler;
    private final Logger log;
    private boolean gameStarted = false;
    private boolean gameVsBot = false;
    private Piece changedPawn;
    private Bot bot;
    private StatisticFrame statistic;
    private Save save;
    private SaveFrame saveDialog;
    private SaveInPgnFrame savePgn;
    private ListOfSavesFrame listOfSaves;
    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public static void main(String[] args) {
        ChessGame game = new ChessGame();           
    }

    public ChessGame() {                
        buttonHandler = new ButtonHandler();
        gameFrame = new GameFrame(buttonHandler);
        gameFrame.createGameField(buttonHandler);
        log = Logger.getLogger(ChessGame.class.getName());   
    }
    
    private void processClick(int i, int j) {
                
        Piece checkToTheKing = gameLogic.checkToTheKing(gameField.getPieces());             
        
        if (checkToTheKing != null) {
            gameFrame.setInfo("  Check to the King!");
            Piece threat = gameLogic.whoIsAThreatForTheKing(gameField.getPieces(), checkToTheKing);
            boolean firstCondition = gameField.canKingMoveSomewhere(checkToTheKing);
            boolean secondCondition = gameLogic.canSomeoneSaveTheKing(gameField.getPieces(), threat);
            if (!firstCondition && !secondCondition) {
                gameFrame.setInfo("  Checkmate to the King!");
                save.endOfTheGame();
                statistic.setTextArea(save.getMoves());
                gameFrame.showWinningMessage();
                gameStarted = false;
            }
        }

        gameFrame.setInfo("");
        gameFrame.unsetHints();
        Piece piece = gameField.getPieceByCoordinates(i, j);
        
        if (piece != null && piece.getColor() == gameLogic.getSide()) {
            gameLogic.setRow(i);
            gameLogic.setCol(j);
            gameLogic.setMoveProviding(true);
            log.log(Level.INFO, "Piece was selected");            
            gameFrame.showHints(gameField.getAllPossibleMoves(piece));
        } else {
            if (!gameLogic.isMoveProviding()) return;            
                        
            piece = gameField.getPieceByCoordinates(gameLogic.getRow(), gameLogic.getCol());
            if (gameLogic.isValidMove(i, j, piece, gameField.getPieces()) != false) { 
                
                save.saveMoves(piece, i, j, gameField.getPieces(), checkToTheKing != null);
                statistic.setTextArea(save.getMoves());                 
                               
                if (gameField.getPieceByCoordinates(i, j) != null) {      
                    statistic.addRemovedPieceToThePanel(gameField.getPieceByCoordinates(i, j).getIcon());
                    try {                       
                        gameField.removePieceFromPieces(i, j);                      
                    } catch (Exception e) {                        
                        log.log(Level.SEVERE, "King cannot be removed!");
                    }             
                }                
                int tmpRow = piece.getRow();
                int tmpCol = piece.getCol();
                piece.setRow(i);
                piece.setCol(j);
                
                checkToTheKing = gameLogic.checkToTheKing(gameField.getPieces());
                                              
                if (checkToTheKing != null && checkToTheKing.getColor() == piece.getColor()) {
                    piece.setRow(tmpRow);
                    piece.setCol(tmpCol);
                    gameFrame.setInfo("  Check to the King!");
                    return;
                }                                        
                
                //Replacing
                if ((piece.getColor() == Constants.BLACK && piece.getRow() == 7 && piece instanceof Pawn) 
                        || (piece.getColor() == Constants.WHITE && piece.getRow() == 0 && piece instanceof Pawn)) {
                    gameFrame.showSelectPieceWindow(buttonHandler);
                    changedPawn = piece;
                }
                
                //Castling 
                if (piece instanceof King && Math.abs(piece.getCol() - j) != 1 && !((King) piece).isWasMoving()) {
                    if (piece.getColor() == Constants.WHITE) {

                        if (j == 6 && gameField.getPieceByCoordinates(i, 5) == null) {
                            Piece castle = gameField.getPieceByCoordinates(7, 7);
                            if (castle instanceof Castle) {
                                if (!((Castle) castle).isWasMoving()) {
                                    castle.setRow(7);
                                    castle.setCol(5);
                                } else {
                                    piece.setRow(7);
                                    piece.setCol(4);
                                    return;
                                }
                            }
                        }
                        else if (j == 2 && gameField.getPieceByCoordinates(i, 1) == null && gameField.getPieceByCoordinates(i, 3) == null) {
                            Piece castle = gameField.getPieceByCoordinates(7, 0);
                            if (castle instanceof Castle) {
                                if (!((Castle) castle).isWasMoving()) {
                                    castle.setRow(7);
                                    castle.setCol(3);
                                } else {
                                    piece.setRow(7);
                                    piece.setCol(4);
                                    return;
                                }
                            }                            
                        }
                    } else {
                        if (j == 6 && gameField.getPieceByCoordinates(i, 5) == null) {
                            Piece castle = gameField.getPieceByCoordinates(0, 7);
                            if (castle instanceof Castle) {
                                if (!((Castle) castle).isWasMoving()) {
                                    castle.setRow(0);
                                    castle.setCol(5);
                                } else {
                                    piece.setRow(0);
                                    piece.setCol(4);
                                    return;
                                }
                            }
                        }
                        else if (j == 2 && gameField.getPieceByCoordinates(i, 1) == null && gameField.getPieceByCoordinates(i, 3) == null) {
                            Piece castle = gameField.getPieceByCoordinates(0, 0);
                            if (castle instanceof Castle) {
                                if (!((Castle) castle).isWasMoving()) {
                                    castle.setRow(0);
                                    castle.setCol(3);
                                } else {
                                    piece.setRow(0);
                                    piece.setCol(4);
                                    return;
                                }
                            }                            
                        }                        
                    }
                }                      
                              
                if (piece instanceof King) {
                    ((King) piece).setWasMoving(true);
                }
                else if (piece instanceof Castle) {
                    ((Castle) piece).setWasMoving(true);
                }
                
                gameLogic.setMoveProviding(false);
                if (piece.getColor() == Constants.WHITE) {
                    gameLogic.setSide(Constants.BLACK);
                } else {
                    gameLogic.setSide(Constants.WHITE);
                }
                log.log(Level.INFO, "Piece was moved");                
                               
                gameFrame.unsetAllIcons();
                gameFrame.setAllIcons(gameField.getPieces());                
                
                if (checkToTheKing != null) {
                    gameFrame.setInfo("  Check to the King!");
                }

                if (gameVsBot) {
                    botMove();
                }                
            }            
        }  
    }
    
    public void botMove() {
        Piece piece = bot.selectPiece(gameField.getPieces());
        
        if (bot.countPossibleMoves(gameField.getAllPossibleMoves(piece)) == 0) {
            while (bot.countPossibleMoves(gameField.getAllPossibleMoves(piece)) == 0) {
                piece = bot.selectPiece(gameField.getPieces());            
            }            
        }
        
        bot.selectMove(piece, bot.countPossibleMoves(gameField.getAllPossibleMoves(piece)), gameField.getAllPossibleMoves(piece));
        
        int row = bot.getRow();
        int col = bot.getCol();
       
        if (gameField.getPieceByCoordinates(row, col) != null) {
            statistic.addRemovedPieceToThePanel(gameField.getPieceByCoordinates(row, col).getIcon());
            try {             
                gameField.removePieceFromPieces(row, col);
            } catch (Exception e) {                        
                log.log(Level.SEVERE, "King cannot be removed!");
            }             
        }
        
        piece.setRow(row);
        piece.setCol(col);

        gameFrame.unsetAllIcons();
        gameFrame.setAllIcons(gameField.getPieces());        
        
        if (piece.getColor() == Constants.WHITE) {
            gameLogic.setSide(Constants.BLACK);
        } else {
            gameLogic.setSide(Constants.WHITE);
        }        
    }
    
    public class ButtonHandler implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            Object source = e.getSource();         
            
            //Save game
            if (source == gameFrame.getSaveItem() && gameStarted) {
                saveDialog = new SaveFrame(this);               
                return;
            }                    
            if (saveDialog != null && source == saveDialog.getConfirm()) {
                try  {                   
                    FileOutputStream f = new FileOutputStream(new File("/home/quasi/NetBeansProjects/ChessGame/src/main/save/"+saveDialog.getTextField()));
                    ObjectOutputStream o = new ObjectOutputStream(f);                   
                    o.writeObject(gameLogic);
                    o.writeObject(gameField);               
                    log.log(Level.INFO, "Game was saved"); 
                } catch (IOException ex) {
                    log.log(Level.SEVERE, "IO exception");
                }                    
                return;
            }
            
            //Load game
            if (source == gameFrame.getLoadItem()) {
                listOfSaves = new ListOfSavesFrame();
                String path = listOfSaves.getSelectedSave();
                try {
                    fis = new FileInputStream(new File(listOfSaves.getSelectedSave()));
                    ois = new ObjectInputStream(fis);
                    gameLogic = null;
                    gameField = null;
                    gameFrame.unsetAllIcons();
                    gameLogic = (GameLogic) ois.readObject();
                    gameField = (GameField) ois.readObject();
                    gameField.addPiecesOnTheField();
                    gameFrame.setAllIcons(gameField.getPieces());
                    gameStarted = true;
                    log.log(Level.INFO, "New game was started");
//                    if (source == gameFrame.getBotItem()) {
//                        gameVsBot = true;
//                    } else {
//                        gameVsBot = false;
//                    }
                    save = new Save();
                    if (statistic != null) {
                        statistic.close();
                    }  
                    statistic = new StatisticFrame();
                    return;
                    
                } catch (FileNotFoundException ex) {
                    log.log(Level.SEVERE, null, ex);
                } catch (IOException | ClassNotFoundException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }
            
            //Save in PGN
            if (source == gameFrame.getPgnItem() && gameStarted) {
                savePgn = new SaveInPgnFrame(save);               
                return;
            }            
                       
            //New game
            if (source == gameFrame.getPlayersItem() || source == gameFrame.getBotItem()) {
                
                if (source == gameFrame.getBotItem()) {
                    bot = new Bot(false);
                }
                              
                if (statistic != null) {
                    statistic.close();
                }               
                statistic = new StatisticFrame();
                             
                gameLogic = null;
                gameField = null;
                gameFrame.unsetAllIcons();

                gameLogic = new GameLogic();
                gameField = new GameField();
                gameField.addPiecesOnTheField();
                gameFrame.setAllIcons(gameField.getPieces());
                gameStarted = true;
                log.log(Level.INFO, "New game was started");
                if (source == gameFrame.getBotItem()) {
                    gameVsBot = true;
                } else {
                    gameVsBot = false;
                }
                save = new Save();
                return;
            }
            
            //Replacing
            if (gameStarted && source == gameFrame.getSelectPieceButton()) {

                Boolean color = changedPawn.getColor();
                int col = changedPawn.getCol();
                if (gameFrame.getKnight().isSelected()) {
                    gameField.getPieces().remove(changedPawn);
                    gameField.getPieces().add(new Knight(color == Constants.WHITE ? 0 : 7, col, color));
                    gameFrame.unsetAllIcons();
                    gameFrame.setAllIcons(gameField.getPieces());                    
                } 
                else if (gameFrame.getCastle().isSelected()){
                    gameField.getPieces().remove(changedPawn);
                    gameField.getPieces().add(new Castle(color == Constants.WHITE ? 0 : 7, col, color));
                    gameFrame.unsetAllIcons();
                    gameFrame.setAllIcons(gameField.getPieces());
                }
                else if (gameFrame.getBishop().isSelected()){
                    gameField.getPieces().remove(changedPawn);
                    gameField.getPieces().add(new Bishop(color == Constants.WHITE ? 0 : 7, col, color));
                    gameFrame.unsetAllIcons();
                    gameFrame.setAllIcons(gameField.getPieces());
                }
                else if (gameFrame.getQueen().isSelected()){
                    gameField.getPieces().remove(changedPawn);
                    gameField.getPieces().add(new Queen(color == Constants.WHITE ? 0 : 7, col, color));
                    gameFrame.unsetAllIcons();
                    gameFrame.setAllIcons(gameField.getPieces());
                }
                log.log(Level.INFO, "Pawn was replaced");
                gameFrame.getSelectPieceFrame().dispose();
            }

            //Click
            if (gameStarted) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (source == gameFrame.getButtonByCoordinates(i, j)) {
                            processClick(i, j);
                            return;
                        }
                    }
                }                
            }                      
        }
    }
}
