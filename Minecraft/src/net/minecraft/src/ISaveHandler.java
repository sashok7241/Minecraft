package net.minecraft.src;

import java.io.File;

public interface ISaveHandler
{
	void checkSessionLock() throws MinecraftException;
	
	void flush();
	
	IChunkLoader getChunkLoader(WorldProvider var1);
	
	File getMapFileFromName(String var1);
	
	IPlayerFileData getSaveHandler();
	
	String getWorldDirectoryName();
	
	WorldInfo loadWorldInfo();
	
	void saveWorldInfo(WorldInfo var1);
	
	void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2);
}
