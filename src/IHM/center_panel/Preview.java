package IHM.center_panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import image.ImagePanel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import logger.Logger;

/**
 * Preview window.
 * When the mouse comes over a tab, we just draw its imagePanel
 * on the preview window and setVisible it.
 * @author ogda
 *
 */
public class Preview extends JPopupMenu{

	private ImagePanel content;
	private JPanel panel;
	
	public Preview()
	{
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setOpaque(true);
	}
	
	public void updateContent(ImagePanel image)
	{
		this.removeAll();

		Dimension size = image.getSize();
		double factor = 160 / size.getWidth();

		
		int width = (int)Math.round(image.getWidth() * factor);
		int height = (int)Math.round(image.getHeight() * factor);
		this.setPreferredSize(new Dimension(width,
				height));

		
       BufferedImage bf = new BufferedImage(width,
                height,
                BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D graphics = bf.createGraphics();
        
        graphics.scale(factor, factor);
         
        image.paint(graphics);
        
        if (content == null)
        	content = new ImagePanel(bf, image.getName());
        else
        	content.image = bf;

		content.setPreferredSize(new Dimension(width,
				height));

		panel.add(content);
		this.add(panel);
		if (!isVisible())
			this.setVisible(true);
		else
			this.repaint();
	}
}
