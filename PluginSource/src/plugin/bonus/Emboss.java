package plugin.bonus;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import plugin.IPlugin;

public class Emboss implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img) {
		float[] mask = {-2.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 2.0f};
		Kernel k = new Kernel(3, 3, mask);
		ConvolveOp convolver = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), img.getType());
		convolver.filter(img, result);
		return result;
	}

	@Override
	public String getName() {
		return "Emboss";
	}

}
