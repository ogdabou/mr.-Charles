package logger;

import entryPoint.Main;

public class Logger {
	
	public Logger() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param msg
	 */
	public static void debug(String msg)
	{
		if(Main.debug)
		{
			System.out.println("[DEBUG] " + msg);
		}
	}
	
	/**
	 * 
	 * @param msg
	 */
	public static void error(String msg)
	{
		if(Main.debug)
		{
			System.out.println("[ERROR] " + msg + "\n");
		}
	}
	
}
