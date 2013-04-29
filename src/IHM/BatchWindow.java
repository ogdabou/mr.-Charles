package IHM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import plugin.IPlugin;
import projects.Project;

public class BatchWindow extends JFrame{
	private JPanel framePanel;
	private JPanel setNamePanel;
	private JLabel nameLabel;
	private JTextField nameField;
	private JPanel chooseFilters;
	private JScrollPane filterPane;
	private JPanel chooseImages;
	private JScrollPane imagePane;
	private JButton cancelButton;
	private JButton createButton;
	private JButton helpButton;
	
	private ArrayList<Project> projectList;
	
	public BatchWindow() {
		
		framePanel = new JPanel();
		this.setTitle("Batch script creator");
		this.setMinimumSize(new Dimension(400, 600));
		this.setPreferredSize(new Dimension(400, 600));
		this.setMaximumSize(new Dimension(400, 600));
		this.setResizable(false);
		framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
		
		setNamePanel = new JPanel();
		setNamePanel.setBorder(BorderFactory.createTitledBorder("Name the script"));
		setNamePanel.setPreferredSize(new Dimension(380, 55));
		setNamePanel.setMaximumSize(new Dimension(380, 55));
		nameLabel = new JLabel("Script name: ");
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(200, 20));
		setNamePanel.add(nameLabel);
		setNamePanel.add(nameField);
		framePanel.add(setNamePanel);

		framePanel.add(new Separator());
		
		filterPane = new JScrollPane();
		chooseFilters = new JPanel();
		chooseFilters.setBorder(BorderFactory.createTitledBorder("Filters to apply"));
		chooseFilters.setLayout(new BoxLayout(chooseFilters, BoxLayout.PAGE_AXIS));
		filterPane.setMinimumSize(new Dimension(380, 150));
		filterPane.setPreferredSize(new Dimension(380, 150));
		
		filterPane.getViewport().add(chooseFilters);
		framePanel.add(filterPane);
		
		framePanel.add(new Separator());
		
		chooseImages = new JPanel();
		chooseImages.setBorder(BorderFactory.createTitledBorder("Image to apply"));
		chooseImages.setLayout(new BoxLayout(chooseImages, BoxLayout.PAGE_AXIS));

		imagePane = new JScrollPane();
		imagePane.getViewport().add(chooseImages);
		imagePane.setMinimumSize(new Dimension(380, 150));
		imagePane.setPreferredSize(new Dimension(380, 150));
		framePanel.add(imagePane);
		
		framePanel.add(new Separator());
		
		JPanel buttonPanel = new JPanel();
		createButton = new JButton("Create");
		cancelButton = new JButton("Cancel");
		helpButton = new JButton("HowTo");
		buttonPanel.add(createButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(helpButton);
		framePanel.add(buttonPanel);
		
		this.add(framePanel);
		this.setSize(new Dimension(400, 400));
		this.pack();
	}
	
	// TODO store everything
	public void fillImagesBoxes(Project projectList)
	{
		for (ImagePanel image : projectList.getImageList())
		{
			JCheckBox imageBox = new JCheckBox(image.getName());
			chooseImages.add(imageBox);
		}
	}
	
	//TODO store everything
	public void fillPluginsBoxes(ArrayList<IPlugin> pluginList)
	{
		for (IPlugin plugin : pluginList)
		{
			JPanel boxpanel = new JPanel();
			JCheckBox b = new JCheckBox(plugin.getName());
			b.setBackground(Color.WHITE);
			JTextField textField = new JTextField();
			textField.setPreferredSize(new Dimension(50, 20));
			boxpanel.add(new JLabel("exec order: "));
			boxpanel.add(textField);
			boxpanel.add(b);
			boxpanel.setBackground(Color.WHITE);
			chooseFilters.add(boxpanel);
		}
	}
}
