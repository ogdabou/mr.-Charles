package plugin.basic;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

import plugin.IPlugin;

/**
 * InvertColor Epita.
 * We create an array of short representing colors value (256 bits)
 * LookupOp invert our bufferedImage with the valuesSet we sended to it.
 * @author ogda
 *
 */
public class InvertColor implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight()
				, img.getType());
		short[] colors = new short[256];
		for (int i = 0; i < 256; i++)
			colors[i] = (short)(256 - i);
		BufferedImageOp inverter = new LookupOp(new ShortLookupTable(0, colors),
				null);
		result = inverter.filter(img, result);
		return result;
	}

	@Override
	public String getName() {
		return "Invert Color";
	}

}
