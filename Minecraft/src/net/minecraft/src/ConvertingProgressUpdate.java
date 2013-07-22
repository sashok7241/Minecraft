package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ConvertingProgressUpdate implements IProgressUpdate
{
	private long field_96245_b;
	final MinecraftServer mcServer;
	
	public ConvertingProgressUpdate(MinecraftServer p_i5002_1_)
	{
		mcServer = p_i5002_1_;
		field_96245_b = System.currentTimeMillis();
	}
	
	@Override public void displayProgressMessage(String p_73720_1_)
	{
	}
	
	@Override public void onNoMoreProgress()
	{
	}
	
	@Override public void resetProgresAndWorkingMessage(String p_73719_1_)
	{
	}
	
	@Override public void resetProgressAndMessage(String par1Str)
	{
	}
	
	@Override public void setLoadingProgress(int p_73718_1_)
	{
		if(System.currentTimeMillis() - field_96245_b >= 1000L)
		{
			field_96245_b = System.currentTimeMillis();
			mcServer.getLogAgent().logInfo("Converting... " + p_73718_1_ + "%");
		}
	}
}
