package memento;

import logger.Logger;
import IHM.ImagePanel;

/**
 * Par fo the Memento Design pattern.
 * This class must create each memento.
 * It is controled by the CareTaker
 * @author ogda
 *
 */
public class Originator {

	ImagePanel currentState;
	
	public Originator()
	{
		Logger.debug("Creating a new Originator");
	}
	
	/**
	 * Set the current state of the program.
	 * We dont copy the image now, it will be copied
	 * only if we need to store this into a memento.
	 * @param image
	 */
	public void setCurrentState(ImagePanel image)
	{
		Logger.debug("Current state is: " + image.getName());
		currentState = image;
	}
	
	/**
	 * Save the current State into a Memento.
	 * In the memento, the ImagePanel will me copied;
	 * @param message
	 * @return
	 */
	public Memento saveInMemento(String message)
	{
		Logger.debug("Saving current state");
		return new Memento(message, currentState);
	}
	
	/**
	 * Restpre set the CurrentState to a previous Memento.
	 * @param memento
	 */
	public void restore(Memento memento)
	{
		Logger.debug("Rollback requested");
		currentState = memento.getState();
	}
}
