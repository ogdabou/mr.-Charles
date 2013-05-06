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

import IHM.center_panel.FooterBar;
import IHM.center_panel.PrimaryPanel;
import IHM.center_panel.RightPanel;
import IHM.center_panel.ToolBar;
import IHM.center_panel.TopPanel;
import IHM.progress_bar.ProgressScroller;

import plugin.IPlugin;
import plugin.JarLoader;
import projects.Project;
import saver.ImageSaver;
import saver.ProjectSaver;
import subWindows.BatchWindow;
import subWindows.NewProjectWindow;
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
	private JMenuItem saveProject;
	private JMenuItem saveImage;
	private JMenu filters;
	private JMenu mandatory;
	private JMenu bonus;
	private JMenuItem batch;
	
	private PrimaryPanel panel;
	private FooterBar footer;
	private RightPanel right;
	private ToolBar toolBar;
	private NewProjectWindow projectWindow;
	private BatchWindow batchWindow;
	private JarLoader jarLoader;
	private ProgressScroller progressPane;
	private TopPanel topPanel;
	/**
	 * Constructor
	 */
	public MainWindow() {
		try {
			//UIManager.setLookAndFeel("javax.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		topPanel = new TopPanel();
		batchWindow = new BatchWindow();
		plugins = new ArrayList<IPlugin>();
		this.setTitle("myPhotoshop - couty_a");
		this.setMinimumSize(new Dimension(1024, 700));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		projectWindow = new NewProjectWindow(this);
		projectWindow.setLayout(new BorderLayout());
		this.setMaximumSize(new Dimension(900,700));
		footer = new FooterBar();
		right = new RightPanel(this);
		progressPane = right.getProgressPanel();

		toolBar = new ToolBar();
		panel = new PrimaryPanel();
		panel.init();
		panel.setProgressPane(progressPane);
		panel.setHistoryScroller(right.getHistoryPanel());
		panel.getHistoryScroller().setPrimaryPanel(panel);
		panel.setFooter(footer);
		panel.setTopPanel(topPanel);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(screen);
		this.setMinimumSize(screen);

		/*JPanel lol = new JPanel();
		lol.setPreferredSize(new Dimension(102, 10));*/
		this.add(topPanel, BorderLayout.NORTH);
		
		this.add(panel, BorderLayout.CENTER);
		this.add(footer, BorderLayout.SOUTH);
		//this.add(new Separator(), BorderLayout.SOUTH);
		this.add(right, BorderLayout.EAST);
		this.add(toolBar, BorderLayout.WEST);
		
		this.setJMenuBar(menuBarInit());
		jarLoader = new JarLoader(bonus, mandatory, this);
		jarLoader.load();
		batchWindow.setPrimary(panel);
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
			File projectFile = projectFinder.getSelectedFile();
			if (projectFile != null)
			{
				topPanel.add(panel.getProgressPane().createProjectLoaderBar());
				topPanel.revalidate();
				topPanel.setVisible(true);
				this.revalidate();
				ProjectSaver.openProject(this, projectFile,
						panel.getProgressPane().getProjectBar());
				/*Project newProject = ProjectSaver.openProject(projectFile);
				if (newProject != null)
					panel.loadProject(newProject);*/
				//topPanel.remove(panel.getProgressPane().getProjectBar());
			}
		}
		else if (name.equals("Open file"))
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			Logger.debug("File opening requested.");
			FileFilter filter = new FileNameExtensionFilter("Image formats (*.jpg, *.jpeg, *.gif, *.ico, *.bmp)",
					"jpeg", "gif", "ico", "bmp", "jpg");
			fileChooser.setFileFilter(filter);
			fileChooser.setName("Add file to the current project");
			fileChooser.showOpenDialog(this);

			File choosen = fileChooser.getSelectedFile();
			if(choosen != null)
			{
				if (choosen.isDirectory())
				{
					for (File file : choosen.listFiles())
					{
						if (file != null)
						{
							panel.addImage(file);
							if (panel.getListSize() != 0)
								panel.getCurrentProject().setDirectory(file.getPath());
						}
					}
				}
				else
				{
					
					panel.addImage(choosen);
					if (panel.getListSize() != 0)
						panel.getCurrentProject().setDirectory(choosen.getPath());
				}
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
		else if (name.equals("X"))
		{
			panel.stopProcess();
		}
		else if (name.equals("Batch"))
		{
			Logger.debug("batch  " + panel.getProjectList().size());
			if (panel.getProjectList().size() > 0)
				batchWindow.fillImagesBoxes(panel.getCurrentProject());
			batchWindow.fillPluginsBoxes(plugins);
			batchWindow.setVisible(true);
		}
		else if (name.equals("Save current image"))
		{
			ImageSaver.saveImage(panel.getCurrentImage(), this);
		}
		else if (name.equals("Save project"))
		{
			ProjectSaver.saveProject(panel.getCurrentProject(), this);
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
	
	public TopPanel getTop()
	{
		return topPanel;
	}
	
	public PrimaryPanel getPrimaryPanel()
	{
		return panel;
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
		
		saveImage = new JMenuItem("Save current image");
		saveImage.addActionListener(this);
		saveProject = new JMenuItem("Save project");
		saveProject.addActionListener(this);
		file.add(saveProject);
		file.add(saveImage);
		file.addSeparator();
		
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		
		filters = new JMenu("Filters");
		mandatory = new JMenu("Mandatories");
		batch = new JMenuItem("Batch");
		batch.addActionListener(this);
		bonus = new JMenu("Bonus");
		filters.add(mandatory);
		filters.add(bonus);
		filters.addSeparator();
		filters.add(batch);
		
		menuBar.add(file);
		menuBar.add(filters);
		
		return menuBar;
	}
}
