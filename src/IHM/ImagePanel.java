package IHM;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logger.Logger;

/**
 * We give you this class to help you display images.
 * You are free to use it or not, to modify it.
 */
public class ImagePanel extends JPanel implements Serializable
{

	private static final long serialVersionUID = -314171089120047242L;
	private String fileName;
	private final int width;
	private final int height;
	private final int imageType;
	private final int[] pixels;
	public transient BufferedImage image;
	public transient BufferedImage view;
	private BufferedImage source;

	/**
	 * Create the ImagePanel
	 *
	 * @param image: image to display
	 * @param name: name of the image
	 */
	public ImagePanel(BufferedImage image, String name)
	{
		fileName = name;
		this.image = image;
		source = image;
		width = image.getWidth();
		height = image.getHeight();
		imageType = image.getType();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}

	/**
	 * Create the ImagePanel
	 *
	 * @param file: image to display
	 */
	public ImagePanel(File file)
	{
		try
		{
			image = ImageIO.read(file);
			fileName = file.getPath();
			Logger.debug("new image : " + fileName);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		width = image.getWidth();
		height = image.getHeight();
		imageType = image.getType();
		pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}

	public void size(int width, int height)
	{
		Image current = image.getScaledInstance(500, 600, 
				Image.SCALE_DEFAULT);
		BufferedImage nImage = new BufferedImage(current.getWidth(null),
				current.getHeight(null),
				image.getType());
		view = nImage;
	}
	
	/**
	 * Create the bufferImage after deserialization.
	 */
	public void buildImage()
	{
		image = new BufferedImage(width, height, imageType);
		image.setRGB(0, 0, width, height, pixels, 0, width);
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

}
