package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet26EntityExpOrb extends Packet
{
	public int entityId;
	public int posX;
	public int posY;
	public int posZ;
	public int xpValue;
	
	public Packet26EntityExpOrb()
	{
	}
	
	public Packet26EntityExpOrb(EntityXPOrb p_i3291_1_)
	{
		entityId = p_i3291_1_.entityId;
		posX = MathHelper.floor_double(p_i3291_1_.posX * 32.0D);
		posY = MathHelper.floor_double(p_i3291_1_.posY * 32.0D);
		posZ = MathHelper.floor_double(p_i3291_1_.posZ * 32.0D);
		xpValue = p_i3291_1_.getXpValue();
	}
	
	@Override public int getPacketSize()
	{
		return 18;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityExpOrb(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		posX = p_73267_1_.readInt();
		posY = p_73267_1_.readInt();
		posZ = p_73267_1_.readInt();
		xpValue = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeInt(posX);
		p_73273_1_.writeInt(posY);
		p_73273_1_.writeInt(posZ);
		p_73273_1_.writeShort(xpValue);
	}
}
