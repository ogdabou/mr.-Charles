package image;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ImageViewer extends JPanel{
	private ImagePanel image;
	
	public ImageViewer(ImagePanel image) {
		this.image = image;
		this.setLayout(new GridBagLayout());
		this.add(image);
	}

}
