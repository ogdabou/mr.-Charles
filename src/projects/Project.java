package projects;


import java.awt.Dimension;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;


import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import logger.Logger;

import IHM.ImagePanel;
import IHM.ImageViewer;

public class Project implements Serializable{

	//TODO currently i have to store the JFILEchoose. but i may change it and using tab.getslectedindec() instead.
	private static final long serialVersionUID = -8160058076788488205L;
	
	private String name;
	private ArrayList<ImagePanel> imageList;
	private String lastDirectory;
	
	/**
	 * 
	 * @param name
	 * @param assignedPane
	 * @param fileChooser
	 */
	public Project(String name)
	{
		lastDirectory = "";
		this.name = name;
		imageList = new ArrayList<ImagePanel>();
	}
	
	/**
	 * Add an image to the current prindexoject
	 * We use a Jpanel with a GridBagLayout to center the image.
	 * A JScrollPan to a scrollbars.
	 * We fixe the image ize in order to make the scroll panel
	 * to correctly center the image.
	 * TODO : get the right fileName
	 * @param path
	 */
	public JScrollPane addImage(File path)
	{
		Logger.debug("Adding image");
		
		ImagePanel newImage = new ImagePanel(path);
		newImage.setName(path.getName());
		newImage.setPreferredSize(new Dimension(newImage.getWidth(),
				newImage.getHeight()));
		newImage.setMinimumSize(new Dimension(newImage.getWidth(),
				newImage.getHeight()));
		imageList.add(newImage);
		ImageViewer viewer = new ImageViewer(newImage);
		
		JScrollPane scroller = new JScrollPane(viewer);
		scroller.setLayout(new ScrollPaneLayout());
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.
				VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.
				HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return scroller;
	}

	/**
	 * Return the name of the file without any path.
	 * @param name
	 * @return
	 */
	String formatName(String name)
	{
		String result = "";
		int cpt = 0;
		cpt = name.lastIndexOf('/');
		result = name.substring(cpt + 1);
		return result;
	}
	
	public int getListSize()
	{
		return imageList.size();
	}
	
	public void setDirectory(String path)
	{
		String result = path;
		int index = result.lastIndexOf("/");
		result = result.substring(0, index);
		if (!result.equals(lastDirectory))
		{
			Logger.debug("Working directory is now: " + result);
			lastDirectory = result;
		}
	}
	
	public String getDirectory()
	{
		return lastDirectory;
	}
	
	public String getImageFormatedName(int index)
	{
		return formatName(imageList.get(index).getFileName());
	}
	
	public ImagePanel getImage(int index)
	{
		return imageList.get(index);
	}
}
