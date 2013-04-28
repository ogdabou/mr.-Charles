package IHM;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
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

public class MainWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Container pan;
	private ArrayList<IPlugin> plugins;
	
	private JMenuBar menuBar;
	
	private JMenu file;
	private JMenuItem project;
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
	private ProgressPane progressPane;
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
		progressPane = right.getProgressPanel();
		toolBar = new ToolBar();
		panel = new PrimaryPanel();
		panel.init();
		panel.setProgressPane(progressPane);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(screen);
		this.setMinimumSize(screen);

		JPanel lol = new JPanel();
		lol.setPreferredSize(new Dimension(102, 10));
		this.add(lol, BorderLayout.NORTH);
		
		this.add(panel, BorderLayout.CENTER);
		this.add(footer, BorderLayout.SOUTH);
		this.add(right, BorderLayout.EAST);
		this.add(toolBar, BorderLayout.WEST);
		
		this.setJMenuBar(menuBarInit());
		jarLoader = new JarLoader(bonus, mandatory, this);
		jarLoader.load();
		
		this.setResizable(false);
		this.setVisible(true);
	}

	/**
	 * Handle events
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		Logger.debug("Action on: " + a.getActionCommand());
		String name = (String)a.getActionCommand();
		JFileChooser projectFinder = null;
		if (panel.getListSize() != 0 && !panel.getCurrentProject().getDirectory().equals(""))
		{
			projectFinder = new JFileChooser(new File(panel.getCurrentProject().getDirectory()));
		}
		else
			projectFinder = new JFileChooser();
		if (name.equals("Open project"))
		{
			//TODO the current searching directory should be saved AND serialized
			Logger.debug("Project opening requested.");
			projectFinder.setName("Open existing project");
			FileFilter filter = new FileNameExtensionFilter("*.myPSD", "myPSD");
			projectFinder.setFileFilter(filter);
			projectFinder.showOpenDialog(this);
		}
		else if (name.equals("Open file"))
		{
			Logger.debug("File opening requested.");
			FileFilter filter = new FileNameExtensionFilter("Image formats (*.jpg, *.jpeg, *.gif, *.ico, *.bmp)",
					"jpeg", "gif", "ico", "bmp", "jpg");
			projectFinder.setFileFilter(filter);
			projectFinder.setName("Add file to the current project");
			projectFinder.showOpenDialog(this);
			File choosen = projectFinder.getSelectedFile();
			if(choosen != null)
			{
				panel.addImage(choosen);
				if (panel.getListSize() != 0)
					panel.getCurrentProject().setDirectory(choosen.getPath());
			}
		}
		else if (name.equals("New project"))
		{
			Logger.debug("Creating new project");
			projectWindow.setTitle("New Project");
			projectWindow.setVisible(true);
		}
		else if (name.equals("OK"))
		{
			Logger.debug("New project named: " + projectWindow.getProjectName());
			panel.addProject(projectWindow.getProjectName());
			projectWindow.setVisible(false);
		}
		else if (name.equals("Exit"))
		{
			System.exit(0);
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
	
	
	/**
	 * Main window MenuBar initialization
	 * @return
	 */
	private JMenuBar menuBarInit()
	{
		menuBar = new JMenuBar();
		file = new JMenu("File");
		
		project = new JMenuItem("New project");
		project.addActionListener(this);
		project.setAccelerator(KeyStroke.getKeyStroke("control P"));

		file.add(project);

		openProject = new JMenuItem("Open project");
		openProject.setAccelerator(KeyStroke.getKeyStroke("control Q"));
		openProject.addActionListener(this);
		openFile = new JMenuItem("Open file");
		openFile.addActionListener(this);
		openFile.setAccelerator(KeyStroke.getKeyStroke("control N"));

		file.addSeparator();
		file.add(openProject);
		file.add(openFile);
		file.addSeparator();
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
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
