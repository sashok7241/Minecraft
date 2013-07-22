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
	public boolean connectionComplete = false;
	private int connectionTimer = 0;
	private String clientUsername = null;
	private volatile boolean field_72544_i = false;
	private String loginServerId = "";
	private boolean field_92079_k = false;
	private SecretKey sharedKey = null;
	
	public NetLoginHandler(MinecraftServer p_i3400_1_, Socket p_i3400_2_, String p_i3400_3_) throws IOException
	{
		mcServer = p_i3400_1_;
		myTCPConnection = new TcpConnection(p_i3400_1_.getLogAgent(), p_i3400_2_, p_i3400_3_, this, p_i3400_1_.getKeyPair().getPrivate());
		myTCPConnection.field_74468_e = 0;
	}
	
	public String getUsernameAndAddress()
	{
		return clientUsername != null ? clientUsername + " [" + myTCPConnection.getSocketAddress().toString() + "]" : myTCPConnection.getSocketAddress().toString();
	}
	
	@Override public void handleClientCommand(Packet205ClientCommand p_72458_1_)
	{
		if(p_72458_1_.forceRespawn == 0)
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
	
	@Override public void handleClientProtocol(Packet2ClientProtocol p_72500_1_)
	{
		clientUsername = p_72500_1_.getUsername();
		if(!clientUsername.equals(StringUtils.stripControlCodes(clientUsername)))
		{
			raiseErrorAndDisconnect("Invalid username!");
		} else
		{
			PublicKey var2 = mcServer.getKeyPair().getPublic();
			if(p_72500_1_.getProtocolVersion() != 61)
			{
				if(p_72500_1_.getProtocolVersion() > 61)
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
	
	@Override public void handleErrorMessage(String p_72515_1_, Object[] p_72515_2_)
	{
		mcServer.getLogAgent().logInfo(getUsernameAndAddress() + " lost connection");
		connectionComplete = true;
	}
	
	@Override public void handleLogin(Packet1Login p_72455_1_)
	{
	}
	
	@Override public void handleServerPing(Packet254ServerPing p_72467_1_)
	{
		try
		{
			ServerConfigurationManager var2 = mcServer.getConfigurationManager();
			String var3 = null;
			if(p_72467_1_.readSuccessfully == 1)
			{
				List var4 = Arrays.asList(new Serializable[] { Integer.valueOf(1), Integer.valueOf(61), mcServer.getMinecraftVersion(), mcServer.getMOTD(), Integer.valueOf(var2.getCurrentPlayerCount()), Integer.valueOf(var2.getMaxPlayers()) });
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
			} else
			{
				var3 = mcServer.getMOTD() + "\u00a7" + var2.getCurrentPlayerCount() + "\u00a7" + var2.getMaxPlayers();
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
	
	@Override public void handleSharedKey(Packet252SharedKey p_72513_1_)
	{
		PrivateKey var2 = mcServer.getKeyPair().getPrivate();
		sharedKey = p_72513_1_.getSharedKey(var2);
		if(!Arrays.equals(verifyToken, p_72513_1_.getVerifyToken(var2)))
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
	
	public void raiseErrorAndDisconnect(String p_72527_1_)
	{
		try
		{
			mcServer.getLogAgent().logInfo("Disconnecting " + getUsernameAndAddress() + ": " + p_72527_1_);
			myTCPConnection.addToSendQueue(new Packet255KickDisconnect(p_72527_1_));
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
	
	@Override public void unexpectedPacket(Packet p_72509_1_)
	{
		raiseErrorAndDisconnect("Protocol error");
	}
	
	static boolean func_72531_a(NetLoginHandler p_72531_0_, boolean p_72531_1_)
	{
		return p_72531_0_.field_72544_i = p_72531_1_;
	}
	
	static String getClientUsername(NetLoginHandler p_72533_0_)
	{
		return p_72533_0_.clientUsername;
	}
	
	static MinecraftServer getLoginMinecraftServer(NetLoginHandler p_72530_0_)
	{
		return p_72530_0_.mcServer;
	}
	
	static String getServerId(NetLoginHandler p_72526_0_)
	{
		return p_72526_0_.loginServerId;
	}
	
	static SecretKey getSharedKey(NetLoginHandler p_72525_0_)
	{
		return p_72525_0_.sharedKey;
	}
}
