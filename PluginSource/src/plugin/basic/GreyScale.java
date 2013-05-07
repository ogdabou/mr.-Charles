package plugin.basic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import plugin.IPlugin;

public class GreyScale implements IPlugin{

	/**
	 * GreyScale filter
	 */
	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics painter = result.getGraphics();
		painter.drawImage(img, 0, 0, null);
		painter.dispose();
		
		Graphics2D painter2D = (Graphics2D)painter;
		painter2D.drawImage(result, null, 0, 0);
		
	    /*BufferedImage convertedImg = new BufferedImage(result.getWidth(), 
	    		result.getHeight(), BufferedImage.TYPE_INT_RGB);
	    convertedImg.getGraphics().drawImage(result, 0, 0, null);
		
		return convertedImg;*/
		return result;
	}

	@Override
	public String getName() 
	{
		return "Gray Scale";
	}

}
