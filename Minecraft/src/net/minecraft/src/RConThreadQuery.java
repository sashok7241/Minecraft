package net.minecraft.src;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RConThreadQuery extends RConThreadBase
{
	private long lastAuthCheckTime;
	private int queryPort;
	private int serverPort;
	private int maxPlayers;
	private String serverMotd;
	private String worldName;
	private DatagramSocket querySocket = null;
	private byte[] buffer = new byte[1460];
	private DatagramPacket incomingPacket = null;
	private Map field_72644_p;
	private String queryHostname;
	private String serverHostname;
	private Map queryClients;
	private long time;
	private RConOutputStream output;
	private long lastQueryResponseTime;
	
	public RConThreadQuery(IServer p_i3406_1_)
	{
		super(p_i3406_1_);
		queryPort = p_i3406_1_.getIntProperty("query.port", 0);
		serverHostname = p_i3406_1_.getHostname();
		serverPort = p_i3406_1_.getPort();
		serverMotd = p_i3406_1_.getServerMOTD();
		maxPlayers = p_i3406_1_.getMaxPlayers();
		worldName = p_i3406_1_.getFolderName();
		lastQueryResponseTime = 0L;
		queryHostname = "0.0.0.0";
		if(0 != serverHostname.length() && !queryHostname.equals(serverHostname))
		{
			queryHostname = serverHostname;
		} else
		{
			serverHostname = "0.0.0.0";
			try
			{
				InetAddress var2 = InetAddress.getLocalHost();
				queryHostname = var2.getHostAddress();
			} catch(UnknownHostException var3)
			{
				logWarning("Unable to determine local host IP, please set server-ip in \'" + p_i3406_1_.getSettingsFilename() + "\' : " + var3.getMessage());
			}
		}
		if(0 == queryPort)
		{
			queryPort = serverPort;
			logInfo("Setting default query port to " + queryPort);
			p_i3406_1_.setProperty("query.port", Integer.valueOf(queryPort));
			p_i3406_1_.setProperty("debug", Boolean.valueOf(false));
			p_i3406_1_.saveProperties();
		}
		field_72644_p = new HashMap();
		output = new RConOutputStream(1460);
		queryClients = new HashMap();
		time = new Date().getTime();
	}
	
	private void cleanQueryClientsMap()
	{
		if(running)
		{
			long var1 = System.currentTimeMillis();
			if(var1 >= lastAuthCheckTime + 30000L)
			{
				lastAuthCheckTime = var1;
				Iterator var3 = queryClients.entrySet().iterator();
				while(var3.hasNext())
				{
					Entry var4 = (Entry) var3.next();
					if(((RConThreadQueryAuth) var4.getValue()).hasExpired(var1).booleanValue())
					{
						var3.remove();
					}
				}
			}
		}
	}
	
	private byte[] createQueryResponse(DatagramPacket p_72624_1_) throws IOException
	{
		long var2 = System.currentTimeMillis();
		if(var2 < lastQueryResponseTime + 5000L)
		{
			byte[] var7 = output.toByteArray();
			byte[] var8 = getRequestID(p_72624_1_.getSocketAddress());
			var7[1] = var8[0];
			var7[2] = var8[1];
			var7[3] = var8[2];
			var7[4] = var8[3];
			return var7;
		} else
		{
			lastQueryResponseTime = var2;
			output.reset();
			output.writeInt(0);
			output.writeByteArray(getRequestID(p_72624_1_.getSocketAddress()));
			output.writeString("splitnum");
			output.writeInt(128);
			output.writeInt(0);
			output.writeString("hostname");
			output.writeString(serverMotd);
			output.writeString("gametype");
			output.writeString("SMP");
			output.writeString("game_id");
			output.writeString("MINECRAFT");
			output.writeString("version");
			output.writeString(server.getMinecraftVersion());
			output.writeString("plugins");
			output.writeString(server.getPlugins());
			output.writeString("map");
			output.writeString(worldName);
			output.writeString("numplayers");
			output.writeString("" + getNumberOfPlayers());
			output.writeString("maxplayers");
			output.writeString("" + maxPlayers);
			output.writeString("hostport");
			output.writeString("" + serverPort);
			output.writeString("hostip");
			output.writeString(queryHostname);
			output.writeInt(0);
			output.writeInt(1);
			output.writeString("player_");
			output.writeInt(0);
			String[] var4 = server.getAllUsernames();
			byte var5 = (byte) var4.length;
			for(byte var6 = (byte) (var5 - 1); var6 >= 0; --var6)
			{
				output.writeString(var4[var6]);
			}
			output.writeInt(0);
			return output.toByteArray();
		}
	}
	
	private byte[] getRequestID(SocketAddress p_72625_1_)
	{
		return ((RConThreadQueryAuth) queryClients.get(p_72625_1_)).getRequestId();
	}
	
	private boolean initQuerySystem()
	{
		try
		{
			querySocket = new DatagramSocket(queryPort, InetAddress.getByName(serverHostname));
			registerSocket(querySocket);
			querySocket.setSoTimeout(500);
			return true;
		} catch(SocketException var2)
		{
			logWarning("Unable to initialise query system on " + serverHostname + ":" + queryPort + " (Socket): " + var2.getMessage());
		} catch(UnknownHostException var3)
		{
			logWarning("Unable to initialise query system on " + serverHostname + ":" + queryPort + " (Unknown Host): " + var3.getMessage());
		} catch(Exception var4)
		{
			logWarning("Unable to initialise query system on " + serverHostname + ":" + queryPort + " (E): " + var4.getMessage());
		}
		return false;
	}
	
	private boolean parseIncomingPacket(DatagramPacket p_72621_1_) throws IOException
	{
		byte[] var2 = p_72621_1_.getData();
		int var3 = p_72621_1_.getLength();
		SocketAddress var4 = p_72621_1_.getSocketAddress();
		logDebug("Packet len " + var3 + " [" + var4 + "]");
		if(3 <= var3 && -2 == var2[0] && -3 == var2[1])
		{
			logDebug("Packet \'" + RConUtils.getByteAsHexString(var2[2]) + "\' [" + var4 + "]");
			switch(var2[2])
			{
				case 0:
					if(!verifyClientAuth(p_72621_1_).booleanValue())
					{
						logDebug("Invalid challenge [" + var4 + "]");
						return false;
					} else if(15 == var3)
					{
						sendResponsePacket(createQueryResponse(p_72621_1_), p_72621_1_);
						logDebug("Rules [" + var4 + "]");
					} else
					{
						RConOutputStream var5 = new RConOutputStream(1460);
						var5.writeInt(0);
						var5.writeByteArray(getRequestID(p_72621_1_.getSocketAddress()));
						var5.writeString(serverMotd);
						var5.writeString("SMP");
						var5.writeString(worldName);
						var5.writeString(Integer.toString(getNumberOfPlayers()));
						var5.writeString(Integer.toString(maxPlayers));
						var5.writeShort((short) serverPort);
						var5.writeString(queryHostname);
						sendResponsePacket(var5.toByteArray(), p_72621_1_);
						logDebug("Status [" + var4 + "]");
					}
				case 9:
					sendAuthChallenge(p_72621_1_);
					logDebug("Challenge [" + var4 + "]");
					return true;
				default:
					return true;
			}
		} else
		{
			logDebug("Invalid packet [" + var4 + "]");
			return false;
		}
	}
	
	@Override public void run()
	{
		logInfo("Query running on " + serverHostname + ":" + queryPort);
		lastAuthCheckTime = System.currentTimeMillis();
		incomingPacket = new DatagramPacket(buffer, buffer.length);
		try
		{
			while(running)
			{
				try
				{
					querySocket.receive(incomingPacket);
					cleanQueryClientsMap();
					parseIncomingPacket(incomingPacket);
				} catch(SocketTimeoutException var7)
				{
					cleanQueryClientsMap();
				} catch(PortUnreachableException var8)
				{
					;
				} catch(IOException var9)
				{
					stopWithException(var9);
				}
			}
		} finally
		{
			closeAllSockets();
		}
	}
	
	private void sendAuthChallenge(DatagramPacket p_72622_1_) throws IOException
	{
		RConThreadQueryAuth var2 = new RConThreadQueryAuth(this, p_72622_1_);
		queryClients.put(p_72622_1_.getSocketAddress(), var2);
		sendResponsePacket(var2.getChallengeValue(), p_72622_1_);
	}
	
	private void sendResponsePacket(byte[] p_72620_1_, DatagramPacket p_72620_2_) throws IOException
	{
		querySocket.send(new DatagramPacket(p_72620_1_, p_72620_1_.length, p_72620_2_.getSocketAddress()));
	}
	
	@Override public void startThread()
	{
		if(!running)
		{
			if(0 < queryPort && 65535 >= queryPort)
			{
				if(initQuerySystem())
				{
					super.startThread();
				}
			} else
			{
				logWarning("Invalid query port " + queryPort + " found in \'" + server.getSettingsFilename() + "\' (queries disabled)");
			}
		}
	}
	
	private void stopWithException(Exception p_72623_1_)
	{
		if(running)
		{
			logWarning("Unexpected exception, buggy JRE? (" + p_72623_1_.toString() + ")");
			if(!initQuerySystem())
			{
				logSevere("Failed to recover from buggy JRE, shutting down!");
				running = false;
			}
		}
	}
	
	private Boolean verifyClientAuth(DatagramPacket p_72627_1_)
	{
		SocketAddress var2 = p_72627_1_.getSocketAddress();
		if(!queryClients.containsKey(var2)) return Boolean.valueOf(false);
		else
		{
			byte[] var3 = p_72627_1_.getData();
			return ((RConThreadQueryAuth) queryClients.get(var2)).getRandomChallenge() != RConUtils.getBytesAsBEint(var3, 7, p_72627_1_.getLength()) ? Boolean.valueOf(false) : Boolean.valueOf(true);
		}
	}
}
