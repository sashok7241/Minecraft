package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet203AutoComplete extends Packet
{
	private String text;
	
	public Packet203AutoComplete()
	{
	}
	
	public Packet203AutoComplete(String par1Str)
	{
		text = par1Str;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + text.length() * 2;
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleAutoComplete(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		text = readString(par1DataInputStream, Packet3Chat.maxChatLength);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(text, par1DataOutputStream);
	}
}
