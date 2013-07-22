package net.minecraft.src;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryConnection implements INetworkManager
{
	private static final SocketAddress mySocketAddress = new InetSocketAddress("127.0.0.1", 0);
	private final List readPacketCache = Collections.synchronizedList(new ArrayList());
	private final ILogAgent field_98214_c;
	private MemoryConnection pairedConnection;
	private NetHandler myNetHandler;
	private boolean shuttingDown;
	private String shutdownReason = "";
	private Object[] field_74439_g;
	private boolean gamePaused;
	
	public MemoryConnection(ILogAgent par1ILogAgent, NetHandler par2NetHandler)
	{
		myNetHandler = par2NetHandler;
		field_98214_c = par1ILogAgent;
	}
	
	@Override public void addToSendQueue(Packet par1Packet)
	{
		if(!shuttingDown)
		{
			pairedConnection.processOrCachePacket(par1Packet);
		}
	}
	
	@Override public void closeConnections()
	{
		pairedConnection = null;
		myNetHandler = null;
	}
	
	public MemoryConnection getPairedConnection()
	{
		return pairedConnection;
	}
	
	@Override public SocketAddress getSocketAddress()
	{
		return mySocketAddress;
	}
	
	public boolean isConnectionActive()
	{
		return !shuttingDown && pairedConnection != null;
	}
	
	public boolean isGamePaused()
	{
		return gamePaused;
	}
	
	@Override public void networkShutdown(String par1Str, Object ... par2ArrayOfObj)
	{
		shuttingDown = true;
		shutdownReason = par1Str;
		field_74439_g = par2ArrayOfObj;
	}
	
	@Override public int packetSize()
	{
		return 0;
	}
	
	public void pairWith(MemoryConnection par1MemoryConnection)
	{
		pairedConnection = par1MemoryConnection;
		par1MemoryConnection.pairedConnection = this;
	}
	
	public void processOrCachePacket(Packet par1Packet)
	{
		if(par1Packet.canProcessAsync() && myNetHandler.canProcessPacketsAsync())
		{
			par1Packet.processPacket(myNetHandler);
		} else
		{
			readPacketCache.add(par1Packet);
		}
	}
	
	@Override public void processReadPackets()
	{
		int var1 = 2500;
		while(var1-- >= 0 && !readPacketCache.isEmpty())
		{
			Packet var2 = (Packet) readPacketCache.remove(0);
			var2.processPacket(myNetHandler);
		}
		if(readPacketCache.size() > var1)
		{
			field_98214_c.logWarning("Memory connection overburdened; after processing 2500 packets, we still have " + readPacketCache.size() + " to go!");
		}
		if(shuttingDown && readPacketCache.isEmpty())
		{
			myNetHandler.handleErrorMessage(shutdownReason, field_74439_g);
		}
	}
	
	@Override public void serverShutdown()
	{
		shuttingDown = true;
	}
	
	public void setGamePaused(boolean par1)
	{
		gamePaused = par1;
	}
	
	@Override public void setNetHandler(NetHandler par1NetHandler)
	{
		myNetHandler = par1NetHandler;
	}
	
	@Override public void wakeThreads()
	{
	}
}
