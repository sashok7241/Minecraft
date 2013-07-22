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
	
	@Override public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
	{
		return null;
	}
	
	@Override public File getMapFileFromName(String par1Str)
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
	
	@Override public void saveWorldInfo(WorldInfo par1WorldInfo)
	{
	}
	
	@Override public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound)
	{
	}
}
