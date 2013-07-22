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
	
	public ServerListenThread(NetworkListenThread p_i3384_1_, InetAddress p_i3384_2_, int p_i3384_3_) throws IOException
	{
		super("Listen thread");
		myNetworkListenThread = p_i3384_1_;
		myPort = p_i3384_3_;
		myServerSocket = new ServerSocket(p_i3384_3_, 0, p_i3384_2_);
		myServerAddress = p_i3384_2_ == null ? myServerSocket.getInetAddress() : p_i3384_2_;
		myServerSocket.setPerformancePreferences(0, 2, 1);
	}
	
	private void addPendingConnection(NetLoginHandler p_71764_1_)
	{
		if(p_71764_1_ == null) throw new IllegalArgumentException("Got null pendingconnection!");
		else
		{
			List var2 = pendingConnections;
			synchronized(pendingConnections)
			{
				pendingConnections.add(p_71764_1_);
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
	
	public void func_71769_a(InetAddress p_71769_1_)
	{
		if(p_71769_1_ != null)
		{
			HashMap var2 = recentConnections;
			synchronized(recentConnections)
			{
				recentConnections.remove(p_71769_1_);
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
