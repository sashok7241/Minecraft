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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		posX = par1DataInputStream.readInt();
		posY = par1DataInputStream.readInt();
		posZ = par1DataInputStream.readInt();
		destroyedStage = par1DataInputStream.read();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeInt(posX);
		par1DataOutputStream.writeInt(posY);
		par1DataOutputStream.writeInt(posZ);
		par1DataOutputStream.write(destroyedStage);
	}
}
