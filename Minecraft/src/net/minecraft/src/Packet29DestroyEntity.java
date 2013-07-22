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
	
	public Packet29DestroyEntity(int ... par1ArrayOfInteger)
	{
		entityId = par1ArrayOfInteger;
	}
	
	@Override public int getPacketSize()
	{
		return 1 + entityId.length * 4;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleDestroyEntity(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = new int[par1DataInputStream.readByte()];
		for(int var2 = 0; var2 < entityId.length; ++var2)
		{
			entityId[var2] = par1DataInputStream.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(entityId.length);
		for(int element : entityId)
		{
			par1DataOutputStream.writeInt(element);
		}
	}
}
