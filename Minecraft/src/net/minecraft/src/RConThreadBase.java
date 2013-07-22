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
	
	RConThreadBase(IServer par1IServer)
	{
		server = par1IServer;
		if(server.isDebuggingEnabled())
		{
			logWarning("Debugging is enabled, performance maybe reduced!");
		}
	}
	
	protected void closeAllSockets()
	{
		closeAllSockets_do(false);
	}
	
	protected void closeAllSockets_do(boolean par1)
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
		if(par1 && 0 < var2)
		{
			logWarning("Force closed " + var2 + " sockets");
		}
	}
	
	protected boolean closeServerSocket(ServerSocket par1ServerSocket)
	{
		return closeServerSocket_do(par1ServerSocket, true);
	}
	
	protected boolean closeServerSocket_do(ServerSocket par1ServerSocket, boolean par2)
	{
		logDebug("closeSocket: " + par1ServerSocket);
		if(null == par1ServerSocket) return false;
		else
		{
			boolean var3 = false;
			try
			{
				if(!par1ServerSocket.isClosed())
				{
					par1ServerSocket.close();
					var3 = true;
				}
			} catch(IOException var5)
			{
				logWarning("IO: " + var5.getMessage());
			}
			if(par2)
			{
				serverSocketList.remove(par1ServerSocket);
			}
			return var3;
		}
	}
	
	protected boolean closeSocket(DatagramSocket par1DatagramSocket, boolean par2)
	{
		logDebug("closeSocket: " + par1DatagramSocket);
		if(null == par1DatagramSocket) return false;
		else
		{
			boolean var3 = false;
			if(!par1DatagramSocket.isClosed())
			{
				par1DatagramSocket.close();
				var3 = true;
			}
			if(par2)
			{
				socketList.remove(par1DatagramSocket);
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
	
	protected void logDebug(String par1Str)
	{
		server.logDebug(par1Str);
	}
	
	protected void logInfo(String par1Str)
	{
		server.logInfo(par1Str);
	}
	
	protected void logSevere(String par1Str)
	{
		server.logSevere(par1Str);
	}
	
	protected void logWarning(String par1Str)
	{
		server.logWarning(par1Str);
	}
	
	protected void registerSocket(DatagramSocket par1DatagramSocket)
	{
		logDebug("registerSocket: " + par1DatagramSocket);
		socketList.add(par1DatagramSocket);
	}
	
	public synchronized void startThread()
	{
		rconThread = new Thread(this);
		rconThread.start();
		running = true;
	}
}
