package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.org.apache.bcel.internal.generic.LASTORE;

import logger.Logger;

import plugin.IPlugin;
import projects.Project;

/**
 * The Primary panel is the center panel, containing the image
 * @author ogda
 *
 */
public class PrimaryPanel extends JPanel implements ActionListener{
	private int nbProjects;
	private ArrayList<Project> projectList;
	private FilterThread computer;
	
	private static final long serialVersionUID = -6382602883374669443L;
	private BorderLayout mainLayout;
	private JTabbedPane projectPane;
	private JFileChooser fileChooser;
	private FileFilter filter;
	private ProgressPane progressPane;
	public BufferedImage onProcessImage;
	public int onProcessImageIndex;
	/**
	 * 
	 */
	public PrimaryPanel() {
		fileChooser = new JFileChooser();
		filter = new FileNameExtensionFilter("Image formats (*.jpg, *.jpeg, *.gif, *.ico, *.bmp)",
				"jpeg", "gif", "ico", "bmp", "jpg");
		fileChooser.setFileFilter(filter);
		projectList = new ArrayList<Project>();
		nbProjects = 0;
		mainLayout = new BorderLayout();
	}
	
	/**
	 * 
	 */
	public void init()
	{
		this.setLayout(mainLayout);
		projectPane = new JTabbedPane();
		this.add(projectPane);
	}
	
	/**
	 * Create a new tab for a new tab.			
	 * The tab is empty and only has a button.
	 * @param name
	 */
	public void addProject(String name)
	{
		Logger.debug("Adding a new project");
		
		JTabbedPane imagePane = new JTabbedPane();
		JButton loadFirst = new JButton("Click to load image(s)");
		Project newProject = new Project(name);
		loadFirst.addActionListener(this);
		loadFirst.setPreferredSize(new Dimension(100, 100));
		imagePane.add(loadFirst.getName(), loadFirst);
		projectPane.add(name ,imagePane);
		projectList.add(newProject);
		nbProjects += 1;
	}
	
	/**
	 * Add an image to the current project.
	 * If no project are opened, create a new one.
	 * @param file
	 */
	public void addImage(File file)
	{
		if (projectList.size() == 0)
		{
			addProject("Untitled-" + nbProjects);
		}

		int index = projectPane.getSelectedIndex();
		JScrollPane added = projectList.get(index).addImage(file);

		JTabbedPane pane =  (JTabbedPane)projectPane.getComponentAt(index);
		if (projectList.get(index).getListSize() <= 1)
		{
			pane.remove(0);
		}
		pane.add(projectList.get(index).getImageFormatedName(projectList.
				get(index).getListSize() - 1), added);
	}
	/**
	 * 
	 * @return
	 */
	public Project getCurrentProject()
	{
		if(projectList.size() >= 0)
			return projectList.get(getCurrentProjectIndex());
		else
			return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCurrentProjectIndex()
	{
		Logger.debug("getCurrentProjectIndex = " + 
							projectPane.getSelectedIndex());
		return projectPane.getSelectedIndex();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCurrentImageIndex()
	{
		JTabbedPane pane =  (JTabbedPane)projectPane
				.getComponentAt(getCurrentImageIndex());
		Logger.debug("getCurrentImageIndex = " + pane.getSelectedIndex());
		return pane.getSelectedIndex();
	}
	
	/**
	 * 
	 */
	public void setDirectory(String path)
	{
		getCurrentProject().setDirectory(path);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDirectory()
	{
		return getCurrentProject().getDirectory();
	}
	/**
	 * Return the size of the list containing all the projects
	 * @return
	 */
	public int getListSize()
	{
		Logger.debug("projectListSize is: " + projectList.size());
		return projectList.size();
	}
	
	public ArrayList<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList<Project> projectList) {
		this.projectList = projectList;
	}

	public JTabbedPane getProjectPane() {
		return projectPane;
	}

	public void setProjectPane(JTabbedPane projectPane) {
		this.projectPane = projectPane;
	}

	/**
	 * Apply the given filter to the current image.
	 * @param filter
	 */
	public void applyFilter(IPlugin filter)
	{
		computer = new FilterThread(filter, this);
		progressPane.addProgress(filter.getName());
		this.revalidate();
		computer.execute();
	}
	
	/**
	 * 
	 */
	public void stopProcess()
	{
		if (computer != null)
		{
			Logger.debug("thread canceled");
			computer.cancel(true);
			/*projectList.get(getCurrentProjectIndex())
			.getImage(getCurrentImageIndex()).setImage(onProcessImage);*/
		}
	}

	/**
	 * 
	 * @param pane
	 */
	public void setProgressPane(ProgressPane pane)
	{
		progressPane = pane;
	}
	/**
	 * 
	 * @return
	 */
	public ProgressPane getProgressPane()
	{
		return progressPane;
	}
	
	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = arg0.getActionCommand();
		if (name.equals("Click to load image(s)"))
		{
			fileChooser.showOpenDialog(new JFrame());
			File choosen = fileChooser.getSelectedFile();
			if (choosen != null)
			{
				Logger.debug("File " + choosen + " selected");
				addImage(choosen);
				if (this.getListSize() != 0)
					this.getCurrentProject().setDirectory(choosen.getPath());
			}
		}
		
	}
}

/**
 * Used to thread our batched script!
 * We have to update a JScrollPane,
 * to use this we use the
 * getViewPort().add() because the .add method does'nt work.
 * @author ogda
 *
 */
class BatchFilterThread extends SwingWorker
{
	private IPlugin filter;
	private PrimaryPanel parent;
	
