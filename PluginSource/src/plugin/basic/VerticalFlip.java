package plugin.basic;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import plugin.IPlugin;
public class VerticalFlip implements IPlugin{

	@Override
	public BufferedImage perform(BufferedImage img) {
		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), img.getColorModel().getTransparency());
		Graphics2D printer = result.createGraphics();
		printer.drawImage(img, 0, 0, img.getWidth(),
				img.getHeight(), 0, img.getHeight(), img.getWidth(), 0, null);
		printer.dispose();
		
		
		
		return result;
	}

	@Override
	public String getName() {
		return "Vertical Flip";
	}

}
