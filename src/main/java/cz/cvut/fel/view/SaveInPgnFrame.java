package cz.cvut.fel.view;

import cz.cvut.fel.model.Save;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author quasi
 */
public class SaveInPgnFrame {
    
    private JFrame frame;
    private JPanel mainPanel;
    private JButton confirm;
    private JLabel label;
    private JLabel event, site, date, round, white, black, result;
    private JTextField eventFIeld, siteField, roundField, whiteField, blackField, resultField; 
    private JFormattedTextField dateField;
    private Date dateNow;
    private SimpleDateFormat formatForDateNow;
    private JFileChooser fileChooser;
    private FileWriter fileWriter;
    private File file;
    private String content;
    private final Save moves;
    
    public SaveInPgnFrame(Save moves) {
        
        this.moves = moves;
        
        frame = new JFrame("Save in PGN format");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);     
        frame.setVisible(true);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);
        mainPanel.setBorder(new EmptyBorder(new Insets(30, 20, 30, 20))); 

        label = new JLabel("Required fields:");
        mainPanel.add(label);    
        label.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));

        event = new JLabel("Event");
        mainPanel.add(event);          
        eventFIeld = new JTextField();
        mainPanel.add(eventFIeld);          
        
        site = new JLabel("Site");
        mainPanel.add(site);          
        siteField = new JTextField();
        mainPanel.add(siteField);   

        date = new JLabel("Date (yyyy.mm.dd)");
        mainPanel.add(date);
        formatForDateNow = new SimpleDateFormat("yyyy.dd.mm");
        dateField = new JFormattedTextField(formatForDateNow);  
        mainPanel.add(dateField);
        dateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
              char c = e.getKeyChar();
              if (!((c >= '0') && (c <= '9') ||
                 (c == KeyEvent.VK_BACK_SPACE) ||
                 (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_PERIOD)))        
              {
                JOptionPane.showMessageDialog(null, "Please Enter Valid");
                e.consume();
              }
            }
          });      
        dateNow = new Date();
        dateField.setText(formatForDateNow.format(dateNow));
        
        round = new JLabel("Round");
        mainPanel.add(round);          
        roundField = new JTextField();
        mainPanel.add(roundField); 
        roundField.setText("1");
        
        white = new JLabel("White");
        mainPanel.add(white);          
        whiteField = new JTextField();
        mainPanel.add(whiteField); 
        
        black = new JLabel("Black");
        mainPanel.add(black);          
        blackField = new JTextField();
        mainPanel.add(blackField); 
        
        result = new JLabel("Result");
        mainPanel.add(result);          
        resultField = new JTextField();
        mainPanel.add(resultField);
        resultField.setText("1-0");
        
        confirm = new JButton("Save");        
        mainPanel.add(confirm);
        confirm.addActionListener((ActionEvent e) -> {
            fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                try {
                    fileWriter = new FileWriter(file.getAbsolutePath()+".pgn");
                    fileWriter.write(createContent());
                    fileWriter.close();
                    frame.setVisible(false);
                } catch (IOException ex) {
                    System.out.println("IOError");
                }
            }
        });
    }
    
    private String createContent() {
        return "[Event \""+eventFIeld.getText()+"\"]\n"
                +"[Site \""+siteField.getText()+"\"]\n"
                +"[Date \""+dateField.getText()+"\"]\n"
                +"[Round \""+roundField.getText()+"\"]\n"
                +"[White \""+whiteField.getText()+"\"]\n"
                +"[Black \""+blackField.getText()+"\"]\n"
                +"[Result \""+resultField.getText()+"\"]\n"
                +"\n"+moves.getMoves().replaceAll("\n", "");        
    }
}