	public BatchFilterThread(IPlugin filter, PrimaryPanel primary) {
		parent = primary;
		
		this.filter = filter;
	}

	@Override
	protected Object doInBackground() throws Exception {
		int index = parent.getProjectPane().getSelectedIndex();
		JTabbedPane pane = (JTabbedPane)parent.getProjectPane()
				.getComponentAt(index);
		Project currentP = parent.getProjectList().get(index);
		ImagePanel image = currentP.getImage(pane.getSelectedIndex());

		Logger.debug("Applying " + filter.getName() + " on " + image.getName());

		JScrollPane panel = (JScrollPane)pane.getComponent(pane.getSelectedIndex());
		ImageViewer viewer = new ImageViewer(image);

		BufferedImage result = filter.perform(image.image);
		image.setImage(result);
		panel.getViewport().add(viewer);
		parent.getProgressPane().removeProgress(filter.getName());
		return null;
	}	
}

/**
 * Used to thread our Filters!
 * We have to update a JScrollPane,
 * to use this we use the
 * getViewPort().add() because the .add method does'nt work.
 * @author ogda
 *
 */
class FilterThread extends SwingWorker
{
	private IPlugin filter;
	private PrimaryPanel parent;
	
	public FilterThread(IPlugin filter, PrimaryPanel primary) {
		parent = primary;
		
		this.filter = filter;
	}

	@Override
	protected Object doInBackground() throws Exception {
		int index = parent.getProjectPane().getSelectedIndex();
		JTabbedPane pane = (JTabbedPane)parent.getProjectPane()
				.getComponentAt(index);
		Project currentP = parent.getProjectList().get(index);
		ImagePanel image = currentP.getImage(pane.getSelectedIndex());
		parent.onProcessImage = image.image;

		Logger.debug("Applying " + filter.getName() + " on " + image.getName());

		JScrollPane panel = (JScrollPane)pane.getComponent(pane.getSelectedIndex());
		ImageViewer viewer = new ImageViewer(image);

		BufferedImage result = filter.perform(image.image);
		image.setImage(result);
		panel.getViewport().add(viewer);
		parent.getProgressPane().removeProgress(filter.getName());
		return null;
	}	
}
