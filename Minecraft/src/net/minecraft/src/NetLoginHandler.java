package net.minecraft.src;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.crypto.SecretKey;

import net.minecraft.server.MinecraftServer;

public class NetLoginHandler extends NetHandler
{
	private static Random rand = new Random();
	private byte[] verifyToken;
	private final MinecraftServer mcServer;
	public final TcpConnection myTCPConnection;
	public boolean connectionComplete;
	private int connectionTimer;
	private String clientUsername;
	private volatile boolean field_72544_i;
	private String loginServerId = "";
	private boolean field_92079_k;
	private SecretKey sharedKey;
	
	public NetLoginHandler(MinecraftServer par1MinecraftServer, Socket par2Socket, String par3Str) throws IOException
	{
		mcServer = par1MinecraftServer;
		myTCPConnection = new TcpConnection(par1MinecraftServer.getLogAgent(), par2Socket, par3Str, this, par1MinecraftServer.getKeyPair().getPrivate());
		myTCPConnection.field_74468_e = 0;
	}
	
	@Override public boolean func_142032_c()
	{
		return connectionComplete;
	}
	
	public String getUsernameAndAddress()
	{
		return clientUsername != null ? clientUsername + " [" + myTCPConnection.getSocketAddress().toString() + "]" : myTCPConnection.getSocketAddress().toString();
	}
	
	@Override public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand)
	{
		if(par1Packet205ClientCommand.forceRespawn == 0)
		{
			if(field_92079_k)
			{
				raiseErrorAndDisconnect("Duplicate login");
				return;
			}
			field_92079_k = true;
			if(mcServer.isServerInOnlineMode())
			{
				new ThreadLoginVerifier(this).start();
			} else
			{
				field_72544_i = true;
			}
		}
	}
	
	@Override public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol)
	{
		clientUsername = par1Packet2ClientProtocol.getUsername();
		if(!clientUsername.equals(StringUtils.stripControlCodes(clientUsername)))
		{
			raiseErrorAndDisconnect("Invalid username!");
		} else
		{
			PublicKey var2 = mcServer.getKeyPair().getPublic();
			if(par1Packet2ClientProtocol.getProtocolVersion() != 74)
			{
				if(par1Packet2ClientProtocol.getProtocolVersion() > 74)
				{
					raiseErrorAndDisconnect("Outdated server!");
				} else
				{
					raiseErrorAndDisconnect("Outdated client!");
				}
			} else
			{
				loginServerId = mcServer.isServerInOnlineMode() ? Long.toString(rand.nextLong(), 16) : "-";
				verifyToken = new byte[4];
				rand.nextBytes(verifyToken);
				myTCPConnection.addToSendQueue(new Packet253ServerAuthData(loginServerId, var2, verifyToken));
			}
		}
	}
	
	@Override public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj)
	{
		mcServer.getLogAgent().logInfo(getUsernameAndAddress() + " lost connection");
		connectionComplete = true;
	}
	
	@Override public void handleLogin(Packet1Login par1Packet1Login)
	{
	}
	
	@Override public void handleServerPing(Packet254ServerPing par1Packet254ServerPing)
	{
		try
		{
			ServerConfigurationManager var2 = mcServer.getConfigurationManager();
			String var3 = null;
			if(par1Packet254ServerPing.func_140050_d())
			{
				var3 = mcServer.getMOTD() + "\u00a7" + var2.getCurrentPlayerCount() + "\u00a7" + var2.getMaxPlayers();
			} else
			{
				List var4 = Arrays.asList(new Serializable[] { Integer.valueOf(1), Integer.valueOf(74), mcServer.getMinecraftVersion(), mcServer.getMOTD(), Integer.valueOf(var2.getCurrentPlayerCount()), Integer.valueOf(var2.getMaxPlayers()) });
				Object var6;
				for(Iterator var5 = var4.iterator(); var5.hasNext(); var3 = var3 + var6.toString().replaceAll("\u0000", ""))
				{
					var6 = var5.next();
					if(var3 == null)
					{
						var3 = "\u00a7";
					} else
					{
						var3 = var3 + "\u0000";
					}
				}
			}
			InetAddress var8 = null;
			if(myTCPConnection.getSocket() != null)
			{
				var8 = myTCPConnection.getSocket().getInetAddress();
			}
			myTCPConnection.addToSendQueue(new Packet255KickDisconnect(var3));
			myTCPConnection.serverShutdown();
			if(var8 != null && mcServer.getNetworkThread() instanceof DedicatedServerListenThread)
			{
				((DedicatedServerListenThread) mcServer.getNetworkThread()).func_71761_a(var8);
			}
			connectionComplete = true;
		} catch(Exception var7)
		{
			var7.printStackTrace();
		}
	}
	
	@Override public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey)
	{
		PrivateKey var2 = mcServer.getKeyPair().getPrivate();
		sharedKey = par1Packet252SharedKey.getSharedKey(var2);
		if(!Arrays.equals(verifyToken, par1Packet252SharedKey.getVerifyToken(var2)))
		{
			raiseErrorAndDisconnect("Invalid client reply");
		}
		myTCPConnection.addToSendQueue(new Packet252SharedKey());
	}
	
	public void initializePlayerConnection()
	{
		String var1 = mcServer.getConfigurationManager().allowUserToConnect(myTCPConnection.getSocketAddress(), clientUsername);
		if(var1 != null)
		{
			raiseErrorAndDisconnect(var1);
		} else
		{
			EntityPlayerMP var2 = mcServer.getConfigurationManager().createPlayerForUser(clientUsername);
			if(var2 != null)
			{
				mcServer.getConfigurationManager().initializeConnectionToPlayer(myTCPConnection, var2);
			}
		}
		connectionComplete = true;
	}
	
	@Override public boolean isServerHandler()
	{
		return true;
	}
	
	public void raiseErrorAndDisconnect(String par1Str)
	{
		try
		{
			mcServer.getLogAgent().logInfo("Disconnecting " + getUsernameAndAddress() + ": " + par1Str);
			myTCPConnection.addToSendQueue(new Packet255KickDisconnect(par1Str));
			myTCPConnection.serverShutdown();
			connectionComplete = true;
		} catch(Exception var3)
		{
			var3.printStackTrace();
		}
	}
	
	public void tryLogin()
	{
		if(field_72544_i)
		{
			initializePlayerConnection();
		}
		if(connectionTimer++ == 600)
		{
			raiseErrorAndDisconnect("Took too long to log in");
		} else
		{
			myTCPConnection.processReadPackets();
		}
	}
	
	@Override public void unexpectedPacket(Packet par1Packet)
	{
		raiseErrorAndDisconnect("Protocol error");
	}
	
	static boolean func_72531_a(NetLoginHandler par0NetLoginHandler, boolean par1)
	{
		return par0NetLoginHandler.field_72544_i = par1;
	}
	
	static String getClientUsername(NetLoginHandler par0NetLoginHandler)
	{
		return par0NetLoginHandler.clientUsername;
	}
	
	static MinecraftServer getLoginMinecraftServer(NetLoginHandler par0NetLoginHandler)
	{
		return par0NetLoginHandler.mcServer;
	}
	
	static String getServerId(NetLoginHandler par0NetLoginHandler)
	{
		return par0NetLoginHandler.loginServerId;
	}
	
	static SecretKey getSharedKey(NetLoginHandler par0NetLoginHandler)
	{
		return par0NetLoginHandler.sharedKey;
	}
}
