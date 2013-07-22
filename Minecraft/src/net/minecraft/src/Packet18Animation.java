package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet18Animation extends Packet
{
	public int entityId;
	public int animate;
	
	public Packet18Animation()
	{
	}
	
	public Packet18Animation(Entity par1Entity, int par2)
	{
		entityId = par1Entity.entityId;
		animate = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleAnimation(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		animate = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(animate);
	}
}
