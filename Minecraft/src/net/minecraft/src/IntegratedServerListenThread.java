package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;

import net.minecraft.server.MinecraftServer;

public class IntegratedServerListenThread extends NetworkListenThread
{
	private final MemoryConnection netMemoryConnection;
	private MemoryConnection theMemoryConnection;
	private String field_71759_e;
	private boolean field_71756_f = false;
	private ServerListenThread myServerListenThread;
	
	public IntegratedServerListenThread(IntegratedServer p_i3121_1_) throws IOException
	{
		super(p_i3121_1_);
		netMemoryConnection = new MemoryConnection(p_i3121_1_.getLogAgent(), (NetHandler) null);
	}
	
	public void func_71754_a(MemoryConnection par1MemoryConnection, String par2Str)
	{
		theMemoryConnection = par1MemoryConnection;
		field_71759_e = par2Str;
	}
	
	public String func_71755_c() throws IOException
	{
		if(myServerListenThread == null)
		{
			int var1 = -1;
			try
			{
				var1 = HttpUtil.func_76181_a();
			} catch(IOException var4)
			{
				;
			}
			if(var1 <= 0)
			{
				var1 = 25564;
			}
			try
			{
				myServerListenThread = new ServerListenThread(this, (InetAddress) null, var1);
				myServerListenThread.start();
			} catch(IOException var3)
			{
				throw var3;
			}
		}
		return myServerListenThread.getInetAddress().getHostAddress() + ":" + myServerListenThread.getMyPort();
	}
	
	public IntegratedServer getIntegratedServer()
	{
		return (IntegratedServer) super.getServer();
	}
	
	@Override public MinecraftServer getServer()
	{
		return getIntegratedServer();
	}
	
	public boolean isGamePaused()
	{
		return field_71756_f && netMemoryConnection.getPairedConnection().isConnectionActive() && netMemoryConnection.getPairedConnection().isGamePaused();
	}
	
	@Override public void networkTick()
	{
		if(theMemoryConnection != null)
		{
			EntityPlayerMP var1 = getIntegratedServer().getConfigurationManager().createPlayerForUser(field_71759_e);
			if(var1 != null)
			{
				netMemoryConnection.pairWith(theMemoryConnection);
				field_71756_f = true;
				getIntegratedServer().getConfigurationManager().initializeConnectionToPlayer(netMemoryConnection, var1);
			}
			theMemoryConnection = null;
			field_71759_e = null;
		}
		if(myServerListenThread != null)
		{
			myServerListenThread.processPendingConnections();
		}
		super.networkTick();
	}
	
	@Override public void stopListening()
	{
		super.stopListening();
		if(myServerListenThread != null)
		{
			getIntegratedServer().getLogAgent().logInfo("Stopping server connection");
			myServerListenThread.func_71768_b();
			myServerListenThread.interrupt();
			myServerListenThread = null;
		}
	}
}
