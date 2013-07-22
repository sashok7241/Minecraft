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
	
	public Packet26EntityExpOrb(EntityXPOrb par1EntityXPOrb)
	{
		entityId = par1EntityXPOrb.entityId;
		posX = MathHelper.floor_double(par1EntityXPOrb.posX * 32.0D);
		posY = MathHelper.floor_double(par1EntityXPOrb.posY * 32.0D);
		posZ = MathHelper.floor_double(par1EntityXPOrb.posZ * 32.0D);
		xpValue = par1EntityXPOrb.getXpValue();
	}
	
	@Override public int getPacketSize()
	{
		return 18;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityExpOrb(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		posX = par1DataInputStream.readInt();
		posY = par1DataInputStream.readInt();
		posZ = par1DataInputStream.readInt();
		xpValue = par1DataInputStream.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeInt(posX);
		par1DataOutputStream.writeInt(posY);
		par1DataOutputStream.writeInt(posZ);
		par1DataOutputStream.writeShort(xpValue);
	}
}
