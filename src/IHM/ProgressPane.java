package IHM;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import IHM.ProgressBarPanel;


/**
 * This class is used to display the progress of threads
 * and handle actions on them.
 * @author ogda
 *
 */
public class ProgressPane extends JScrollPane{
	private ArrayList<ProgressBarPanel> filterQueue;
	private JPanel container;

	public ProgressPane(JPanel containerr)
	{
		super(containerr);
		filterQueue = new ArrayList<ProgressBarPanel>();
		container = containerr;
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
	}
	
	/**
	 * Add a new ProgressBar
	 */
	public void addProgress(String threadName)
	{
		ProgressBarPanel newProgress = new ProgressBarPanel(threadName);
		filterQueue.add(newProgress);
		container.add(newProgress);
		newProgress.testCompletion();
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
			filterQueue.add(test);
			container.add(test);
		}
	}
}
