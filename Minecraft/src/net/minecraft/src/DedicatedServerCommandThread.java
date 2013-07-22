package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DedicatedServerCommandThread extends Thread
{
	final DedicatedServer server;
	
	DedicatedServerCommandThread(DedicatedServer par1DedicatedServer)
	{
		server = par1DedicatedServer;
	}
	
	@Override public void run()
	{
		BufferedReader var1 = new BufferedReader(new InputStreamReader(System.in));
		String var2;
		try
		{
			while(!server.isServerStopped() && server.isServerRunning() && (var2 = var1.readLine()) != null)
			{
				server.addPendingCommand(var2, server);
			}
		} catch(IOException var4)
		{
			var4.printStackTrace();
		}
	}
}
