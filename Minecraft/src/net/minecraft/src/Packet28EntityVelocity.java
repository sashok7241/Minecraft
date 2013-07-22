package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet28EntityVelocity(Entity par1Entity)
	{
		this(par1Entity.entityId, par1Entity.motionX, par1Entity.motionY, par1Entity.motionZ);
	}
	
	public Packet28EntityVelocity(int par1, double par2, double par4, double par6)
	{
		entityId = par1;
		double var8 = 3.9D;
		if(par2 < -var8)
		{
			par2 = -var8;
		}
		if(par4 < -var8)
		{
			par4 = -var8;
		}
		if(par6 < -var8)
		{
			par6 = -var8;
		}
		if(par2 > var8)
		{
			par2 = var8;
		}
		if(par4 > var8)
		{
			par4 = var8;
		}
		if(par6 > var8)
		{
			par6 = var8;
		}
		motionX = (int) (par2 * 8000.0D);
		motionY = (int) (par4 * 8000.0D);
		motionZ = (int) (par6 * 8000.0D);
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet28EntityVelocity var2 = (Packet28EntityVelocity) par1Packet;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityVelocity(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		motionX = par1DataInput.readShort();
		motionY = par1DataInput.readShort();
		motionZ = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeShort(motionX);
		par1DataOutput.writeShort(motionY);
		par1DataOutput.writeShort(motionZ);
	}
}
