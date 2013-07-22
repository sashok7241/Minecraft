package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet17Sleep extends Packet
{
	public int entityID;
	public int bedX;
	public int bedY;
	public int bedZ;
	public int field_73622_e;
	
	public Packet17Sleep()
	{
	}
	
	public Packet17Sleep(Entity p_i3317_1_, int p_i3317_2_, int p_i3317_3_, int p_i3317_4_, int p_i3317_5_)
	{
		field_73622_e = p_i3317_2_;
		bedX = p_i3317_3_;
		bedY = p_i3317_4_;
		bedZ = p_i3317_5_;
		entityID = p_i3317_1_.entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 14;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSleep(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityID = p_73267_1_.readInt();
		field_73622_e = p_73267_1_.readByte();
		bedX = p_73267_1_.readInt();
		bedY = p_73267_1_.readByte();
		bedZ = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityID);
		p_73273_1_.writeByte(field_73622_e);
		p_73273_1_.writeInt(bedX);
		p_73273_1_.writeByte(bedY);
		p_73273_1_.writeInt(bedZ);
	}
}
