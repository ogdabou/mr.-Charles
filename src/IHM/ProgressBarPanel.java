package IHM;



import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarPanel extends JPanel{

	private JProgressBar progressBar;
	private JLabel progressTitleLabel;
	private String progressTitle;
	
	public ProgressBarPanel(String threadName) {
		this.setBackground(Color.WHITE);
		progressTitleLabel = new JLabel(threadName);
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setString("processing");
		this.add(progressTitleLabel);
		this.add(progressBar);
	}
}
