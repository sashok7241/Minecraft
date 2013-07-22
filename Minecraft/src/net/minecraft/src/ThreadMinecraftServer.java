package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ThreadMinecraftServer extends Thread
{
	final MinecraftServer theServer;
	
	public ThreadMinecraftServer(MinecraftServer p_i10043_1_, String p_i10043_2_)
	{
		super(p_i10043_2_);
		theServer = p_i10043_1_;
	}
	
	@Override public void run()
	{
		theServer.run();
	}
}
