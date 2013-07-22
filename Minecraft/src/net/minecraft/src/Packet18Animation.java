package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet18Animation extends Packet
{
	public int entityId;
	public int animate;
	
	public Packet18Animation()
	{
	}
	
	public Packet18Animation(Entity p_i3297_1_, int p_i3297_2_)
	{
		entityId = p_i3297_1_.entityId;
		animate = p_i3297_2_;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleAnimation(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		animate = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(animate);
	}
}
