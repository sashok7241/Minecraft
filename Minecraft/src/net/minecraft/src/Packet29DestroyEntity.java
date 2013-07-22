package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet29DestroyEntity extends Packet
{
	public int[] entityId;
	
	public Packet29DestroyEntity()
	{
	}
	
	public Packet29DestroyEntity(int ... p_i3340_1_)
	{
		entityId = p_i3340_1_;
	}
	
	@Override public int getPacketSize()
	{
		return 1 + entityId.length * 4;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleDestroyEntity(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = new int[p_73267_1_.readByte()];
		for(int var2 = 0; var2 < entityId.length; ++var2)
		{
			entityId[var2] = p_73267_1_.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(entityId.length);
		for(int element : entityId)
		{
			p_73273_1_.writeInt(element);
		}
	}
}
