package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet
{
	public String reason;
	
	public Packet255KickDisconnect()
	{
	}
	
	public Packet255KickDisconnect(String par1Str)
	{
		reason = par1Str;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return reason.length();
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleKickDisconnect(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		reason = readString(par1DataInput, 256);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(reason, par1DataOutput);
	}
}
