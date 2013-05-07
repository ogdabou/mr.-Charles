package plugin;

import java.awt.MenuItem;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import IHM.MainWindow;

import logger.Logger;

public class JarLoader {
	
	private JarFile jarFile;
	private Enumeration e;
	private URLClassLoader classloader;
	private File pluginFolder;
	// TODO try to separate this, handling observer pattern
	private JMenu bonus;
	private JMenu mandatory;
	private MainWindow mainWindow;
	
	public JarLoader(JMenu bonus, JMenu mandatory, MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		this.bonus = bonus;
		this.mandatory = mandatory;
		pluginFolder = new File("plugin/");
	}
	
	public void load()
	{
		for (File current : pluginFolder.listFiles())
		{
				Logger.debug(current.getPath());
	
			try {
				try {
					jarFile = new JarFile(current);
				}
				catch (IOException e1) 
				{
					Logger.error("IO exception while trying to open JAR at :" + current);
				}
				e = jarFile.entries();
				URL[] urls = { new URL("jar:file:" + current + "!/") };
				classloader = URLClassLoader.newInstance(urls);
				JarEntry jarEntry = null;
				while (e.hasMoreElements())
				{
					jarEntry = (JarEntry) e.nextElement();
					if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class"))
					{
						continue;
					}
					else
					{
					String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6 );
					className = className.replaceAll("/", ".");
					try {
						Class result = classloader.loadClass(className);
						Logger.debug("Plugin added :" + result.getName());
						addFilter(result, className);
						
					} catch (ClassNotFoundException e1) {
						Logger.error("ClassNotFoundException in JarLoader.java");
					}
					Logger.debug("loading class: " + className);
					}
				}
			} catch (MalformedURLException e) {
				Logger.error("MalformedExcpetion in JarLoader.java");
			}
		}
	}
	
	private void addFilter(Class result, String className)
	{
		IPlugin newPlugin;
		try {
			newPlugin = (IPlugin)result.newInstance();
			JMenuItem newFilterMenu = new JMenuItem(newPlugin.getName());
			newFilterMenu.addActionListener(mainWindow);
			mainWindow.addFilter(newPlugin);

			if (className.contains("basic"))
				mandatory.add(newFilterMenu);
			else
				bonus.add(newFilterMenu);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
