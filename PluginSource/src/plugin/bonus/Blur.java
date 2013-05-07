package plugin.bonus;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import plugin.IPlugin;

public class Blur implements IPlugin{

	@Override
	public BufferedImage perform(BufferedImage img) {
		float[] matrix = {.1111f, .1111f, .1111f,
				.1111f, .1111f, .1111f, 
				.1111f, .1111f, .1111f};

        int iw = img.getWidth();
        int ih = img.getHeight();
        BufferedImageOp bfo = null;
        AffineTransform at = new AffineTransform();
        at.scale(img.getWidth()/2.0/img.getWidth(), img.getHeight()/1.0/img.getHeight());
        Kernel k = new Kernel(3, 3, matrix);
        BufferedImage bimg = new BufferedImage(img.getWidth(),img.getHeight(),
        		BufferedImage.TYPE_INT_RGB);
        ConvolveOp convolver = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
        convolver.filter(img, bimg);
        bfo = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        
        Graphics2D big = img.createGraphics();
        big.drawImage(img, bfo, 0, 0);
        big.drawImage(bimg, bfo, img.getWidth()/2+3,0);
		
		return bimg;
	}

	@Override
	public String getName() {
		return "Blur";
	}

}
