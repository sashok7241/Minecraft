package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet22Collect extends Packet
{
	public int collectedEntityId;
	public int collectorEntityId;
	
	public Packet22Collect()
	{
	}
	
	public Packet22Collect(int p_i3358_1_, int p_i3358_2_)
	{
		collectedEntityId = p_i3358_1_;
		collectorEntityId = p_i3358_2_;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleCollect(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		collectedEntityId = p_73267_1_.readInt();
		collectorEntityId = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(collectedEntityId);
		p_73273_1_.writeInt(collectorEntityId);
	}
}
