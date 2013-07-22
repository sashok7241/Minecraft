package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.server.MinecraftServer;

public class CallableServerMemoryStats implements Callable
{
	final MinecraftServer mcServer;
	
	public CallableServerMemoryStats(MinecraftServer p_i5009_1_)
	{
		mcServer = p_i5009_1_;
	}
	
	@Override public Object call()
	{
		return callServerMemoryStats();
	}
	
	public String callServerMemoryStats()
	{
		return MinecraftServer.getServerConfigurationManager(mcServer).getCurrentPlayerCount() + " / " + MinecraftServer.getServerConfigurationManager(mcServer).getMaxPlayers() + "; " + MinecraftServer.getServerConfigurationManager(mcServer).playerEntityList;
	}
}
