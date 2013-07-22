package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat extends Packet
{
	public static int maxChatLength = 119;
	public String message;
	private boolean isServer;
	
	public Packet3Chat()
	{
		isServer = true;
	}
	
	public Packet3Chat(String par1Str)
	{
		this(par1Str, true);
	}
	
	public Packet3Chat(String par1Str, boolean par2)
	{
		isServer = true;
		if(par1Str.length() > maxChatLength)
		{
			par1Str = par1Str.substring(0, maxChatLength);
		}
		message = par1Str;
		isServer = par2;
	}
	
	@Override public boolean canProcessAsync()
	{
		return !message.startsWith("/");
	}
	
	public boolean getIsServer()
	{
		return isServer;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + message.length() * 2;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleChat(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		message = readString(par1DataInputStream, maxChatLength);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(message, par1DataOutputStream);
	}
}
