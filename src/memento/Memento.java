package memento;

import logger.Logger;
import IHM.ImagePanel;

/**
 * Par of the Memento design pattern used to do the history.
 * This class is where we store datas.
 * Each memento is sent to the CareTaker and is created by the
 * Originator.
 * @author ogda
 *
 */
public class Memento {
	private ImagePanel savedImage;
	private String message;
	
	public Memento(String msg, ImagePanel toSave) {
		message = msg;
		savedImage = toSave.getCopy();
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
