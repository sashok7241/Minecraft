package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		statisticId = par1DataInputStream.readInt();
		amount = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(statisticId);
		par1DataOutputStream.writeByte(amount);
	}
}
