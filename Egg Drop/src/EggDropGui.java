import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EggDropGui extends JFrame {
	
	//Create global panels/frame
	static JPanel panel1 = new JPanel();
	static JPanel titlePanel = new JPanel();
	static JPanel panel2 = new JPanel();
	static JFrame frame = new JFrame();
	
	//Make list of lines
	static ArrayList<String> lines = new ArrayList<>();

	public static void main(String[] args) {
		
		String titleStr = "Click \"Log Data\" to begin logging or \"View Data\" to view the last collected data";
		
		//Create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create buttons
		JButton logData = new JButton("Log Data");
		
		//Add listener for logData
		logData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//Event for logData
				if(e.getSource().equals(logData))  {
					
					System.out.println("log data");
					
					//Run logData Method
					ReadData.logData();
					
					
				}
				
			}
			
		});
		
		//Create viewData
		JButton viewData = new JButton("View Data");
		
		//Add listener for viewData
		viewData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Event for viewData
				if(e.getSource().equals(viewData)) {
					
					System.out.println("view data");
					
				}
				
			}
			
		});
		
		//Create title
		JLabel title = new JLabel(titleStr);
		
		//Set font/bold
		title.setFont(new Font(title.getFont().getName(), Font.BOLD, 15));
		
		//Add buttons to panels
		panel1.add(logData);
		panel2.add(viewData);
		
		//Add title to panel
		titlePanel.add(title);
		
		//Set Panel borders
		titlePanel.setBorder(new EmptyBorder(20, 10, 10, 10));
		
		frame.setTitle("Egg Drop Logging Utility");
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(panel1, BorderLayout.WEST);
		frame.add(panel2, BorderLayout.EAST);

		frame.pack();
		//frame.setSize(800, 400);
		
		//Set frame visible
		frame.setVisible(true);
		
	}
	
	//Add text
	public static void addText(String text) {
		
		//Define string builder
		StringBuffer strbld = new StringBuffer();
		
		//Define constants
		int MAX_LINES = 10;
		int MAX_LETTERS = 40;
		
		JLabel textLabel = new JLabel();
		
		System.out.println("Lines size: "+lines.size());
		
		//If list is smaller than mex lines
		if(lines.size() < MAX_LINES) {
			
			//Add new line to array
			lines.add(text);
			
		} else {
			
			//If array is full
			lines.remove(0);
			
			//Shift entries down by 1
			for(int i = 0; i < lines.size()-1; i++) {
				
				lines.set(i, lines.get(i+1));
				
			}
			
			//Set last value to new text value
			lines.set(MAX_LINES, text);
			
		}
		
		//Add lines to string builder
		for(int i = 0; i < lines.size(); i++) {
			
			strbld.append(lines.get(i) + "\n");
			
		}
		
		//Add lines to label
		textLabel.setText(strbld.toString());
		System.out.println("Textlabel: "+strbld);

		//Add label and set visible
		titlePanel.add(textLabel);
		frame.add(titlePanel);
		titlePanel.setVisible(true);
		
	}

}
