package saver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import logger.Logger;
import plugin.IPlugin;
import IHM.MainWindow;

public class ScriptSaver 
{
	public static void saveScript(MainWindow window, IPlugin script)
	{
		/*Logger.debug("Saving script: " + script.getName());

		File path = new File("plugin/scripts/" + script.getName() + ".myScr");
		if (path != null)
		{
			FileOutputStream output;
			try {
				output = new FileOutputStream(path);
				try {
					ObjectOutputStream objOutput = new ObjectOutputStream(output);
					objOutput.writeObject(script);
					objOutput.flush();
					Logger.debug("Script saved Serialized!");
					objOutput.close();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	/*public static Project openProject(MainWindow mainWindow, File file, JProgressBar bar)
	{
		ProjectOpener opener = new ProjectOpener(mainWindow, file, bar);
		opener.execute();
		return null;
	}*/
}
