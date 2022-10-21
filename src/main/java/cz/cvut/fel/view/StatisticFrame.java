package cz.cvut.fel.view;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author quasi
 */
public class StatisticFrame implements Serializable{
    
    private final JFrame frame;
    private JPanel mainPanel, headerPanel, bodyPanel, footerPanel;
    private JPanel headerSubPanel1, headerSubPanel2;
    private JLabel whiteName, blackName, whiteTime, blackTime;
    private JTextArea textArea;
    
    public StatisticFrame() {
        
        frame = new JFrame("Statistic");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 900);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int)rect.getMinX();
        int y = frame.getHeight()-(int)rect.getMaxY();
        frame.setLocation(x, y);        

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);
        mainPanel.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0))); 
        
        headerPanel = new JPanel();
        mainPanel.add(headerPanel);
        headerSubPanel1 = new JPanel();
        headerSubPanel2 = new JPanel();
        headerPanel.add(headerSubPanel1);
        headerPanel.add(headerSubPanel2);        
        whiteName = new JLabel("White:");
        blackName = new JLabel("Black:");
        whiteTime = new JLabel("00:00");
        blackTime = new JLabel("00:00");
        headerSubPanel1.add(whiteName);
        headerSubPanel1.add(whiteTime);
        headerSubPanel2.add(blackName);
        headerSubPanel2.add(blackTime);
        headerSubPanel1.setBorder(new EmptyBorder(new Insets(0, 0, 0, 30)));
        headerSubPanel2.setBorder(new EmptyBorder(new Insets(0, 30, 0, 0)));
        
        bodyPanel = new JPanel();
        mainPanel.add(bodyPanel);

        footerPanel = new JPanel();
        mainPanel.add(footerPanel);
        textArea = new JTextArea(60, 25);
        footerPanel.add(textArea);
        textArea.setCaretColor(Color.WHITE);
        textArea.setFont(textArea.getFont().deriveFont(16f));
    }
    
    public void addRemovedPieceToThePanel(ImageIcon piece) {
        Image image = piece.getImage();
        Image newImage = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        piece = new ImageIcon(newImage);
        JLabel pieceLabel = new JLabel(piece);
        bodyPanel.add(pieceLabel);
        SwingUtilities.updateComponentTreeUI(frame);
    }
    
    public void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }   

    public void setTextArea(String message) {
       textArea.setText(message);
    }
}
