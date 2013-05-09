package memento;

import java.awt.Dimension;
import java.io.Serializable;

import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import image.ImagePanel;
import logger.Logger;

/**
 * Par of the Memento design pattern used to do the history.
 * This class is where we store datas.
 * Each memento is sent to the CareTaker and is created by the
 * Originator.
 * @author ogda
 *
 */
public class Memento implements Serializable{
	/**
	 * 
	 */
	public static int ID;
	private static final long serialVersionUID = 1L;
	private ImagePanel savedImage;
	private String message;
	boolean valid = true;
	private int id = 0;
	
	public Memento(String msg, ImagePanel toSave) {
		message = msg;
		savedImage = toSave.getCopy();
		Memento.ID = Memento.ID + 1;
		id = Memento.ID;
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	public void setValid(boolean b)
	{
		valid = b;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public ImagePanel getState()
	{
		return savedImage;
	}
	
	public void print()
	{
		Logger.debug("Memento for " + savedImage.getName() 
					+ " / " + message);
	}
}
