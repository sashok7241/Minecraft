package net.minecraft.src;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

import net.minecraft.client.Minecraft;

public class ThreadLanServerFind extends Thread
{
	private final LanServerList localServerList;
	private final InetAddress broadcastAddress;
	private final MulticastSocket socket;
	
	public ThreadLanServerFind(LanServerList par1LanServerList) throws IOException
	{
		super("LanServerDetector");
		localServerList = par1LanServerList;
		setDaemon(true);
		socket = new MulticastSocket(4445);
		broadcastAddress = InetAddress.getByName("224.0.2.60");
		socket.setSoTimeout(5000);
		socket.joinGroup(broadcastAddress);
	}
	
	@Override public void run()
	{
		byte[] var2 = new byte[1024];
		while(!isInterrupted())
		{
			DatagramPacket var1 = new DatagramPacket(var2, var2.length);
			try
			{
				socket.receive(var1);
			} catch(SocketTimeoutException var5)
			{
				continue;
			} catch(IOException var6)
			{
				var6.printStackTrace();
				break;
			}
			String var3 = new String(var1.getData(), var1.getOffset(), var1.getLength());
			Minecraft.getMinecraft().getLogAgent().logFine(var1.getAddress() + ": " + var3);
			localServerList.func_77551_a(var3, var1.getAddress());
		}
		try
		{
			socket.leaveGroup(broadcastAddress);
		} catch(IOException var4)
		{
			;
		}
		socket.close();
	}
}
