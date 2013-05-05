package saver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import logger.Logger;

import projects.Project;

import IHM.MainWindow;

public class ProjectSaver 
{
	
	public static void saveProject(Project project, MainWindow window)
	{
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Save in *.myPSD",
				"myPSd");
		chooser.setFileFilter(filter);
		chooser.showSaveDialog(window);
		File path = chooser.getSelectedFile();
		Logger.debug("Saving project");
		if (path != null)
		{
			FileOutputStream output;
			try {
				output = new FileOutputStream(path);
				try {
					ObjectOutputStream objOutput = new ObjectOutputStream(output);
					
					objOutput.writeObject(project);
					objOutput.flush();
					Logger.debug("Project Serialized!");
					objOutput.close();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Project openProject(File file)
	{
		Project project = null;
		try {
			FileInputStream input = new FileInputStream(file);
			ObjectInputStream objInput= new ObjectInputStream(input);
			try {	
				project = (Project) objInput.readObject(); 
				if (project == null)
					Logger.debug("project failed");
			} finally {
				try {
					objInput.close();
				} finally {
					input.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		if(project != null) {
			Logger.debug("Project loaded");
		}
		return project;
	}

}
