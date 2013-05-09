package IHM.progress_bar;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import logger.Logger;
import IHM.MainWindow;
import IHM.TopPanel;
import IHM.progress_bar.ProgressBarPanel;


/**
 * This class is used to display the progress of threads
 * and handle actions on them.
 * @author ogda
 *
 */
public class ProgressScroller extends JScrollPane{
	private HashMap<String, ArrayList<ProgressBarPanel>> filterQueue;
	private JPanel container;
	private MainWindow mainWindow;
	private JProgressBar batchProgress;
	private JProgressBar projectLoaderBar;

	public ProgressScroller(JPanel containerr, MainWindow mainWindow)
	{
		super(containerr);
		this.mainWindow = mainWindow;
		filterQueue = new HashMap<String, ArrayList<ProgressBarPanel>>();
		container = containerr;
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
	}
	
	/**
	 * Add a new ProgressBar
	 */
	public void addProgress(String threadName)
	{
		Logger.debug("adding progress bar");
		ProgressBarPanel newProgress = new ProgressBarPanel(threadName, mainWindow);
		newProgress.setPreferredSize(new Dimension(200, 30));
		newProgress.setMaximumSize(new Dimension(200, 30));
		if (filterQueue.get(threadName) != null)
			filterQueue.get(threadName).add(newProgress);
		else
		{
			filterQueue.put(threadName, new ArrayList<ProgressBarPanel>());
			filterQueue.get(threadName).add(newProgress);
		}
		container.add(newProgress);
		container.repaint();
		this.repaint();
	}
	
	public JProgressBar createProjectLoaderBar()
	{
		if(projectLoaderBar == null)
			projectLoaderBar = new JProgressBar();
		projectLoaderBar.setPreferredSize(new Dimension (250, 30));
		projectLoaderBar.setStringPainted(true);
		projectLoaderBar.setIndeterminate(true);
		projectLoaderBar.setString("Loading Project . . .");
		return projectLoaderBar;
	}
	
	public JProgressBar getProjectBar()
	{
		return projectLoaderBar;
	}
	
	public void createBatchBar(int max)
	{
		batchProgress = new JProgressBar(0, max);
		batchProgress.setPreferredSize(new Dimension(250, 30));
	}
	
	public void updateTitle(String title)
	{
		batchProgress.setName(title);
	}
	
	public void updateProgress(String msg)
	{
		msg = msg + "(" + batchProgress.getValue() + "/" + 
				(batchProgress.getMaximum()-1) +")";
		batchProgress.setString(msg);
		batchProgress.setStringPainted(true);
		Logger.debug("Batch progress is : " + (batchProgress.getValue() + 1));
		batchProgress.setValue(batchProgress.getValue() + 1);
		if (batchProgress.getValue() == batchProgress.getMaximum())
		{
			mainWindow.getTop().remove(batchProgress);
			mainWindow.getTop().repaint();
		}
	}
	
	public JProgressBar getBatchProgress()
	{
		return batchProgress;
	}
	
	public void showProgress()
	{
		container.add(batchProgress);
		container.repaint();
	}
	
	public void repaintAll()
	{
		if(container != null)
			container.repaint();
		this.repaint();
	}
	
	/**
	 * 
	 * @param threadName
	 */
	public synchronized void removeProgress(String threadName)
	{
		this.getViewport().remove(container);
		Logger.debug("Removing progressBar");
		container.remove(filterQueue.get(threadName).get(0));
		filterQueue.get(threadName).remove(0);
		this.getViewport().add(container);
		container.repaint();
		this.repaint();
		
	}
}
