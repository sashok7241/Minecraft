package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;

import net.minecraft.server.MinecraftServer;

public class DedicatedServerListenThread extends NetworkListenThread
{
	private final ServerListenThread theServerListenThread;
	
	public DedicatedServerListenThread(MinecraftServer p_i3383_1_, InetAddress p_i3383_2_, int p_i3383_3_) throws IOException
	{
		super(p_i3383_1_);
		theServerListenThread = new ServerListenThread(this, p_i3383_2_, p_i3383_3_);
		theServerListenThread.start();
	}
	
	public void func_71761_a(InetAddress p_71761_1_)
	{
		theServerListenThread.func_71769_a(p_71761_1_);
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
