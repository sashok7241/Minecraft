package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet55BlockDestroy(int par1, int par2, int par3, int par4, int par5)
	{
		entityId = par1;
		posX = par2;
		posY = par3;
		posZ = par4;
		destroyedStage = par5;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet55BlockDestroy var2 = (Packet55BlockDestroy) par1Packet;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleBlockDestroy(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		posX = par1DataInput.readInt();
		posY = par1DataInput.readInt();
		posZ = par1DataInput.readInt();
		destroyedStage = par1DataInput.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeInt(posX);
		par1DataOutput.writeInt(posY);
		par1DataOutput.writeInt(posZ);
		par1DataOutput.write(destroyedStage);
	}
}
