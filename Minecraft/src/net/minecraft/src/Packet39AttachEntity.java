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
	
	public Packet39AttachEntity(Entity p_i3353_1_, Entity p_i3353_2_)
	{
		entityId = p_i3353_1_.entityId;
		vehicleEntityId = p_i3353_2_ != null ? p_i3353_2_.entityId : -1;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet39AttachEntity var2 = (Packet39AttachEntity) p_73268_1_;
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleAttachEntity(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		vehicleEntityId = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeInt(vehicleEntityId);
	}
}
