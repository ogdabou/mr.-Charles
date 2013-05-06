package IHM.center_panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sun.nio.cs.HistoricallyNamedCharset;

import logger.Logger;
import memento.CareTaker;
import memento.Memento;

/**
 * This class is here to display the history of each image.
 * @author ogda
 *
 */
public class HystoryScroller extends JScrollPane implements MouseListener{
	private Map<JLabel, Integer> stateList;
	private Map<JLabel, CareTaker> takerList;
	private JPanel mainPanel;
	private PrimaryPanel centerPanel;

	public HystoryScroller() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		stateList = new HashMap<JLabel, Integer>();
		takerList = new HashMap<JLabel, CareTaker>();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		this.getViewport().add(mainPanel);
	}
	
	public void setPrimaryPanel(PrimaryPanel p)
	{
		centerPanel = p;
	}
	
	/**
	 * The method redraw is called each time the currentImage careTaker changes
	 * @param careTaker
	 */
	public void redraw(CareTaker careTaker)
	{
		Logger.debug("Redrawing History");
		this.getViewport().removeAll();
		mainPanel.removeAll();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		int cpt = 0;
		if(careTaker != null)
		{
		for (Memento memento : careTaker.getList())
		{
			memento.print();
			JPanel gate = new JPanel();
			JLabel label = new JLabel(memento.getMessage());
			if (!memento.isValid())
				label.setForeground(Color.LIGHT_GRAY);
			if (cpt > careTaker.currentMementoIndex)
				label.setForeground(Color.LIGHT_GRAY);
			gate.add(label);
			gate.setPreferredSize(new Dimension(235, 20));
			gate.setMaximumSize(new Dimension(235, 20));
			gate.addMouseListener(this);
			mainPanel.add(gate);
			stateList.put(label, cpt);
			takerList.put(label, careTaker);
			cpt += 1;
		}
		this.getViewport().add(mainPanel);
		this.repaint();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		Logger.debug("Restoring requested");
		
		JPanel l = (JPanel)e.getComponent();
		if (e.getClickCount() == 2)
		{
			l.setBackground(Color.GRAY);
			JLabel label = (JLabel)l.getComponent(0);
			CareTaker c = takerList.get(label);
			c.restoreToState(stateList.get(label));
			Logger.debug("Restoring to sate " + stateList.get(label) +
					" msg "+ c.originator.getMessage());
			centerPanel.setCurrentImage(c.originator.currentState);
			centerPanel.repaint();
			this.redraw(c);
		}
		else {
			l.setBackground(Color.WHITE);
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		JPanel l = (JPanel)e.getComponent();
		//this.revalidate();
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
