package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet4UpdateTime extends Packet
{
	public long worldAge;
	public long time;
	
	public Packet4UpdateTime()
	{
	}
	
	public Packet4UpdateTime(long par1, long par3)
	{
		worldAge = par1;
		time = par3;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 16;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleUpdateTime(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		worldAge = par1DataInputStream.readLong();
		time = par1DataInputStream.readLong();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeLong(worldAge);
		par1DataOutputStream.writeLong(time);
	}
}
