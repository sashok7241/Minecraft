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
	
	public Packet200Statistic(int p_i3298_1_, int p_i3298_2_)
	{
		statisticId = p_i3298_1_;
		amount = p_i3298_2_;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 6;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleStatistic(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		statisticId = p_73267_1_.readInt();
		amount = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(statisticId);
		p_73273_1_.writeByte(amount);
	}
}
