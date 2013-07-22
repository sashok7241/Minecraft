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
	
	public LogAgent(String p_i11036_1_, String p_i11036_2_, String p_i11036_3_)
	{
		serverLogger = Logger.getLogger(p_i11036_1_);
		loggerName = p_i11036_1_;
		loggerPrefix = p_i11036_2_;
		logFile = p_i11036_3_;
		setupLogger();
	}
	
	@Override public void logFine(String par1Str)
	{
		serverLogger.log(Level.FINE, par1Str);
	}
	
	@Override public void logInfo(String p_98233_1_)
	{
		serverLogger.log(Level.INFO, p_98233_1_);
	}
	
	@Override public void logSevere(String p_98232_1_)
	{
		serverLogger.log(Level.SEVERE, p_98232_1_);
	}
	
	@Override public void logSevereException(String p_98234_1_, Throwable p_98234_2_)
	{
		serverLogger.log(Level.SEVERE, p_98234_1_, p_98234_2_);
	}
	
	@Override public void logWarning(String p_98236_1_)
	{
		serverLogger.log(Level.WARNING, p_98236_1_);
	}
	
	@Override public void logWarningException(String p_98235_1_, Throwable p_98235_2_)
	{
		serverLogger.log(Level.WARNING, p_98235_1_, p_98235_2_);
	}
	
	@Override public void logWarningFormatted(String p_98231_1_, Object ... p_98231_2_)
	{
		serverLogger.log(Level.WARNING, p_98231_1_, p_98231_2_);
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
	
	static String func_98237_a(LogAgent p_98237_0_)
	{
		return p_98237_0_.loggerPrefix;
	}
}
