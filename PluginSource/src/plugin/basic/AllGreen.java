package plugin.basic;

import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class AllGreen implements IPlugin
{

	@Override
	public BufferedImage perform(BufferedImage img)
	{
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++)
				res.setRGB(i, j, 0x00ff00);
		return res;
	}

	@Override
	public String getName()
	{
		return "All green";
	}

}
