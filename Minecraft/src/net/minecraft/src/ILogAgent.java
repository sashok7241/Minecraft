package net.minecraft.src;

public interface ILogAgent
{
	void logFine(String var1);
	
	void logInfo(String var1);
	
	void logSevere(String var1);
	
	void logSevereException(String var1, Throwable var2);
	
	void logWarning(String var1);
	
	void logWarningException(String var1, Throwable var2);
	
	void logWarningFormatted(String var1, Object ... var2);
}
