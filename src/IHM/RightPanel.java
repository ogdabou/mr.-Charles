package IHM;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RightPanel extends JPanel{
	private JTabbedPane topPane;
	private JPanel progressPanel;
	private ProgressPane progress;
	
	public RightPanel() {
		topPane = new JTabbedPane();
		progressPanel = new JPanel();
		progressPanel.setBackground(Color.WHITE);
		topPane.setPreferredSize(new Dimension(240, 300));
		topPane.setMinimumSize(new Dimension(240, 300));
		progress = new ProgressPane(progressPanel);
		topPane.add("Filters execution", progress);
		
		this.add(topPane);
		this.setPreferredSize(new Dimension(250, 600));
		this.setMinimumSize(new Dimension(250, 600));
	}
	
	public ProgressPane getProgressPanel()
	{
		return progress;
	}

}
