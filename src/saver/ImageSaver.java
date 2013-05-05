package saver;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import logger.Logger;

import IHM.MainWindow;

import image.ImagePanel;

public class ImageSaver 
{
	public static void saveImage(ImagePanel image, MainWindow window)
	{
		Logger.debug("Saving image");
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Image formats (*.jpg, *.jpeg, *.gif, *.ico, *.bmp)",
				"jpeg", "gif", "ico", "bmp", "jpg");
		chooser.setFileFilter(filter);
		chooser.showSaveDialog(window);
		File path = chooser.getSelectedFile();
		if (path != null)
		{
			try {
				ImageIO.write(image.image,
						image.getName().substring(image.getName().lastIndexOf('.') + 1)
						, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
