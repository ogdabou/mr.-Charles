package IHM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sun.nio.cs.HistoricallyNamedCharset;

import logger.Logger;
import memento.CareTaker;
import memento.Memento;

public class HystoryScroller extends JScrollPane implements MouseListener{
	private ArrayList<JLabel> stateList;
	private JPanel mainPanel;

	public HystoryScroller() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		stateList = new ArrayList<JLabel>();

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		this.getViewport().add(mainPanel);
	}
	
	
	public void redraw(CareTaker careTaker)
	{
		Logger.debug("Redrawing History");
		this.getViewport().removeAll();
		mainPanel.removeAll();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		for (Memento memento : careTaker.getList())
		{
			memento.print();
			JPanel gate = new JPanel();
			JLabel label = new JLabel(memento.getMessage());
			gate.add(label);
			gate.setPreferredSize(new Dimension(235, 20));
			gate.setMaximumSize(new Dimension(235, 20));
			gate.addMouseListener(this);
			mainPanel.add(gate);
		}
		this.getViewport().add(mainPanel);
		this.revalidate();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		Logger.debug("Clicking on history");
		JPanel l = (JPanel)e.getComponent();
		if (e.getClickCount() % 2 == 0)
			l.setBackground(Color.GRAY);
		else {
			l.setBackground(Color.WHITE);
		}
		//l.revalidate();
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
