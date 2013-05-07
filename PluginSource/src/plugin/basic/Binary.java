package plugin.basic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import plugin.IPlugin;

public class Binary implements IPlugin{

	@Override
	public BufferedImage perform(BufferedImage img) {
		BufferedImage result = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		
		Graphics painter = result.getGraphics();
		painter.drawImage(img, 0, 0, null);
		painter.dispose();
		
		Graphics2D painter2D = (Graphics2D)painter;
		painter2D.drawImage(result, null, 0, 0);
		return result;
	}

	@Override
	public String getName() {
		return "Binary";
	}

}
