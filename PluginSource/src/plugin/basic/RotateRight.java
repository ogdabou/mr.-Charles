package plugin.basic;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * 
 */
import plugin.IPlugin;
public class RotateRight implements IPlugin{

	@Override
	public BufferedImage perform(BufferedImage img) {
		BufferedImage result = new BufferedImage(img.getHeight(),
				img.getWidth(), img.getType());
		
		Graphics2D printer = (Graphics2D)result.getGraphics();
		
		printer.rotate(Math.toRadians(-90), result.getWidth() / 2,
				result.getHeight() / 2);
		printer.translate(((result.getWidth()-img.getWidth()) /2),
				((result.getHeight()-img.getHeight()) /2));
		printer.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
		
		return result;
	}

	@Override
	public String getName() {
		return "Rotate Right";
	}

}