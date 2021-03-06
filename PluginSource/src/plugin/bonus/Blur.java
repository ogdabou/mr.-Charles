package plugin.bonus;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import plugin.IPlugin;

/**
 * Simple Blur algorithm using convolerOperator.
 * @author ogda
 *
 */
public class Blur implements IPlugin{

	@Override
	public BufferedImage perform(BufferedImage img) {
		float[] mask = new float[25];
		for (int i =0; i < 25; i++)
		{
			mask[i] = 1.0f/25.0f;
		}

		Kernel k = new Kernel(5, 5, mask);
		ConvolveOp convolver = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), img.getType());
		convolver.filter(img, result);
		return result;
	}

	@Override
	public String getName() {
		return "Blur";
	}

}
