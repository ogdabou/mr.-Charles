package IHM;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logger.Logger;
import IHM.ProgressBarPanel;


/**
 * This class is used to display the progress of threads
 * and handle actions on them.
 * @author ogda
 *
 */
public class ProgressPane extends JScrollPane{
	private HashMap<String, ArrayList<ProgressBarPanel>> filterQueue;
	private JPanel container;

	public ProgressPane(JPanel containerr)
	{
		super(containerr);
		filterQueue = new HashMap<String, ArrayList<ProgressBarPanel>>();
		container = containerr;
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
	}
	
	/**
	 * Add a new ProgressBar
	 */
	public void addProgress(String threadName)
	{
		ProgressBarPanel newProgress = new ProgressBarPanel(threadName);
		if (filterQueue.get(threadName) != null)
			filterQueue.get(threadName).add(newProgress);
		else
		{
			filterQueue.put(threadName, new ArrayList<ProgressBarPanel>());
			filterQueue.get(threadName).add(newProgress);
		}
		container.add(newProgress);
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
		this.revalidate();
		
	}
	
	/**
	 * 
	 */
	public void testScroll()
	{
		int cpt = 0;
		while(cpt < 100)
		{
			cpt ++;
			ProgressBarPanel test = new ProgressBarPanel("test" + cpt);
			container.add(test);
		}
	}
}
