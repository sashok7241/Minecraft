package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		posX = par1DataInput.readInt();
		posY = par1DataInput.readInt();
		posZ = par1DataInput.readInt();
		xpValue = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeInt(posX);
		par1DataOutput.writeInt(posY);
		par1DataOutput.writeInt(posZ);
		par1DataOutput.writeShort(xpValue);
	}
}
