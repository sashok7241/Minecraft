package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RConThreadMain extends RConThreadBase
{
	private int rconPort;
	private int serverPort;
	private String hostname;
	private ServerSocket serverSocket = null;
	private String rconPassword;
	private Map clientThreads;
	
	public RConThreadMain(IServer p_i3408_1_)
	{
		super(p_i3408_1_);
		rconPort = p_i3408_1_.getIntProperty("rcon.port", 0);
		rconPassword = p_i3408_1_.getStringProperty("rcon.password", "");
		hostname = p_i3408_1_.getHostname();
		serverPort = p_i3408_1_.getPort();
		if(0 == rconPort)
		{
			rconPort = serverPort + 10;
			logInfo("Setting default rcon port to " + rconPort);
			p_i3408_1_.setProperty("rcon.port", Integer.valueOf(rconPort));
			if(0 == rconPassword.length())
			{
				p_i3408_1_.setProperty("rcon.password", "");
			}
			p_i3408_1_.saveProperties();
		}
		if(0 == hostname.length())
		{
			hostname = "0.0.0.0";
		}
		initClientThreadList();
		serverSocket = null;
	}
	
	private void cleanClientThreadsMap()
	{
		Iterator var1 = clientThreads.entrySet().iterator();
		while(var1.hasNext())
		{
			Entry var2 = (Entry) var1.next();
			if(!((RConThreadClient) var2.getValue()).isRunning())
			{
				var1.remove();
			}
		}
	}
	
	private void initClientThreadList()
	{
		clientThreads = new HashMap();
	}
	
	@Override public void run()
	{
		logInfo("RCON running on " + hostname + ":" + rconPort);
		try
		{
			while(running)
			{
				try
				{
					Socket var1 = serverSocket.accept();
					var1.setSoTimeout(500);
					RConThreadClient var2 = new RConThreadClient(server, var1);
					var2.startThread();
					clientThreads.put(var1.getRemoteSocketAddress(), var2);
					cleanClientThreadsMap();
				} catch(SocketTimeoutException var7)
				{
					cleanClientThreadsMap();
				} catch(IOException var8)
				{
					if(running)
					{
						logInfo("IO: " + var8.getMessage());
					}
				}
			}
		} finally
		{
			closeServerSocket(serverSocket);
		}
	}
	
	@Override public void startThread()
	{
		if(0 == rconPassword.length())
		{
			logWarning("No rcon password set in \'" + server.getSettingsFilename() + "\', rcon disabled!");
		} else if(0 < rconPort && 65535 >= rconPort)
		{
			if(!running)
			{
				try
				{
					serverSocket = new ServerSocket(rconPort, 0, InetAddress.getByName(hostname));
					serverSocket.setSoTimeout(500);
					super.startThread();
				} catch(IOException var2)
				{
					logWarning("Unable to initialise rcon on " + hostname + ":" + rconPort + " : " + var2.getMessage());
				}
			}
		} else
		{
			logWarning("Invalid rcon port " + rconPort + " found in \'" + server.getSettingsFilename() + "\', rcon disabled!");
		}
	}
}
