package net.minecraft.src;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.SecretKey;

import net.minecraft.server.MinecraftServer;

public class TcpConnection implements INetworkManager
{
	public static AtomicInteger field_74471_a = new AtomicInteger();
	public static AtomicInteger field_74469_b = new AtomicInteger();
	private final Object sendQueueLock;
	private final ILogAgent field_98215_i;
	private Socket networkSocket;
	private final SocketAddress remoteSocketAddress;
	private volatile DataInputStream socketInputStream;
	private volatile DataOutputStream socketOutputStream;
	private volatile boolean isRunning;
	private volatile boolean isTerminating;
	private Queue readPackets;
	private List dataPackets;
	private List chunkDataPackets;
	private NetHandler theNetHandler;
	private boolean isServerTerminating;
	private Thread writeThread;
	private Thread readThread;
	private String terminationReason;
	private Object[] field_74480_w;
	private int field_74490_x;
	private int sendQueueByteLength;
	public static int[] field_74470_c = new int[256];
	public static int[] field_74467_d = new int[256];
	public int field_74468_e;
	boolean isInputBeingDecrypted;
	boolean isOutputEncrypted;
	private SecretKey sharedKeyForEncryption;
	private PrivateKey field_74463_A;
	private int chunkDataPacketsDelay;
	
	public TcpConnection(ILogAgent par1ILogAgent, Socket par2Socket, String par3Str, NetHandler par4NetHandler) throws IOException
	{
		this(par1ILogAgent, par2Socket, par3Str, par4NetHandler, (PrivateKey) null);
	}
	
	public TcpConnection(ILogAgent par1ILogAgent, Socket par2Socket, String par3Str, NetHandler par4NetHandler, PrivateKey par5PrivateKey) throws IOException
	{
		sendQueueLock = new Object();
		isRunning = true;
		readPackets = new ConcurrentLinkedQueue();
		dataPackets = Collections.synchronizedList(new ArrayList());
		chunkDataPackets = Collections.synchronizedList(new ArrayList());
		terminationReason = "";
		chunkDataPacketsDelay = 50;
		field_74463_A = par5PrivateKey;
		networkSocket = par2Socket;
		field_98215_i = par1ILogAgent;
		remoteSocketAddress = par2Socket.getRemoteSocketAddress();
		theNetHandler = par4NetHandler;
		try
		{
			par2Socket.setSoTimeout(30000);
			par2Socket.setTrafficClass(24);
		} catch(SocketException var7)
		{
			System.err.println(var7.getMessage());
		}
		socketInputStream = new DataInputStream(par2Socket.getInputStream());
		socketOutputStream = new DataOutputStream(new BufferedOutputStream(par2Socket.getOutputStream(), 5120));
		readThread = new TcpReaderThread(this, par3Str + " read thread");
		writeThread = new TcpWriterThread(this, par3Str + " write thread");
		readThread.start();
		writeThread.start();
	}
	
	@Override public void addToSendQueue(Packet par1Packet)
	{
		if(!isServerTerminating)
		{
			Object var2 = sendQueueLock;
			synchronized(sendQueueLock)
			{
				sendQueueByteLength += par1Packet.getPacketSize() + 1;
				dataPackets.add(par1Packet);
			}
		}
	}
	
	@Override public void closeConnections()
	{
		wakeThreads();
		writeThread = null;
		readThread = null;
	}
	
	private void decryptInputStream() throws IOException
	{
		isInputBeingDecrypted = true;
		InputStream var1 = networkSocket.getInputStream();
		socketInputStream = new DataInputStream(CryptManager.decryptInputStream(sharedKeyForEncryption, var1));
	}
	
	private void encryptOuputStream() throws IOException
	{
		socketOutputStream.flush();
		isOutputEncrypted = true;
		BufferedOutputStream var1 = new BufferedOutputStream(CryptManager.encryptOuputStream(sharedKeyForEncryption, networkSocket.getOutputStream()), 5120);
		socketOutputStream = new DataOutputStream(var1);
	}
	
	private boolean func_74454_a(Packet par1Packet, boolean par2)
	{
		if(!par1Packet.isRealPacket()) return false;
		else
		{
			List var3 = par2 ? chunkDataPackets : dataPackets;
			Iterator var4 = var3.iterator();
			Packet var5;
			do
			{
				if(!var4.hasNext()) return false;
				var5 = (Packet) var4.next();
			} while(var5.getPacketId() != par1Packet.getPacketId());
			return par1Packet.containsSameEntityIDAs(var5);
		}
	}
	
	private Packet func_74460_a(boolean par1)
	{
		Packet var2 = null;
		List var3 = par1 ? chunkDataPackets : dataPackets;
		Object var4 = sendQueueLock;
		synchronized(sendQueueLock)
		{
			while(!var3.isEmpty() && var2 == null)
			{
				var2 = (Packet) var3.remove(0);
				sendQueueByteLength -= var2.getPacketSize() + 1;
				if(func_74454_a(var2, par1))
				{
					var2 = null;
				}
			}
			return var2;
		}
	}
	
	public Socket getSocket()
	{
		return networkSocket;
	}
	
	@Override public SocketAddress getSocketAddress()
	{
		return remoteSocketAddress;
	}
	
	@Override public void networkShutdown(String par1Str, Object ... par2ArrayOfObj)
	{
		if(isRunning)
		{
			isTerminating = true;
			terminationReason = par1Str;
			field_74480_w = par2ArrayOfObj;
			isRunning = false;
			new TcpMasterThread(this).start();
			try
			{
				socketInputStream.close();
			} catch(Throwable var6)
			{
				;
			}
			try
			{
				socketOutputStream.close();
			} catch(Throwable var5)
			{
				;
			}
			try
			{
				networkSocket.close();
			} catch(Throwable var4)
			{
				;
			}
			socketInputStream = null;
			socketOutputStream = null;
			networkSocket = null;
		}
	}
	
