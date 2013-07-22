package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet42RemoveEntityEffect extends Packet
{
	public int entityId;
	public byte effectId;
	
	public Packet42RemoveEntityEffect()
	{
	}
	
	public Packet42RemoveEntityEffect(int p_i3341_1_, PotionEffect p_i3341_2_)
	{
		entityId = p_i3341_1_;
		effectId = (byte) (p_i3341_2_.getPotionID() & 255);
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleRemoveEntityEffect(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		effectId = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(effectId);
	}
}
