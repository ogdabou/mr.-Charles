package IHM;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import plugin.IPlugin;
import plugin.JarLoader;
import sun.tools.jar.resources.jar;


import logger.Logger;

public class MainWindow extends JFrame implements ActionListener, MenuListener{

	private static final long serialVersionUID = 1L;
	private Container pan;
	private ArrayList<IPlugin> plugins;
	
	private JMenuBar menuBar;
	
	private JMenu file;
	private JMenu createNew;
	private JMenuItem project;
	private JMenuItem image;
	private JMenu open;
	private JMenuItem openProject;
	private JMenuItem openFile;
	private JMenuItem exit;
	private JMenu filters;
	private JMenu mandatory;
	private JMenu bonus;
	
	private PrimaryPanel panel;
	private FooterBar footer;
	private RightPanel right;
	private ToolBar toolBar;
	private NewProjectWindow projectWindow;
	private JarLoader jarLoader;
	/**
	 * Constructor
	 */
	public MainWindow() {
		plugins = new ArrayList<IPlugin>();
		this.setTitle("myPhotoshop - couty_a");
		this.setMinimumSize(new Dimension(1024, 700));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		projectWindow = new NewProjectWindow(this);
		projectWindow.setLayout(new BorderLayout());
		this.setMaximumSize(new Dimension(900,700));
		footer = new FooterBar();
		right = new RightPanel();
		toolBar = new ToolBar();
		panel = new PrimaryPanel();
		panel.init();

		this.add(panel, BorderLayout.CENTER);
		this.add(footer, BorderLayout.SOUTH);
		this.add(right, BorderLayout.EAST);
		this.add(toolBar, BorderLayout.WEST);
		
		this.setJMenuBar(menuBarInit());
		jarLoader = new JarLoader(bonus, mandatory, this);
		//jarLoader.load();
		

		this.setVisible(true);
	}

	/**
	 * Handle events
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		Logger.debug("Action on: " + a.getActionCommand());
		String name = (String)a.getActionCommand();
		JFileChooser projectFinder = new JFileChooser();

		if (name.contains("Existing "))
		{
			if (name.equals("Existing project"))
			{
				//TODO the current searching directory should be saved AND serialized
				Logger.debug("Project opening requested.");
				FileFilter filter = new FileNameExtensionFilter("*.myPSD", "myPSD");
				projectFinder.setFileFilter(filter);
				projectFinder.showOpenDialog(this);
			}
			else if (name.equals("Existing file"))
			{
				//TODO the current searching directory should be saved AND serialized
				Logger.debug("Project opening requested.");
				FileFilter filter = new FileNameExtensionFilter("Image formats (*.jpg, *.jpeg, *.gif, *.ico, *.bmp)",
						"jpeg", "gif", "ico", "bmp", "jpg");
				projectFinder.setFileFilter(filter);
				projectFinder.showOpenDialog(this);
				File choosen = projectFinder.getSelectedFile();
				if(choosen != null)
				{
					panel.addImage(choosen);
				}
			}
			File choosen = projectFinder.getSelectedFile();
		}
		else if (name.equals("Project"))
		{
			Logger.debug("Creating new project");
			projectWindow.setVisible(true);
			
		}
		else if (name.equals("OK"))
		{
			Logger.debug("New project named: " + projectWindow.getProjectName());
			panel.addProject(projectWindow.getProjectName());
			projectWindow.setVisible(false);
		}
		else
		{
			int index = 0;
			for (IPlugin current : plugins)
			{
				String pluginName = current.getName();
				if (name.equals(pluginName))
				{
					Logger.debug("Pluggin " + current.getName() + " selected");
					panel.applyFilter(plugins.get(index));
					//apply plugin
				}
				index += 1;
			}
		}
	}

	/**
	 * Validate a file extension
	 * @param name
	 * @return
	 */
	private boolean acceptFile(File name)
	{
		String nameS = name.getName();
		if (!nameS.contains(".jpg")
				&& !nameS.contains(".png")
				&& !nameS.contains("gif")
				&& !nameS.contains("jpeg")
				&& !nameS.contains("bmp")
				&& !nameS.contains("ico"))
		{
			return false;
		}
		return true;
	}
	
	public void addFilter(IPlugin p)
	{
		plugins.add(p);
	}
	
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * Called when the mouse is over the menu
	 */
	public void menuSelected(MenuEvent arg0) {
		
		//Logger.debug(arg0.getSource().toString());
		
	}
	
	
	/**
	 * Main window MenuBar initialization
	 * @return
	 */
	private JMenuBar menuBarInit()
	{
		menuBar = new JMenuBar();
		file = new JMenu("File");
		
		createNew = new JMenu("New...");
		project = new JMenuItem("Project");
		createNew.addMenuListener(this);
		project.addActionListener(this);

		image = new JMenuItem("File");
		image.addActionListener(this);
		
		createNew.add(project);
		createNew.add(image);
		file.add(createNew);

		open = new JMenu("Open");
		openProject = new JMenuItem("Existing project");
		openProject.addActionListener(this);
		openFile = new JMenuItem("Existing file");
		openFile.addActionListener(this);
		open.add(openProject);
		open.add(openFile);
		file.add(open);
		
		exit = new JMenuItem("Exit");
		file.add(exit);
		
		filters = new JMenu("Filters");
		mandatory = new JMenu("Mandatories");
		bonus = new JMenu("Bonus");
		filters.add(mandatory);
		filters.add(bonus);
		
		menuBar.add(file);
		menuBar.add(filters);
		
		return menuBar;
	}
}
