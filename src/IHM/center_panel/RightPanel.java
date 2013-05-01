package IHM.center_panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import IHM.MainWindow;
import IHM.progress_bar.ProgressScroller;

public class RightPanel extends JPanel{
	private JTabbedPane topPane;
	private JTabbedPane botPane;
	private JPanel progressPanel;
	private HystoryScroller history;
	private ProgressScroller progress;
	
	public RightPanel(MainWindow mainWindow) {
		topPane = new JTabbedPane();
		progressPanel = new JPanel();
		progressPanel.setBackground(Color.WHITE);
		topPane.setPreferredSize(new Dimension(240, 300));
		topPane.setMinimumSize(new Dimension(240, 300));
		progress = new ProgressScroller(progressPanel, mainWindow);
		topPane.add("Filters execution", progress);
		
		botPane = new JTabbedPane();
		history = new HystoryScroller();
		//botPane.setBackground(Color.WHITE);
		botPane.setPreferredSize(new Dimension(240, 300));
		botPane.setMinimumSize(new Dimension(240, 300));
		botPane.add("History", history);
		
		this.add(topPane);
		this.add(botPane);
		this.setPreferredSize(new Dimension(250, 600));
		this.setMinimumSize(new Dimension(250, 600));
	}
	
	public ProgressScroller getProgressPanel()
	{
		return progress;
	}
	
	public HystoryScroller getHistoryPanel()
	{
		return history;
	}

}
