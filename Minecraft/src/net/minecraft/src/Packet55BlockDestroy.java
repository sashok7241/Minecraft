package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet55BlockDestroy extends Packet
{
	private int entityId;
	private int posX;
	private int posY;
	private int posZ;
	private int destroyedStage;
	
	public Packet55BlockDestroy()
	{
	}
	
	public Packet55BlockDestroy(int p_i3361_1_, int p_i3361_2_, int p_i3361_3_, int p_i3361_4_, int p_i3361_5_)
	{
		entityId = p_i3361_1_;
		posX = p_i3361_2_;
		posY = p_i3361_3_;
		posZ = p_i3361_4_;
		destroyedStage = p_i3361_5_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet55BlockDestroy var2 = (Packet55BlockDestroy) p_73268_1_;
		return var2.entityId == entityId;
	}
	
	public int getDestroyedStage()
	{
		return destroyedStage;
	}
	
	public int getEntityId()
	{
		return entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 13;
	}
	
	public int getPosX()
	{
		return posX;
	}
	
	public int getPosY()
	{
		return posY;
	}
	
	public int getPosZ()
	{
		return posZ;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleBlockDestroy(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		posX = p_73267_1_.readInt();
		posY = p_73267_1_.readInt();
		posZ = p_73267_1_.readInt();
		destroyedStage = p_73267_1_.read();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeInt(posX);
		p_73273_1_.writeInt(posY);
		p_73273_1_.writeInt(posZ);
		p_73273_1_.write(destroyedStage);
	}
}
