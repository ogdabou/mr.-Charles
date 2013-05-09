package projects;


import image.ImagePanel;
import image.ImageViewer;

import java.awt.Dimension;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import com.sun.org.apache.bcel.internal.generic.JsrInstruction;

import logger.Logger;
import memento.CareTaker;

import IHM.HystoryScroller;

public class Project implements Serializable
{

	//TODO currently i have to store the JFILEchoose. but i may change it and using tab.getslectedindec() instead.
	private static final long serialVersionUID = -8160058076788488205L;
	
	private String name;
	private ArrayList<ImagePanel> imageList;
	private Map<JScrollPane, ImagePanel> scrollToImage;
	private String lastDirectory;
	private Map<ImagePanel, CareTaker> mementoList = new HashMap<ImagePanel,
			CareTaker>();
	
	/**
	 * 
	 * @param name
	 * @param assignedPane
	 * @param fileChooser
	 */
	public Project(String name)
	{
		scrollToImage = new HashMap<JScrollPane, ImagePanel>();
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
	 * TODO : Be careful, same-name images will have the same Memento
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

		CareTaker careTaker = new CareTaker();
		
		imageList.add(newImage);
		careTaker.originator.setCurrentState(newImage);
		careTaker.originator
			.saveInMemento(newImage.getName() + " loaded");
		careTaker.states.add(careTaker.originator.
				saveInMemento(newImage.getName() + " opened"));
		mementoList.put(newImage, careTaker);

		ImageViewer viewer = new ImageViewer(newImage);
		
		JScrollPane scroller = new JScrollPane(viewer);
		
		scroller.setLayout(new ScrollPaneLayout());
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.
				VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.
				HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollToImage.put(scroller, newImage);
		return scroller;
	}
	
	public JScrollPane loadImage(ImagePanel newImage)
	{
		Logger.debug("Adding image");

		newImage.setPreferredSize(new Dimension(newImage.getWidth(),
				newImage.getHeight()));
		newImage.setMinimumSize(new Dimension(newImage.getWidth(),
				newImage.getHeight()));

		ImageViewer viewer = new ImageViewer(newImage);
		
		JScrollPane scroller = new JScrollPane(viewer);
		
		scroller.setLayout(new ScrollPaneLayout());
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.
				VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.
				HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollToImage.put(scroller, newImage);
		return scroller;
	}

	/**
	 * TODO : Send something to add this into the panel!
	 * @param img
	 * @param msg
	 */
	public void addMemento(ImagePanel img, String msg)
	{
		mementoList.get(img).states.add
			(mementoList.get(img).originator.saveInMemento(msg));
		mementoList.get(img).originator.setCurrentState(img);
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
	
	/**
	 * 
	 * @return
	 */
	public int getListSize()
	{
		return imageList.size();
	}
	
	/**
	 * 
	 * @param path
	 */
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
	
	public CareTaker getCareTaker(ImagePanel img)
	{
		return mementoList.get(img);
	}
	
	public Map<ImagePanel, CareTaker> getMementoList()
	{
		return mementoList;
	}
	
	public ArrayList<ImagePanel> getImageList()
	{
		return imageList;
	}
	
	public String getDirectory()
	{
		return lastDirectory;
	}
	
	public String getImageFormatedName(int index)
	{
		return formatName(imageList.get(index).getFileName());
	}
	
	public ImagePanel getImage(JScrollPane jsp)
	{
		return scrollToImage.get(jsp);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
