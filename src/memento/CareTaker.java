package memento;

import image.ImagePanel;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import logger.Logger;

/**
 * Par of the Memento design pattern.
 * It controls the originator.
 * @author ogda
 *
 */
public class CareTaker implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public ArrayList<Memento> states = new ArrayList<Memento>();
	public int currentMementoIndex = 1;
	public Originator originator = new Originator();
	
	/**
	 * We save the current states
	 * @param message
	 */
	public void addToMemento(String message) 
	{
		Logger.debug(states.size() + " " + currentMementoIndex);
		if (currentMementoIndex >= states.size())
		{
			states.add(originator.saveInMemento(message));
			currentMementoIndex += 1;
		}
		else if (currentMementoIndex < states.size())
		{
			states.add(originator.saveInMemento(message));
			for(int i = states.size() - 2; i > currentMementoIndex; i--)
			{
				Memento m = states.get(i);
				Logger.debug("Removing " + m.getMessage() + " at index = " + i);
				states.remove(i);
			}
			currentMementoIndex += 1;
		}
	}
	
	/**
	 * 
	 */
	public void restoreToState(int stateIndex)
	{
		currentMementoIndex = stateIndex;
		Logger.debug("Rollback requested to: " + stateIndex);
		originator.restore(states.get(stateIndex));
	}
	
	public ArrayList<Memento> getList()
	{
		return states;
	}
}
