package subWindows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import logger.Logger;
import plugin.IPlugin;
import saver.ScriptSaver;
import IHM.MainWindow;

public class ScriptWindow extends JFrame implements DragSourceListener, DragGestureListener,
	MouseListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel dnd = new JPanel();
	private JList pluginJList = new JList();
	private JList<String> choosentJList = new JList<String>();
	private Map<String, IPlugin> pluginList = new HashMap<String, IPlugin>();
	private String[] pluginNames;
	private ArrayList<String> stringList = new ArrayList<String>();
	private Transferable draged;
	private JButton create;
	private JButton cancel;
	private JTextField name = new JTextField();
	DefaultListModel<String> model;
	JScrollPane chooseScroller = new JScrollPane();
	private JPanel namePanel;
	private JLabel textlabel;
	private JPanel buttonPanel;
	private JPanel splitContainer;
	
	private DragSource dragSource;
	private MainWindow window;
	private JSplitPane splitPane;
	
	public ScriptWindow(MainWindow window)
	{
		splitContainer = new JPanel();
		buttonPanel = new JPanel();
		namePanel = new JPanel();
		namePanel.setPreferredSize(new Dimension(300, 60));
		namePanel.setMaximumSize(new Dimension(400, 60));
		textlabel = new JLabel("Script Name");
		name.setPreferredSize(new Dimension(200, 25));
		namePanel.add(textlabel);
		namePanel.add(name);
		this.window = window;
		this.setMinimumSize(new Dimension(450, 600));
		this.setPreferredSize(new Dimension(450, 600));
		this.setMaximumSize(new Dimension(450, 600));
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		create = new JButton("Create");
		create.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		buttonPanel.add(create);
		buttonPanel.add(cancel);
		this.setTitle("Script creator");
		dnd.setLayout(new BoxLayout(dnd, BoxLayout.PAGE_AXIS));
		this.add(dnd);
		this.setLocationRelativeTo(null);
	}
	
	public void setPluginList(ArrayList<IPlugin> pluginList)
	{
		stringList.clear();
		for (IPlugin i : pluginList)
		{
			this.pluginList.put(i.getName(), i);
		}
		pluginNames = this.pluginList.keySet().toArray(new String[0]);
		pluginJList = new JList(this.pluginNames);
		pluginJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		choosentJList = new JList<String>(new DefaultListModel<String>());
		dragSource = new DragSource();
		DragGestureRecognizer recognizer = dragSource.
				createDefaultDragGestureRecognizer(pluginJList,
		        DnDConstants.ACTION_COPY, this);
		if(splitPane != null)
			splitPane.removeAll();
		dnd.removeAll();
		chooseScroller.removeAll();
		chooseScroller.getViewport().add(choosentJList);
		//choosentJList.scrollRectToVisible(getBounds());
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
				,new JScrollPane(pluginJList) , new JScrollPane(choosentJList));
		splitPane.setPreferredSize(new Dimension(400, 400));
		splitPane.setMinimumSize(new Dimension(400, 400));
		splitPane.setMaximumSize(new Dimension(400, 400));

		splitPane.setDividerLocation(200);

		initSplit();
		splitContainer.removeAll();
		splitContainer.add(splitPane);
		splitContainer.setMaximumSize(new Dimension(400, 600));
		dnd.add(namePanel);
		dnd.add(splitContainer);
		dnd.add(buttonPanel);
		pluginJList.addMouseListener(this);
		
		
		this.setVisible(true);
	}
	
	public void initSplit()
	{
		splitPane.setBackground(Color.WHITE);
		splitPane.setOneTouchExpandable(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void dragGestureRecognized(DragGestureEvent e) {
		draged = new StringSelection(pluginJList.getSelectedValue().toString());
	    dragSource.startDrag(e, DragSource.DefaultCopyDrop, draged, this);
		
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if((e.getClickCount() % 2) == 0)
		{
			//int clickedIndex = pluginJList.locationToIndex(e.getPoint());
			Logger.debug("Added " + (String)pluginJList.getSelectedValue());
			stringList.add((String)pluginJList.getSelectedValue());
			pluginNames = stringList.toArray(new String[0]);
			model = new DefaultListModel<String>();
			for (String s : pluginNames)
			{
				model.addElement(s);
			}
			choosentJList.setModel(model);
			choosentJList.setPreferredSize(new Dimension(200, pluginNames.length * 28));
			this.revalidate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = (String)arg0.getActionCommand();
		if (name.equals("Create"))
		{
			ArrayList<IPlugin> list = new ArrayList<IPlugin>();
			for (String s : pluginNames)
			{
				list.add(pluginList.get(s));
			}
			if(choosentJList != null)
			{
				JMenuItem newScript = new JMenuItem(this.name.getText());
				newScript.addActionListener(window);
				window.getScriptMenu().add(newScript);
				Script script = new Script(list, this.name.getText());
				window.addPlugin((IPlugin)script);
				model = (DefaultListModel<String>)choosentJList.getModel();
				model.removeAllElements();
				ScriptSaver.saveScript(window, script);
			}
			this.setVisible(false);
			
		}
		else if (name.equals("Cancel"))
		{
			if(choosentJList != null)
			{
				model = (DefaultListModel<String>)choosentJList.getModel();
				model.removeAllElements();
				choosentJList.setModel(model);
				choosentJList = new JList<String>(new DefaultListModel<String>());
			}
			this.setVisible(false);
		}
	}
}

class Script implements IPlugin
{
	private ArrayList<IPlugin> pList;
	private String name;
	public Script(ArrayList<IPlugin> pluginList, String name)
	{
		this.name = name;
		pList = pluginList;
	}

	@Override
	public BufferedImage perform(BufferedImage img) {
		for(IPlugin plugin : pList)
		{
			img = plugin.perform(img);
		}
		return img;
	}

	@Override
	public String getName() {
		return name;
	}
	
}