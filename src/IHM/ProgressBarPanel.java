package IHM;



import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarPanel extends JPanel{

	private JProgressBar progressBar;
	private JLabel progressTitleLabel;
	private String progressTitle;
	private JButton stopButton;
	
	public ProgressBarPanel(String threadName, MainWindow mainWindow) {
		this.setBackground(Color.WHITE);
		progressTitleLabel = new JLabel(threadName);
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setString("processing");
		progressBar.setPreferredSize(new Dimension(85, 20));
		stopButton = new JButton("X");
		stopButton.setPreferredSize(new Dimension(20, 20));
		stopButton.addActionListener(mainWindow);
		this.add(progressTitleLabel);
		this.add(progressBar);
		this.add(stopButton);

	}
}
