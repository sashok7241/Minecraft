package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class LanServer
{
	private String lanServerMotd;
	private String lanServerIpPort;
	private long timeLastSeen;
	
	public LanServer(String p_i3119_1_, String p_i3119_2_)
	{
		lanServerMotd = p_i3119_1_;
		lanServerIpPort = p_i3119_2_;
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
