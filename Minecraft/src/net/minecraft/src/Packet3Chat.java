package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet3Chat extends Packet
{
	public String message;
	private boolean isServer;
	
	public Packet3Chat()
	{
		isServer = true;
	}
	
	public Packet3Chat(ChatMessageComponent par1ChatMessageComponent)
	{
		this(par1ChatMessageComponent.func_111062_i());
	}
	
	public Packet3Chat(ChatMessageComponent par1ChatMessageComponent, boolean par2)
	{
		this(par1ChatMessageComponent.func_111062_i(), par2);
	}
	
	public Packet3Chat(String par1Str)
	{
		this(par1Str, true);
	}
	
	public Packet3Chat(String par1Str, boolean par2)
	{
		isServer = true;
		if(par1Str.length() > 32767)
		{
			par1Str = par1Str.substring(0, 32767);
		}
		message = par1Str;
		isServer = par2;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		message = readString(par1DataInput, 32767);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(message, par1DataOutput);
	}
}
