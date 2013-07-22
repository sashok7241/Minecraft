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
	
	public Packet3Chat(String p_i3300_1_)
	{
		this(p_i3300_1_, true);
	}
	
	public Packet3Chat(String p_i3301_1_, boolean p_i3301_2_)
	{
		isServer = true;
		if(p_i3301_1_.length() > maxChatLength)
		{
			p_i3301_1_ = p_i3301_1_.substring(0, maxChatLength);
		}
		message = p_i3301_1_;
		isServer = p_i3301_2_;
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleChat(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		message = readString(p_73267_1_, maxChatLength);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(message, p_73273_1_);
	}
}
