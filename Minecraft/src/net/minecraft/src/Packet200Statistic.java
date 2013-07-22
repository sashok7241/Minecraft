package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet200Statistic extends Packet
{
	public int statisticId;
	public int amount;
	
	public Packet200Statistic()
	{
	}
	
	public Packet200Statistic(int par1, int par2)
	{
		statisticId = par1;
		amount = par2;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 6;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleStatistic(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		statisticId = par1DataInput.readInt();
		amount = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(statisticId);
		par1DataOutput.writeInt(amount);
	}
}
