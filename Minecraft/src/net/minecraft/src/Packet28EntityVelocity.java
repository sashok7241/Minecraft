package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet28EntityVelocity extends Packet
{
	public int entityId;
	public int motionX;
	public int motionY;
	public int motionZ;
	
	public Packet28EntityVelocity()
	{
	}
	
	public Packet28EntityVelocity(Entity p_i3348_1_)
	{
		this(p_i3348_1_.entityId, p_i3348_1_.motionX, p_i3348_1_.motionY, p_i3348_1_.motionZ);
	}
	
	public Packet28EntityVelocity(int p_i3349_1_, double p_i3349_2_, double p_i3349_4_, double p_i3349_6_)
	{
		entityId = p_i3349_1_;
		double var8 = 3.9D;
		if(p_i3349_2_ < -var8)
		{
			p_i3349_2_ = -var8;
		}
		if(p_i3349_4_ < -var8)
		{
			p_i3349_4_ = -var8;
		}
		if(p_i3349_6_ < -var8)
		{
			p_i3349_6_ = -var8;
		}
		if(p_i3349_2_ > var8)
		{
			p_i3349_2_ = var8;
		}
		if(p_i3349_4_ > var8)
		{
			p_i3349_4_ = var8;
		}
		if(p_i3349_6_ > var8)
		{
			p_i3349_6_ = var8;
		}
		motionX = (int) (p_i3349_2_ * 8000.0D);
		motionY = (int) (p_i3349_4_ * 8000.0D);
		motionZ = (int) (p_i3349_6_ * 8000.0D);
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet28EntityVelocity var2 = (Packet28EntityVelocity) p_73268_1_;
		return var2.entityId == entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 10;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityVelocity(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		motionX = p_73267_1_.readShort();
		motionY = p_73267_1_.readShort();
		motionZ = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeShort(motionX);
		p_73273_1_.writeShort(motionY);
		p_73273_1_.writeShort(motionZ);
	}
}
