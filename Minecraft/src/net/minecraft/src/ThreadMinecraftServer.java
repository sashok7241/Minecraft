package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ThreadMinecraftServer extends Thread
{
	final MinecraftServer theServer;
	
	public ThreadMinecraftServer(MinecraftServer par1MinecraftServer, String par2Str)
	{
		super(par2Str);
		theServer = par1MinecraftServer;
	}
	
	@Override public void run()
	{
		theServer.run();
	}
}
