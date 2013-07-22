package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet39AttachEntity extends Packet
{
	public int field_111007_a;
	public int field_111006_b;
	public int vehicleEntityId;
	
	public Packet39AttachEntity()
	{
	}
	
	public Packet39AttachEntity(int par1, Entity par2Entity, Entity par3Entity)
	{
		field_111007_a = par1;
		field_111006_b = par2Entity.entityId;
		vehicleEntityId = par3Entity != null ? par3Entity.entityId : -1;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet39AttachEntity var2 = (Packet39AttachEntity) par1Packet;
		return var2.field_111006_b == field_111006_b;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleAttachEntity(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		field_111006_b = par1DataInput.readInt();
		vehicleEntityId = par1DataInput.readInt();
		field_111007_a = par1DataInput.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(field_111006_b);
		par1DataOutput.writeInt(vehicleEntityId);
		par1DataOutput.writeByte(field_111007_a);
	}
}
