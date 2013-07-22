package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet4UpdateTime extends Packet
{
	public long worldAge;
	public long time;
	
	public Packet4UpdateTime()
	{
	}
	
	public Packet4UpdateTime(long par1, long par3, boolean par5)
	{
		worldAge = par1;
		time = par3;
		if(!par5)
		{
			time = -time;
			if(time == 0L)
			{
				time = -1L;
			}
		}
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		worldAge = par1DataInput.readLong();
		time = par1DataInput.readLong();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeLong(worldAge);
		par1DataOutput.writeLong(time);
	}
}
