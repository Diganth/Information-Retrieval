package IR1;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

import javax.swing.*;

public class Gui extends JPanel implements ActionListener {
    
	protected JTextField textField;
    protected JTextArea textArea;
    static String query;
    static Integer[] result;
    static String[] document;
    static int erFlag;

    public Gui() {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(30, 50);
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 10;
        c.ipady = 10;
        c.weightx = 10.0;
        c.weighty = 10.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
        query = textField.getText();
          
        Query.resolveQuery(query);
        
        if(erFlag < 1){
        	textArea.setText("");
        	textArea.repaint();
        	for (int i=0; i < result.length; i++){
        		if (result[i] != null){
        			textArea.append("Document " + i + ":" + document[result[i]] + "\n\n\n");
        		}
        	}
        }
        else if (erFlag == 1){
        	textArea.setText("");
        	textArea.repaint();
        	textArea.append("Please retype the query . . The token being queried for does not exist.");
        }
        else{
        	textArea.setText("");
        	textArea.repaint();
        	textArea.append("Please retype the query. There seems to be a problem with the query.");
        }
        
    }

    static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new Gui());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

   public static void result(int errFlag, Integer[] answer, String[] documents) {
	   	erFlag = errFlag;
		result = answer;
		document = documents;
	}
}
