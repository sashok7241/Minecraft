package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet19EntityAction extends Packet
{
	public int entityId;
	public int state;
	public int field_111009_c;
	
	public Packet19EntityAction()
	{
	}
	
	public Packet19EntityAction(Entity par1Entity, int par2)
	{
		this(par1Entity, par2, 0);
	}
	
	public Packet19EntityAction(Entity par1Entity, int par2, int par3)
	{
		entityId = par1Entity.entityId;
		state = par2;
		field_111009_c = par3;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityAction(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		state = par1DataInput.readByte();
		field_111009_c = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(state);
		par1DataOutput.writeInt(field_111009_c);
	}
}
