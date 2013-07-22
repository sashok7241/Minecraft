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
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.SecretKey;

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
	private List readPackets;
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
	
	public TcpConnection(ILogAgent p_i11026_1_, Socket p_i11026_2_, String p_i11026_3_, NetHandler p_i11026_4_) throws IOException
	{
		this(p_i11026_1_, p_i11026_2_, p_i11026_3_, p_i11026_4_, (PrivateKey) null);
	}
	
	public TcpConnection(ILogAgent p_i11027_1_, Socket p_i11027_2_, String p_i11027_3_, NetHandler p_i11027_4_, PrivateKey p_i11027_5_) throws IOException
	{
		sendQueueLock = new Object();
		isRunning = true;
		isTerminating = false;
		readPackets = Collections.synchronizedList(new ArrayList());
		dataPackets = Collections.synchronizedList(new ArrayList());
		chunkDataPackets = Collections.synchronizedList(new ArrayList());
		isServerTerminating = false;
		terminationReason = "";
		field_74490_x = 0;
		sendQueueByteLength = 0;
		field_74468_e = 0;
		isInputBeingDecrypted = false;
		isOutputEncrypted = false;
		sharedKeyForEncryption = null;
		field_74463_A = null;
		chunkDataPacketsDelay = 50;
		field_74463_A = p_i11027_5_;
		networkSocket = p_i11027_2_;
		field_98215_i = p_i11027_1_;
		remoteSocketAddress = p_i11027_2_.getRemoteSocketAddress();
		theNetHandler = p_i11027_4_;
		try
		{
			p_i11027_2_.setSoTimeout(30000);
			p_i11027_2_.setTrafficClass(24);
		} catch(SocketException var7)
		{
			System.err.println(var7.getMessage());
		}
		socketInputStream = new DataInputStream(p_i11027_2_.getInputStream());
		socketOutputStream = new DataOutputStream(new BufferedOutputStream(p_i11027_2_.getOutputStream(), 5120));
		readThread = new TcpReaderThread(this, p_i11027_3_ + " read thread");
		writeThread = new TcpWriterThread(this, p_i11027_3_ + " write thread");
		readThread.start();
		writeThread.start();
	}
	
	@Override public void addToSendQueue(Packet p_74429_1_)
	{
		if(!isServerTerminating)
		{
			Object var2 = sendQueueLock;
			synchronized(sendQueueLock)
			{
				sendQueueByteLength += p_74429_1_.getPacketSize() + 1;
				dataPackets.add(p_74429_1_);
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
	
	private boolean func_74454_a(Packet p_74454_1_, boolean p_74454_2_)
	{
		if(!p_74454_1_.isRealPacket()) return false;
		else
		{
			List var3 = p_74454_2_ ? chunkDataPackets : dataPackets;
			Iterator var4 = var3.iterator();
			Packet var5;
			do
			{
				if(!var4.hasNext()) return false;
				var5 = (Packet) var4.next();
			} while(var5.getPacketId() != p_74454_1_.getPacketId());
			return p_74454_1_.containsSameEntityIDAs(var5);
		}
	}
	
	private Packet func_74460_a(boolean p_74460_1_)
	{
		Packet var2 = null;
		List var3 = p_74460_1_ ? chunkDataPackets : dataPackets;
		Object var4 = sendQueueLock;
		synchronized(sendQueueLock)
		{
			while(!var3.isEmpty() && var2 == null)
			{
				var2 = (Packet) var3.remove(0);
				sendQueueByteLength -= var2.getPacketSize() + 1;
				if(func_74454_a(var2, p_74460_1_))
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
	
	@Override public void networkShutdown(String p_74424_1_, Object ... p_74424_2_)
	{
		if(isRunning)
		{
			isTerminating = true;
			terminationReason = p_74424_1_;
			field_74480_w = p_74424_2_;
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
	
	private void onNetworkError(Exception p_74455_1_)
	{
		p_74455_1_.printStackTrace();
		networkShutdown("disconnect.genericReason", new Object[] { "Internal exception: " + p_74455_1_.toString() });
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
		while(!readPackets.isEmpty() && var1-- >= 0)
		{
			Packet var2 = (Packet) readPackets.remove(0);
			var2.processPacket(theNetHandler);
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
			if(field_74468_e == 0 || !dataPackets.isEmpty() && System.currentTimeMillis() - ((Packet) dataPackets.get(0)).creationTimeMillis >= field_74468_e)
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
			if(chunkDataPacketsDelay-- <= 0 && (field_74468_e == 0 || !chunkDataPackets.isEmpty() && System.currentTimeMillis() - ((Packet) chunkDataPackets.get(0)).creationTimeMillis >= field_74468_e))
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
	
	@Override public void setNetHandler(NetHandler p_74425_1_)
	{
		theNetHandler = p_74425_1_;
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
	
	static DataOutputStream getOutputStream(TcpConnection p_74453_0_)
	{
		return p_74453_0_.socketOutputStream;
	}
	
	static Thread getReadThread(TcpConnection p_74457_0_)
	{
		return p_74457_0_.readThread;
	}
	
	static Thread getWriteThread(TcpConnection p_74461_0_)
	{
		return p_74461_0_.writeThread;
	}
	
	static boolean isRunning(TcpConnection p_74462_0_)
	{
		return p_74462_0_.isRunning;
	}
	
	static boolean isServerTerminating(TcpConnection p_74449_0_)
	{
		return p_74449_0_.isServerTerminating;
	}
	
	static boolean isTerminating(TcpConnection p_74456_0_)
	{
		return p_74456_0_.isTerminating;
	}
	
	static boolean readNetworkPacket(TcpConnection p_74450_0_)
	{
		return p_74450_0_.readPacket();
	}
	
	static void sendError(TcpConnection p_74458_0_, Exception p_74458_1_)
	{
		p_74458_0_.onNetworkError(p_74458_1_);
	}
	
	static boolean sendNetworkPacket(TcpConnection p_74451_0_)
	{
		return p_74451_0_.sendPacket();
	}
}
