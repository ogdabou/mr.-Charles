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
	public ArrayList<Memento> ahead = new ArrayList<Memento>();
	public int currentMementoIndex = 1;
	public Originator originator = new Originator();
	private boolean asRestored = false;
	
	/**
	 * We save the current states
	 * @param message
	 */
	public boolean addToMemento(String message) 
	{
		Logger.debug(states.size() + " " + currentMementoIndex);
		if (currentMementoIndex >= states.size())
		{
			states.add(originator.saveInMemento(message));
			currentMementoIndex += 1;
			return true;
		}
		else
		{
			for (int i = 0; i < ahead.size(); i++)
			{
				states.remove(ahead.get(i));
			}
			ahead.clear();
			/*for(int i = states.size() - 1; i > currentMementoIndex; i--)
			{
				Memento m = states.get(i);
				Logger.debug("Removing " + m.getMessage() + " at index = " + i);
				states.remove(i);
			}*/
			states.add(originator.saveInMemento(message));
			currentMementoIndex += 1;
			return false;
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
		
		for (int i = stateIndex + 1; i < states.size(); i++)
		{
			ahead.clear();
			ahead.add(states.get(i));
		}
	}
	
	public ArrayList<Memento> getList()
	{
		return states;
	}
}
