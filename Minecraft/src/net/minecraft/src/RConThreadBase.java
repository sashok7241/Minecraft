package net.minecraft.src;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class RConThreadBase implements Runnable
{
	protected boolean running = false;
	protected IServer server;
	protected Thread rconThread;
	protected int field_72615_d = 5;
	protected List socketList = new ArrayList();
	protected List serverSocketList = new ArrayList();
	
	RConThreadBase(IServer p_i3404_1_)
	{
		server = p_i3404_1_;
		if(server.isDebuggingEnabled())
		{
			logWarning("Debugging is enabled, performance maybe reduced!");
		}
	}
	
	protected void closeAllSockets()
	{
		closeAllSockets_do(false);
	}
	
	protected void closeAllSockets_do(boolean p_72612_1_)
	{
		int var2 = 0;
		Iterator var3 = socketList.iterator();
		while(var3.hasNext())
		{
			DatagramSocket var4 = (DatagramSocket) var3.next();
			if(closeSocket(var4, false))
			{
				++var2;
			}
		}
		socketList.clear();
		var3 = serverSocketList.iterator();
		while(var3.hasNext())
		{
			ServerSocket var5 = (ServerSocket) var3.next();
			if(closeServerSocket_do(var5, false))
			{
				++var2;
			}
		}
		serverSocketList.clear();
		if(p_72612_1_ && 0 < var2)
		{
			logWarning("Force closed " + var2 + " sockets");
		}
	}
	
	protected boolean closeServerSocket(ServerSocket p_72608_1_)
	{
		return closeServerSocket_do(p_72608_1_, true);
	}
	
	protected boolean closeServerSocket_do(ServerSocket p_72605_1_, boolean p_72605_2_)
	{
		logDebug("closeSocket: " + p_72605_1_);
		if(null == p_72605_1_) return false;
		else
		{
			boolean var3 = false;
			try
			{
				if(!p_72605_1_.isClosed())
				{
					p_72605_1_.close();
					var3 = true;
				}
			} catch(IOException var5)
			{
				logWarning("IO: " + var5.getMessage());
			}
			if(p_72605_2_)
			{
				serverSocketList.remove(p_72605_1_);
			}
			return var3;
		}
	}
	
	protected boolean closeSocket(DatagramSocket p_72604_1_, boolean p_72604_2_)
	{
		logDebug("closeSocket: " + p_72604_1_);
		if(null == p_72604_1_) return false;
		else
		{
			boolean var3 = false;
			if(!p_72604_1_.isClosed())
			{
				p_72604_1_.close();
				var3 = true;
			}
			if(p_72604_2_)
			{
				socketList.remove(p_72604_1_);
			}
			return var3;
		}
	}
	
	protected int getNumberOfPlayers()
	{
		return server.getCurrentPlayerCount();
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	protected void logDebug(String p_72607_1_)
	{
		server.logDebug(p_72607_1_);
	}
	
	protected void logInfo(String p_72609_1_)
	{
		server.logInfo(p_72609_1_);
	}
	
	protected void logSevere(String p_72610_1_)
	{
		server.logSevere(p_72610_1_);
	}
	
	protected void logWarning(String p_72606_1_)
	{
		server.logWarning(p_72606_1_);
	}
	
	protected void registerSocket(DatagramSocket p_72601_1_)
	{
		logDebug("registerSocket: " + p_72601_1_);
		socketList.add(p_72601_1_);
	}
	
	public synchronized void startThread()
	{
		rconThread = new Thread(this);
		rconThread.start();
		running = true;
	}
}
