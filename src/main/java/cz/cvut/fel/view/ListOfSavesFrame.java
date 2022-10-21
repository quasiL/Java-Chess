package cz.cvut.fel.view;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author quasi
 */
public class ListOfSavesFrame {
    
    private final JFrame frame;
    private JPanel mainPanel;
    private final File folder = new File("./src/main/save/");
    private File[] listOfFiles;
    private JList list;
    private final JLabel label = new JLabel("Select game");
    private final JButton button = new JButton("Select");
    private Object objList;
    private String selectedSave;
    
    public ListOfSavesFrame() {
        
        frame = new JFrame("Load game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 900);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel);
        mainPanel.setBorder(new EmptyBorder(new Insets(30, 20, 30, 20))); 
        
        listOfFiles = folder.listFiles();
        list = new JList(listOfFiles);  
        mainPanel.add(list);

        button.addActionListener(this::selectClick);      
        mainPanel.add(button);
    }
    
    public void selectClick(ActionEvent e) {
        objList = list.getSelectedValue();
        selectedSave = objList.toString();
        System.out.println("path: "+selectedSave);
    }

    public String getSelectedSave() {
        return selectedSave;
    }
}
