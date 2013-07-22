package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = new int[par1DataInput.readByte()];
		for(int var2 = 0; var2 < entityId.length; ++var2)
		{
			entityId[var2] = par1DataInput.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(entityId.length);
		for(int element : entityId)
		{
			par1DataOutput.writeInt(element);
		}
	}
}
