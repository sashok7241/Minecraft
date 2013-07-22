package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ConvertingProgressUpdate implements IProgressUpdate
{
	private long field_96245_b;
	final MinecraftServer mcServer;
	
	public ConvertingProgressUpdate(MinecraftServer par1MinecraftServer)
	{
		mcServer = par1MinecraftServer;
		field_96245_b = MinecraftServer.func_130071_aq();
	}
	
	@Override public void displayProgressMessage(String par1Str)
	{
	}
	
	@Override public void resetProgresAndWorkingMessage(String par1Str)
	{
	}
	
	@Override public void setLoadingProgress(int par1)
	{
		if(MinecraftServer.func_130071_aq() - field_96245_b >= 1000L)
		{
			field_96245_b = MinecraftServer.func_130071_aq();
			mcServer.getLogAgent().logInfo("Converting... " + par1 + "%");
		}
	}
}
