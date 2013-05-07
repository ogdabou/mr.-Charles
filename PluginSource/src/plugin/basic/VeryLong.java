package plugin.basic;

import java.awt.image.BufferedImage;
import java.util.Random;

import plugin.IPlugin;

public class VeryLong implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++)
				res.setRGB(i, j, new Random().nextInt());
		
		try 
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public String getName() 
	{
		return "Very long";
	}

}
