package net.minecraft.src;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogAgent implements ILogAgent
{
	private final Logger serverLogger;
	private final String logFile;
	private final String loggerName;
	private final String loggerPrefix;
	
	public LogAgent(String par1Str, String par2Str, String par3Str)
	{
		serverLogger = Logger.getLogger(par1Str);
		loggerName = par1Str;
		loggerPrefix = par2Str;
		logFile = par3Str;
		setupLogger();
	}
	
	@Override public void logFine(String par1Str)
	{
		serverLogger.log(Level.FINE, par1Str);
	}
	
	@Override public void logInfo(String par1Str)
	{
		serverLogger.log(Level.INFO, par1Str);
	}
	
	@Override public void logSevere(String par1Str)
	{
		serverLogger.log(Level.SEVERE, par1Str);
	}
	
	@Override public void logSevereException(String par1Str, Throwable par2Throwable)
	{
		serverLogger.log(Level.SEVERE, par1Str, par2Throwable);
	}
	
	@Override public void logWarning(String par1Str)
	{
		serverLogger.log(Level.WARNING, par1Str);
	}
	
	@Override public void logWarningException(String par1Str, Throwable par2Throwable)
	{
		serverLogger.log(Level.WARNING, par1Str, par2Throwable);
	}
	
	@Override public void logWarningFormatted(String par1Str, Object ... par2ArrayOfObj)
	{
		serverLogger.log(Level.WARNING, par1Str, par2ArrayOfObj);
	}
	
	private void setupLogger()
	{
		serverLogger.setUseParentHandlers(false);
		Handler[] var1 = serverLogger.getHandlers();
		int var2 = var1.length;
		for(int var3 = 0; var3 < var2; ++var3)
		{
			Handler var4 = var1[var3];
			serverLogger.removeHandler(var4);
		}
		LogFormatter var6 = new LogFormatter(this, (LogAgentINNER1) null);
		ConsoleHandler var7 = new ConsoleHandler();
		var7.setFormatter(var6);
		serverLogger.addHandler(var7);
		try
		{
			FileHandler var8 = new FileHandler(logFile, true);
			var8.setFormatter(var6);
			serverLogger.addHandler(var8);
		} catch(Exception var5)
		{
			serverLogger.log(Level.WARNING, "Failed to log " + loggerName + " to " + logFile, var5);
		}
	}
	
	static String func_98237_a(LogAgent par0LogAgent)
	{
		return par0LogAgent.loggerPrefix;
	}
}
