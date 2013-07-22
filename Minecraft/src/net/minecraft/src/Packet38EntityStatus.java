package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet38EntityStatus extends Packet
{
	public int entityId;
	public byte entityStatus;
	
	public Packet38EntityStatus()
	{
	}
	
	public Packet38EntityStatus(int par1, byte par2)
	{
		entityId = par1;
		entityStatus = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityStatus(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		entityStatus = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(entityStatus);
	}
}
