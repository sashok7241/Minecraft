package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;

import net.minecraft.server.MinecraftServer;

public class DedicatedServerListenThread extends NetworkListenThread
{
	private final ServerListenThread theServerListenThread;
	
	public DedicatedServerListenThread(MinecraftServer par1MinecraftServer, InetAddress par2InetAddress, int par3) throws IOException
	{
		super(par1MinecraftServer);
		theServerListenThread = new ServerListenThread(this, par2InetAddress, par3);
		theServerListenThread.start();
	}
	
	public void func_71761_a(InetAddress par1InetAddress)
	{
		theServerListenThread.func_71769_a(par1InetAddress);
	}
	
	public DedicatedServer getDedicatedServer()
	{
		return (DedicatedServer) super.getServer();
	}
	
	@Override public MinecraftServer getServer()
	{
		return getDedicatedServer();
	}
	
	@Override public void networkTick()
	{
		theServerListenThread.processPendingConnections();
		super.networkTick();
	}
	
	@Override public void stopListening()
	{
		super.stopListening();
		theServerListenThread.func_71768_b();
		theServerListenThread.interrupt();
	}
}
