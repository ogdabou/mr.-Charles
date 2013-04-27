package entryPoint;

import IHM.MainWindow;

public class Main
{
	public static boolean debug = false;
	
	public static void main(String[] args)
	{
		for (String arg : args)
		{
			if (arg.equals("--debug"))
			{
				debug = true;
			}
		}
		
		MainWindow window = new MainWindow();
	}
}
