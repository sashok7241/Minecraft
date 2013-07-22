package net.minecraft.src;

public interface IServer
{
	String executeCommand(String var1);
	
	String[] getAllUsernames();
	
	int getCurrentPlayerCount();
	
	String getFolderName();
	
	String getHostname();
	
	int getIntProperty(String var1, int var2);
	
	int getMaxPlayers();
	
	String getMinecraftVersion();
	
	String getPlugins();
	
	int getPort();
	
	String getServerMOTD();
	
	String getSettingsFilename();
	
	String getStringProperty(String var1, String var2);
	
	boolean isDebuggingEnabled();
	
	void logDebug(String var1);
	
	void logInfo(String var1);
	
	void logSevere(String var1);
	
	void logWarning(String var1);
	
	void saveProperties();
	
	void setProperty(String var1, Object var2);
}
