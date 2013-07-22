package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		text = readString(par1DataInput, 32767);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(org.apache.commons.lang3.StringUtils.substring(text, 0, 32767), par1DataOutput);
	}
}
