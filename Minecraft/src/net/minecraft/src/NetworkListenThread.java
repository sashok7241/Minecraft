package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public abstract class NetworkListenThread
{
	private final MinecraftServer mcServer;
	private final List connections = Collections.synchronizedList(new ArrayList());
	public volatile boolean isListening = false;
	
	public NetworkListenThread(MinecraftServer p_i3402_1_) throws IOException
	{
		mcServer = p_i3402_1_;
		isListening = true;
	}
	
	public void addPlayer(NetServerHandler p_71745_1_)
	{
		connections.add(p_71745_1_);
	}
	
	public MinecraftServer getServer()
	{
		return mcServer;
	}
	
	public void networkTick()
	{
		for(int var1 = 0; var1 < connections.size(); ++var1)
		{
			NetServerHandler var2 = (NetServerHandler) connections.get(var1);
			try
			{
				var2.networkTick();
			} catch(Exception var5)
			{
				if(var2.netManager instanceof MemoryConnection)
				{
					CrashReport var4 = CrashReport.makeCrashReport(var5, "Ticking memory connection");
					throw new ReportedException(var4);
				}
				mcServer.getLogAgent().logWarningException("Failed to handle packet for " + var2.playerEntity.getEntityName() + "/" + var2.playerEntity.getPlayerIP() + ": " + var5, var5);
				var2.kickPlayerFromServer("Internal server error");
			}
			if(var2.connectionClosed)
			{
				connections.remove(var1--);
			}
			var2.netManager.wakeThreads();
		}
	}
	
	public void stopListening()
	{
		isListening = false;
	}
}
