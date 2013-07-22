package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.server.MinecraftServer;

public class CallableServerMemoryStats implements Callable
{
	final MinecraftServer mcServer;
	
	public CallableServerMemoryStats(MinecraftServer par1MinecraftServer)
	{
		mcServer = par1MinecraftServer;
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
