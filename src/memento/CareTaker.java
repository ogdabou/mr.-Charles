package memento;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import logger.Logger;
import IHM.ImagePanel;

/**
 * Par of the Memento design pattern.
 * It controls the originator.
 * @author ogda
 *
 */
public class CareTaker {
	
	public ArrayList<Memento> states = new ArrayList<Memento>();
	public Originator originator = new Originator();
	
	public void addToMemento(String message) 
	{
		states.add(originator.saveInMemento("Coucou les amis"));
	}
	
	/**
	 * 
	 */
	public void restoreToState(int stateIndex)
	{
		Logger.debug("Restoring pevious image");
		//JScrollPane scrollPanel = getCurrentScrollPane();
		ImagePanel image = states.get(stateIndex).getState();
		originator.restore(states.get(stateIndex));
		image = states.get(stateIndex).getState();	
	}
	
	public ArrayList<Memento> getList()
	{
		return states;
	}
}
