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
	
	public Packet4UpdateTime(long p_i5035_1_, long p_i5035_3_)
	{
		worldAge = p_i5035_1_;
		time = p_i5035_3_;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleUpdateTime(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		worldAge = p_73267_1_.readLong();
		time = p_73267_1_.readLong();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeLong(worldAge);
		p_73273_1_.writeLong(time);
	}
}
