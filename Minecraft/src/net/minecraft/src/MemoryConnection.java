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
	private boolean shuttingDown = false;
	private String shutdownReason = "";
	private Object[] field_74439_g;
	private boolean gamePaused = false;
	
	public MemoryConnection(ILogAgent p_i11025_1_, NetHandler p_i11025_2_)
	{
		myNetHandler = p_i11025_2_;
		field_98214_c = p_i11025_1_;
	}
	
	@Override public void addToSendQueue(Packet p_74429_1_)
	{
		if(!shuttingDown)
		{
			pairedConnection.processOrCachePacket(p_74429_1_);
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
	
	@Override public void networkShutdown(String p_74424_1_, Object ... p_74424_2_)
	{
		shuttingDown = true;
		shutdownReason = p_74424_1_;
		field_74439_g = p_74424_2_;
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
	
	public void processOrCachePacket(Packet p_74436_1_)
	{
		if(p_74436_1_.canProcessAsync() && myNetHandler.canProcessPacketsAsync())
		{
			p_74436_1_.processPacket(myNetHandler);
		} else
		{
			readPacketCache.add(p_74436_1_);
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
	
	@Override public void setNetHandler(NetHandler p_74425_1_)
	{
		myNetHandler = p_74425_1_;
	}
	
	@Override public void wakeThreads()
	{
	}
}
