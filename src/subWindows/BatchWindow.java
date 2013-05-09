package subWindows;

import image.ImagePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingWorker;

import logger.Logger;

import IHM.Separator;
import IHM.center_panel.PrimaryPanel;
import IHM.progress_bar.ProgressScroller;

import plugin.IPlugin;
import projects.Project;

public class BatchWindow extends JFrame implements ActionListener{
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
	private JButton helpButton;    			//p.show(projectPane, e.getX(), e.getY());
	private JButton selectAll;
	private ProgressScroller progressPane;
	
	private Map<String, ImagePanel> imageList = new HashMap<String, ImagePanel>();
	private Map<String, IPlugin> pluginList = new HashMap<String, IPlugin>();
	private Map<ButtonModel, IPlugin> pluginMap = new HashMap<ButtonModel, IPlugin>();
	private Map<JCheckBox, ImagePanel> buttonlist = new HashMap<JCheckBox,
			ImagePanel>();
	
	private IPlugin plugin;
	
	private PrimaryPanel primary;
	private ButtonGroup radioGroup = new ButtonGroup();
	
	private ArrayList<Project> projectList;
	
	public BatchWindow() {
		
		framePanel = new JPanel();
		this.setTitle("Batch script creator");
		this.setMinimumSize(new Dimension(400, 600));
		this.setPreferredSize(new Dimension(400, 600));
		this.setMaximumSize(new Dimension(400, 600));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
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
		selectAll = new JButton("Select All");
		selectAll.addActionListener(this);
		createButton = new JButton("Create");
		createButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		helpButton = new JButton("HowTo");
		helpButton.addActionListener(this);
		buttonPanel.add(selectAll);
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
		buttonlist.clear();
		chooseImages.removeAll();
		for (ImagePanel image : projectList.getImageList())
		{
			JCheckBox imageBox = new JCheckBox(image.getName());
			imageBox.addActionListener(this);
			//imageList.put(image.getName(), image);
			buttonlist.put(imageBox, image);
			chooseImages.add(imageBox);
		}
	}
	
	//TODO store everything
	public void fillPluginsBoxes(ArrayList<IPlugin> pluginList)
	{
		chooseFilters.removeAll();
		pluginMap.clear();

		for (IPlugin plugin : pluginList)
		{
			JPanel boxpanel = new JPanel();
			JRadioButton b = new JRadioButton(plugin.getName());
			JTextField textField = new JTextField();
			textField.setPreferredSize(new Dimension(50, 20));
			this.pluginList.put(plugin.getName(), plugin);
			
			pluginMap.put(b.getModel(), plugin);
			boxpanel.add(new JLabel("exec order: "));
			boxpanel.add(textField);
			radioGroup.add(b);
			chooseFilters.add(b);
		}
	}
	
	public void setPrimary(PrimaryPanel p)
	{
		primary = p;
		progressPane = primary.getProgressPane();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = (String)(String)e.getActionCommand();
		
		if (name.equals("Create"))
		{
			Logger.debug("Creating new BATCH");
			Set<JCheckBox> s = buttonlist.keySet();
			plugin = pluginMap.get(radioGroup.getSelection());
			
			progressPane.createBatchBar(s.size());
			progressPane.showProgress();
			primary.getTopPanel().add(progressPane.getBatchProgress());

			ArrayList<ImagePanel> toCompute = new ArrayList<ImagePanel>();
			for (JCheckBox b : s)
			{
				if(b.isSelected())
				{
					toCompute.add(buttonlist.get(b));

				}
			}
			BatchComputer computer = new BatchComputer(primary, plugin,
					toCompute);
			computer.execute();
			/*for (JCheckBox b : s)
			{
				if(b.isSelected())
				{
					primary.getProjectPane().repaint();
					primary.computeBatch(plugin, buttonlist.get(b));
					primary.getProgressPane().repaintAll();
				}
			}
			primary.getTopPanel().repaint();*/
			this.setVisible(false);
		}
		else if (name.equals("Select All"))
		{
			Set<JCheckBox> s = buttonlist.keySet();
			for (JCheckBox b : s)
			{
				b.setSelected(true);
			}
		}
		else if (name.equals("Cancel"))
		{
			this.setVisible(false);
		}
	}
}

class BatchComputer extends SwingWorker
{
	private PrimaryPanel primary;
	private IPlugin plugin;
	private ArrayList<ImagePanel>  imageList;
	
	
	public BatchComputer(PrimaryPanel primary, IPlugin plugin,
			ArrayList<ImagePanel> toCompute)
	{
		this.primary = primary;
		this.plugin = plugin;
		this.imageList = toCompute;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		Logger.debug("Executing Batch with " + plugin.getName());
		for (ImagePanel image : imageList)
		{

			primary.getProjectPane().repaint();
			primary.computeBatch(plugin, image);
			primary.repaint();
		}
		return null;
	}
	
}
