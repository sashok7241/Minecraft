package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet30Entity extends Packet
{
	public int entityId;
	public byte xPosition;
	public byte yPosition;
	public byte zPosition;
	public byte yaw;
	public byte pitch;
	public boolean rotating;
	
	public Packet30Entity()
	{
	}
	
	public Packet30Entity(int par1)
	{
		entityId = par1;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet30Entity var2 = (Packet30Entity) par1Packet;
		return var2.entityId == entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 4;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntity(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
	}
	
	@Override public String toString()
	{
		return "Entity_" + super.toString();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
	}
}
