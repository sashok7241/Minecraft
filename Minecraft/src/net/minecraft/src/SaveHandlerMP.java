package net.minecraft.src;

import java.io.File;

public class SaveHandlerMP implements ISaveHandler
{
	@Override public void checkSessionLock() throws MinecraftException
	{
	}
	
	@Override public void flush()
	{
	}
	
	@Override public IChunkLoader getChunkLoader(WorldProvider p_75763_1_)
	{
		return null;
	}
	
	@Override public File getMapFileFromName(String p_75758_1_)
	{
		return null;
	}
	
	@Override public IPlayerFileData getSaveHandler()
	{
		return null;
	}
	
	@Override public String getWorldDirectoryName()
	{
		return "none";
	}
	
	@Override public WorldInfo loadWorldInfo()
	{
		return null;
	}
	
	@Override public void saveWorldInfo(WorldInfo p_75761_1_)
	{
	}
	
	@Override public void saveWorldInfoWithPlayer(WorldInfo p_75755_1_, NBTTagCompound p_75755_2_)
	{
	}
}
