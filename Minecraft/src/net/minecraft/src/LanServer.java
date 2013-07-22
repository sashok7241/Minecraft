package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class LanServer
{
	private String lanServerMotd;
	private String lanServerIpPort;
	private long timeLastSeen;
	
	public LanServer(String par1Str, String par2Str)
	{
		lanServerMotd = par1Str;
		lanServerIpPort = par2Str;
		timeLastSeen = Minecraft.getSystemTime();
	}
	
	public String getServerIpPort()
	{
		return lanServerIpPort;
	}
	
	public String getServerMotd()
	{
		return lanServerMotd;
	}
	
	public void updateLastSeen()
	{
		timeLastSeen = Minecraft.getSystemTime();
	}
}
