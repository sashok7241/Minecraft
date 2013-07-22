package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ConvertingProgressUpdate implements IProgressUpdate
{
	private long field_96245_b;
	final MinecraftServer mcServer;
	
	public ConvertingProgressUpdate(MinecraftServer par1)
	{
		mcServer = par1;
		field_96245_b = System.currentTimeMillis();
	}
	
	@Override public void displayProgressMessage(String par1Str)
	{
	}
	
	@Override public void onNoMoreProgress()
	{
	}
	
	@Override public void resetProgresAndWorkingMessage(String par1Str)
	{
	}
	
	@Override public void resetProgressAndMessage(String par1Str)
	{
	}
	
	@Override public void setLoadingProgress(int par1)
	{
		if(System.currentTimeMillis() - field_96245_b >= 1000L)
		{
			field_96245_b = System.currentTimeMillis();
			mcServer.getLogAgent().logInfo("Converting... " + par1 + "%");
		}
	}
}
