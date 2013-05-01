package IHM.center_panel;

import image.ImagePanel;
import image.ImageViewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import IHM.progress_bar.ProgressScroller;

import com.sun.org.apache.bcel.internal.generic.LASTORE;

import logger.Logger;
import memento.CareTaker;
import memento.Originator;

import plugin.IPlugin;
import projects.Project;

/**
 * The Primary panel is the center panel, containing the image
 * @author ogda
 *
 */
public class PrimaryPanel extends JPanel implements ActionListener, MouseListener{
	private int nbProjects;
	private ArrayList<Project> projectList;
	private Map<JTabbedPane, Project> tabToProject;
	private Map<Project, JTabbedPane> projectToTab;
	private FilterThread computer;
	
	private static final long serialVersionUID = -6382602883374669443L;
	private BorderLayout mainLayout;
	private JTabbedPane projectPane;
	private JFileChooser fileChooser;
	private FileFilter filter;
	private ProgressScroller progressPane;
	private HystoryScroller historyScroller;
	public ImagePanel onProcessImage;
	private BufferedImage imageOnThread;
	public int onProcessImageIndex;
	/**
	 * 
	 */
	public PrimaryPanel() {
		tabToProject = new HashMap<JTabbedPane, Project>();
		projectToTab = new HashMap<Project, JTabbedPane>();
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
		//projectPane.addMouseListener(this);
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
		
		JTabbedPane imageTabbedPane = new JTabbedPane();
		imageTabbedPane.addMouseListener(this);
		JButton loadFirst = new JButton("Click to load image(s)");
		Project newProject = new Project(name);
		newProject.setHistoryScrolelr(historyScroller);
		loadFirst.addActionListener(this);
		loadFirst.setPreferredSize(new Dimension(100, 100));
		imageTabbedPane.add(loadFirst.getName(), loadFirst);
		projectPane.add(name ,imageTabbedPane);
		projectList.add(newProject);
		tabToProject.put(imageTabbedPane, newProject);
		projectToTab.put(newProject, imageTabbedPane);
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
		// get the tabbedPane of the current project
		JTabbedPane pane = (JTabbedPane)projectPane.getSelectedComponent();
		// get the concerned project
		Project addedProject = tabToProject.get(pane);
		
		JScrollPane added = addedProject.addImage(file);
		if (addedProject.getListSize() <= 1)
		{
			pane.remove(0);
		}
		// each onglet of the projectPane contains a JScrollPane
		pane.add(file.getName(), added);
		pane.setSelectedIndex(pane.getTabCount() - 1);
	}
	/**
	 * 
	 * @return
	 */
	public Project getCurrentProject()
	{
		if(projectList.size() >= 0)
		{
			JTabbedPane pane = (JTabbedPane)projectPane.getSelectedComponent();
			return tabToProject.get(pane);
		}
		else
			return null;
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
	 * This function stop the current Filter thread.
	 * We had stored the old image copy.
	 */
	public void stopProcess()
	{
		if (computer != null)
		{
			Logger.debug("thread canceled");
			computer.cancel(true);
		}
	}

	/**
	 * 
	 * @param pane
	 */
	public void setProgressPane(ProgressScroller pane)
	{
		progressPane = pane;
	}
	/**
	 * 
	 * @return
	 */
	public ProgressScroller getProgressPane()
	{
		return progressPane;
	}
	
	public JTabbedPane getProjectTab(Project p)
	{
		return projectToTab.get(p);
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
	
	public void setHistoryScroller(HystoryScroller h)
	{
		this.historyScroller = h;
	}

	/**
	 * Return the ImagePanel selected by the user
	 * @return
	 */
	public ImagePanel getCurrentImage()
	{
		Project currentP = getCurrentProject();
		JTabbedPane pane = getProjectTab(currentP);
		JScrollPane scrollPanel = (JScrollPane)pane.getComponent(pane.getSelectedIndex());
		ImagePanel image = currentP.getImage(scrollPanel);
		return image;
	}
	
	/**
	 * Return the current JScrollPane
	 * @return
	 */
	public JScrollPane getCurrentScrollPane()
	{
		Project currentP = getCurrentProject();
		JTabbedPane pane = getProjectTab(currentP);
		return (JScrollPane)pane.getComponent(pane.getSelectedIndex());
	}
	
	public HystoryScroller getHistoryScroller()
	{
		return historyScroller;
	}
	
	/**
	 * Implements CareTaker.
	 * Save the currentImage to a memento.
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		Project currentProject = getCurrentProject();
		ImagePanel img = getCurrentImage();
		historyScroller.redraw(currentProject.getMementoList().get(img));
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println("entered" + e);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println("exited" + e);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("pressed" + e);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	private BufferedImage oldPicture;
	
	public FilterThread(IPlugin filter, PrimaryPanel primary) {
		parent = primary;
		
		this.filter = filter;
	}

	@Override
	protected Object doInBackground() throws Exception {

		Project currentP = parent.getCurrentProject();
		JTabbedPane pane = parent.getProjectTab(currentP);
		JScrollPane scrollPanel = (JScrollPane)pane.getComponent(pane.getSelectedIndex());
		ImagePanel image = currentP.getImage(scrollPanel);
		
		oldPicture = image.getCopy().image;

		Logger.debug("Applying " + filter.getName() + " on " + image.getName());

		ImageViewer viewer = new ImageViewer(image);

		BufferedImage result = filter.perform(image.image);
		if (!this.isCancelled())
		{
			Logger.debug("Thread ending normaly");
			image.setImage(result);
			Logger.debug("addMemento for :" + image.getName());
			currentP.addMemento(image, "Filter: " + filter.getName());
		}
		else
		{
			Logger.debug("Thread cancelled.");
			image.setImage(oldPicture);
		}
		scrollPanel.getViewport().add(viewer);
		parent.getProgressPane().removeProgress(filter.getName());
		parent.getHistoryScroller().redraw(currentP.getMementoList().get(image));
		return null;
	}	
}
