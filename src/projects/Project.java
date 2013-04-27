package projects;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logger.Logger;

import IHM.ImagePanel;

public class Project implements Serializable{

	//TODO currently i have to store the JFILEchoose. but i may change it and using tab.getslectedindec() instead.
	private static final long serialVersionUID = -8160058076788488205L;
	
	private String name;
	private ArrayList<ImagePanel> imageList;
	
	/**
	 * 
	 * @param name
	 * @param assignedPane
	 * @param fileChooser
	 */
	public Project(String name)
	{
		this.name = name;
		imageList = new ArrayList<ImagePanel>();
	}
	
	/**
	 * Add an image to the current prindexoject
	 * TODO : get the right fileName
	 * @param path
	 */
	public JPanel addImage(File path)
	{
		Logger.debug("Adding image");
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BoxLayout(imagePanel, 0));
		imagePanel.setPreferredSize(new Dimension(500, 500));

		ImagePanel newImage = new ImagePanel(path);
		newImage.size(newImage.getWidth(), newImage.getHeight());
		imageList.add(newImage);
		imagePanel.add(newImage);
		return imagePanel;
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
	
	public String getImageFormatedName(int index)
	{
		return formatName(imageList.get(index).getFileName());
	}
	
	public ImagePanel getImage(int index)
	{
		return imageList.get(index);
	}
}
