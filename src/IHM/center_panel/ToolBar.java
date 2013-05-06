package IHM.center_panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ToolBar extends JPanel{
	
	
	public ToolBar() {
		this.setPreferredSize(new Dimension(100, 600));
		this.setMinimumSize(new Dimension(100, 600));
		//this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

}
