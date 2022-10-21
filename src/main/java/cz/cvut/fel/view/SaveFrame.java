package cz.cvut.fel.view;

import cz.cvut.fel.chessgame.ChessGame.ButtonHandler;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author quasi
 */
public class SaveFrame {
    
    private JFrame frame;
    private JPanel mainPanel;
    private JButton confirm;
    private JLabel label;
    private JTextField textField;    
    private Date date;
    private SimpleDateFormat formatForDateNow;
    
    public SaveFrame(ButtonHandler buttonHandler) {
        frame = new JFrame("Save");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);     
        frame.setVisible(true);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);
        mainPanel.setBorder(new EmptyBorder(new Insets(30, 20, 30, 20))); 

        label = new JLabel("Save as:");
        mainPanel.add(label);    
        label.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
        textField = new JTextField();
        mainPanel.add(textField); 
        
        date = new Date();
        formatForDateNow = new SimpleDateFormat("yyyyMMdd_HHmm");
        textField.setText(formatForDateNow.format(date)+"_game");        
        textField.setSize(100, 20);
        
        confirm = new JButton("Save");
        confirm.addActionListener(buttonHandler);
        mainPanel.add(confirm);         
    }

    public JButton getConfirm() {
        return confirm;
    }    

    public String getTextField() {
        return textField.getText();
    }
}
