package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet35EntityHeadRotation extends Packet
{
	public int entityId;
	public byte headRotationYaw;
	
	public Packet35EntityHeadRotation()
	{
	}
	
	public Packet35EntityHeadRotation(int par1, byte par2)
	{
		entityId = par1;
		headRotationYaw = par2;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet35EntityHeadRotation var2 = (Packet35EntityHeadRotation) par1Packet;
		return var2.entityId == entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityHeadRotation(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		headRotationYaw = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(headRotationYaw);
	}
}
