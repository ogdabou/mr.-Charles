package IHM;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewProjectWindow extends JFrame{
	private JTextField projectName;
	private JPanel pan;
	private JLabel projectNameLabel;
	private JButton okButton;
	
	
	public NewProjectWindow(MainWindow parent)
	{
		this.setTitle("myPhotoshop - couty_a");
		this.setMinimumSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		pan = new JPanel();
		
		projectName = new JTextField();
		projectName.setPreferredSize(new Dimension(100, 20));
		projectName.setName("salut");
		
		projectNameLabel = new JLabel("Name: ");
		
		okButton = new JButton("OK");
		okButton.addActionListener(parent);
		
		pan.add(projectNameLabel);
		pan.add(projectName);
		pan.add(okButton);
		this.add(pan);
		this.pack();
	}
	
	public String getProjectName()
	{
		return projectName.getText();
	}
}
