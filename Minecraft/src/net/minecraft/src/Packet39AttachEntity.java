package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet39AttachEntity extends Packet
{
	public int entityId;
	public int vehicleEntityId;
	
	public Packet39AttachEntity()
	{
	}
	
	public Packet39AttachEntity(Entity par1Entity, Entity par2Entity)
	{
		entityId = par1Entity.entityId;
		vehicleEntityId = par2Entity != null ? par2Entity.entityId : -1;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet39AttachEntity var2 = (Packet39AttachEntity) par1Packet;
		return var2.entityId == entityId;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		vehicleEntityId = par1DataInputStream.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeInt(vehicleEntityId);
	}
}
