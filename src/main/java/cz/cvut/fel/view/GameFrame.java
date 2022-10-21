package cz.cvut.fel.view;

import cz.cvut.fel.chessgame.ChessGame.ButtonHandler;
import cz.cvut.fel.model.Piece;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author quasi
 */
public class GameFrame extends JFrame {
    
    private final JButton[][] squares = new JButton[8][8];
    private final JMenuBar menuBar;
    private final JMenu newGameMenu, loadMenu, saveMenu, settingMenu, aboutMenu;
    private final JMenuItem playersItem, botItem, saveItem, pgnItem, loadItem;
    private JFrame frame, selectPieceFrame;
    private JPanel panel, selectPiecePanel;
    private JLabel info, selectPieceLabel;
    private JRadioButton castle, bishop, knight, queen;
    private JButton selectPieceButton;
    private ButtonGroup radioGroup;

    public JButton[][] getSquares() {
        return squares;
    }

    public void setInfo(String message) {
        info.setText(message);
    }
       
    public GameFrame(ButtonHandler buttonHandler) {
        
        frame = new JFrame("Chess v.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));
        frame.add(panel);
        
        menuBar = new JMenuBar();        
        newGameMenu = new JMenu("New Game");       
        loadMenu = new JMenu("Load");       
        saveMenu = new JMenu("Save");
        settingMenu = new JMenu("Settings");
        aboutMenu = new JMenu("About");
        
        playersItem = new JMenuItem("2 players");
        playersItem.addActionListener(buttonHandler);
        botItem = new JMenuItem("Player vs Bot");
        botItem.addActionListener(buttonHandler);
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(buttonHandler);
        pgnItem = new JMenuItem("Save in PGN");
        pgnItem.addActionListener(buttonHandler);
        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(buttonHandler);
                
        newGameMenu.add(playersItem);
        newGameMenu.add(botItem);
        saveMenu.add(saveItem);
        saveMenu.add(pgnItem);
        loadMenu.add(loadItem);
        menuBar.add(newGameMenu);
        menuBar.add(loadMenu);
        menuBar.add(saveMenu);
        menuBar.add(settingMenu);
        menuBar.add(aboutMenu);
        info = new JLabel("");
        info.setForeground(Color.blue);
        menuBar.add(info);
        frame.setJMenuBar(menuBar);        
    }

    public JMenuItem getPlayersItem() {
        return playersItem;
    }

    public JMenuItem getBotItem() {
        return botItem;
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

    public JMenuItem getLoadItem() {
        return loadItem;
    }
    
    public JMenuItem getPgnItem() {
        return pgnItem;
    }
    
    public void createGameField(ButtonHandler buttonHandler) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new JButton();
                if ((i+j) % 2 != 0) {
                    squares[i][j].setBackground(Color.GRAY);
                }
                panel.add(squares[i][j]);
                squares[i][j].addActionListener(buttonHandler);
                squares[i][j].setBorder(BorderFactory.createEmptyBorder()); 
                squares[i][j].setFocusable(false);
            }
        }     
    }
    
    public void setAllIcons(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            squares[p.getRow()][p.getCol()].setIcon(p.getIcon());
        }        
    }
    
    public void unsetAllIcons() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].setIcon(null);
            }
        }      
    }
    
    public JButton getButtonByCoordinates(int i, int j) {
        return squares[i][j];
    }
    
    public void showHints(int[][] possibleMoves) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (possibleMoves[i][j] == 1) {
                    squares[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                }
            }
        }        
    }
    
    public void unsetHints() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }
    
    public void showWinningMessage() {
        JOptionPane.showMessageDialog(frame, "End of the game!");
    }
    
    public void showSelectPieceWindow(ButtonHandler buttonHandler) {
        
        selectPieceFrame = new JFrame("Select a piece");
        selectPieceFrame.setSize(400, 280);        
        selectPieceFrame.setLocationRelativeTo(null);
        selectPieceFrame.setVisible(true);
        
        selectPiecePanel = new JPanel();
        selectPiecePanel.setLayout(new BoxLayout(selectPiecePanel, BoxLayout.Y_AXIS));
        selectPieceFrame.add(selectPiecePanel);
        selectPiecePanel.setBorder(new EmptyBorder(new Insets(30, 150, 30, 0)));    
        
        selectPieceLabel = new JLabel("Select a piece");        
        castle = new JRadioButton("Castle");
        knight = new JRadioButton("Knight");
        bishop = new JRadioButton("Bishop");
        queen = new JRadioButton("Queen");
        radioGroup = new ButtonGroup();
        selectPieceButton = new JButton("Select");
        selectPiecePanel.add(selectPieceLabel);
        selectPiecePanel.add(Box.createRigidArea(new Dimension(0, 25)));
        castle.setSelected(true);
        selectPiecePanel.add(castle);
        selectPiecePanel.add(knight);
        selectPiecePanel.add(bishop);
        selectPiecePanel.add(queen);
        selectPiecePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        selectPiecePanel.add(selectPieceButton);
        radioGroup.add(castle);
        radioGroup.add(knight);
        radioGroup.add(bishop);
        radioGroup.add(queen);
        
        selectPieceButton.addActionListener(buttonHandler);
    }

    public JRadioButton getKnight() {
        return knight;
    }

    public JRadioButton getCastle() {
        return castle;
    }

    public JRadioButton getBishop() {
        return bishop;
    }

    public JRadioButton getQueen() {
        return queen;
    }
    
    public JButton getSelectPieceButton() {
        return selectPieceButton;
    }

    public JFrame getSelectPieceFrame() {
        return selectPieceFrame;
    }          
}