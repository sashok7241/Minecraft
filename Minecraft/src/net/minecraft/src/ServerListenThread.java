package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ServerListenThread extends Thread
{
	private final List pendingConnections = Collections.synchronizedList(new ArrayList());
	private final HashMap recentConnections = new HashMap();
	private int connectionCounter = 0;
	private final ServerSocket myServerSocket;
	private NetworkListenThread myNetworkListenThread;
	private final InetAddress myServerAddress;
	private final int myPort;
	
	public ServerListenThread(NetworkListenThread par1NetworkListenThread, InetAddress par2InetAddress, int par3) throws IOException
	{
		super("Listen thread");
		myNetworkListenThread = par1NetworkListenThread;
		myPort = par3;
		myServerSocket = new ServerSocket(par3, 0, par2InetAddress);
		myServerAddress = par2InetAddress == null ? myServerSocket.getInetAddress() : par2InetAddress;
		myServerSocket.setPerformancePreferences(0, 2, 1);
	}
	
	private void addPendingConnection(NetLoginHandler par1NetLoginHandler)
	{
		if(par1NetLoginHandler == null) throw new IllegalArgumentException("Got null pendingconnection!");
		else
		{
			List var2 = pendingConnections;
			synchronized(pendingConnections)
			{
				pendingConnections.add(par1NetLoginHandler);
			}
		}
	}
	
	public void func_71768_b()
	{
		try
		{
			myServerSocket.close();
		} catch(Throwable var2)
		{
			;
		}
	}
	
	public void func_71769_a(InetAddress par1InetAddress)
	{
		if(par1InetAddress != null)
		{
			HashMap var2 = recentConnections;
			synchronized(recentConnections)
			{
				recentConnections.remove(par1InetAddress);
			}
		}
	}
	
	public InetAddress getInetAddress()
	{
		return myServerAddress;
	}
	
	public int getMyPort()
	{
		return myPort;
	}
	
	public void processPendingConnections()
	{
		List var1 = pendingConnections;
		synchronized(pendingConnections)
		{
			for(int var2 = 0; var2 < pendingConnections.size(); ++var2)
			{
				NetLoginHandler var3 = (NetLoginHandler) pendingConnections.get(var2);
				try
				{
					var3.tryLogin();
				} catch(Exception var6)
				{
					var3.raiseErrorAndDisconnect("Internal server error");
					myNetworkListenThread.getServer().getLogAgent().logWarningException("Failed to handle packet for " + var3.getUsernameAndAddress() + ": " + var6, var6);
				}
				if(var3.connectionComplete)
				{
					pendingConnections.remove(var2--);
				}
				var3.myTCPConnection.wakeThreads();
			}
		}
	}
	
	@Override public void run()
	{
		while(myNetworkListenThread.isListening)
		{
			try
			{
				Socket var1 = myServerSocket.accept();
				NetLoginHandler var2 = new NetLoginHandler(myNetworkListenThread.getServer(), var1, "Connection #" + connectionCounter++);
				addPendingConnection(var2);
			} catch(IOException var3)
			{
				var3.printStackTrace();
			}
		}
		myNetworkListenThread.getServer().getLogAgent().logInfo("Closing listening thread");
	}
}