	private void onNetworkError(Exception par1Exception)
	{
		par1Exception.printStackTrace();
		networkShutdown("disconnect.genericReason", new Object[] { "Internal exception: " + par1Exception.toString() });
	}
	
	@Override public int packetSize()
	{
		return chunkDataPackets.size();
	}
	
	@Override public void processReadPackets()
	{
		if(sendQueueByteLength > 2097152)
		{
			networkShutdown("disconnect.overflow", new Object[0]);
		}
		if(readPackets.isEmpty())
		{
			if(field_74490_x++ == 1200)
			{
				networkShutdown("disconnect.timeout", new Object[0]);
			}
		} else
		{
			field_74490_x = 0;
		}
		int var1 = 1000;
		while(var1-- >= 0)
		{
			Packet var2 = (Packet) readPackets.poll();
			if(var2 != null && !theNetHandler.func_142032_c())
			{
				var2.processPacket(theNetHandler);
			}
		}
		wakeThreads();
		if(isTerminating && readPackets.isEmpty())
		{
			theNetHandler.handleErrorMessage(terminationReason, field_74480_w);
		}
	}
	
	private boolean readPacket()
	{
		boolean var1 = false;
		try
		{
			Packet var2 = Packet.readPacket(field_98215_i, socketInputStream, theNetHandler.isServerHandler(), networkSocket);
			if(var2 != null)
			{
				if(var2 instanceof Packet252SharedKey && !isInputBeingDecrypted)
				{
					if(theNetHandler.isServerHandler())
					{
						sharedKeyForEncryption = ((Packet252SharedKey) var2).getSharedKey(field_74463_A);
					}
					decryptInputStream();
				}
				int[] var10000 = field_74470_c;
				int var10001 = var2.getPacketId();
				var10000[var10001] += var2.getPacketSize() + 1;
				if(!isServerTerminating)
				{
					if(var2.canProcessAsync() && theNetHandler.canProcessPacketsAsync())
					{
						field_74490_x = 0;
						var2.processPacket(theNetHandler);
					} else
					{
						readPackets.add(var2);
					}
				}
				var1 = true;
			} else
			{
				networkShutdown("disconnect.endOfStream", new Object[0]);
			}
			return var1;
		} catch(Exception var3)
		{
			if(!isTerminating)
			{
				onNetworkError(var3);
			}
			return false;
		}
	}
	
	private boolean sendPacket()
	{
		boolean var1 = false;
		try
		{
			Packet var2;
			int var10001;
			int[] var10000;
			if(field_74468_e == 0 || !dataPackets.isEmpty() && MinecraftServer.func_130071_aq() - ((Packet) dataPackets.get(0)).creationTimeMillis >= field_74468_e)
			{
				var2 = func_74460_a(false);
				if(var2 != null)
				{
					Packet.writePacket(var2, socketOutputStream);
					if(var2 instanceof Packet252SharedKey && !isOutputEncrypted)
					{
						if(!theNetHandler.isServerHandler())
						{
							sharedKeyForEncryption = ((Packet252SharedKey) var2).getSharedKey();
						}
						encryptOuputStream();
					}
					var10000 = field_74467_d;
					var10001 = var2.getPacketId();
					var10000[var10001] += var2.getPacketSize() + 1;
					var1 = true;
				}
			}
			if(chunkDataPacketsDelay-- <= 0 && (field_74468_e == 0 || !chunkDataPackets.isEmpty() && MinecraftServer.func_130071_aq() - ((Packet) chunkDataPackets.get(0)).creationTimeMillis >= field_74468_e))
			{
				var2 = func_74460_a(true);
				if(var2 != null)
				{
					Packet.writePacket(var2, socketOutputStream);
					var10000 = field_74467_d;
					var10001 = var2.getPacketId();
					var10000[var10001] += var2.getPacketSize() + 1;
					chunkDataPacketsDelay = 0;
					var1 = true;
				}
			}
			return var1;
		} catch(Exception var3)
		{
			if(!isTerminating)
			{
				onNetworkError(var3);
			}
			return false;
		}
	}
	
	@Override public void serverShutdown()
	{
		if(!isServerTerminating)
		{
			wakeThreads();
			isServerTerminating = true;
			readThread.interrupt();
			new TcpMonitorThread(this).start();
		}
	}
	
	@Override public void setNetHandler(NetHandler par1NetHandler)
	{
		theNetHandler = par1NetHandler;
	}
	
	@Override public void wakeThreads()
	{
		if(readThread != null)
		{
			readThread.interrupt();
		}
		if(writeThread != null)
		{
			writeThread.interrupt();
		}
	}
	
	static DataOutputStream getOutputStream(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.socketOutputStream;
	}
	
	static Thread getReadThread(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.readThread;
	}
	
	static Thread getWriteThread(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.writeThread;
	}
	
	static boolean isRunning(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.isRunning;
	}
	
	static boolean isServerTerminating(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.isServerTerminating;
	}
	
	static boolean isTerminating(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.isTerminating;
	}
	
	static boolean readNetworkPacket(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.readPacket();
	}
	
	static void sendError(TcpConnection par0TcpConnection, Exception par1Exception)
	{
		par0TcpConnection.onNetworkError(par1Exception);
	}
	
	static boolean sendNetworkPacket(TcpConnection par0TcpConnection)
	{
		return par0TcpConnection.sendPacket();
	}
}
