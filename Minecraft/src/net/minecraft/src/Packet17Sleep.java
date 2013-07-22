package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet17Sleep extends Packet
{
	public int entityID;
	public int bedX;
	public int bedY;
	public int bedZ;
	public int field_73622_e;
	
	public Packet17Sleep()
	{
	}
	
	public Packet17Sleep(Entity par1Entity, int par2, int par3, int par4, int par5)
	{
		field_73622_e = par2;
		bedX = par3;
		bedY = par4;
		bedZ = par5;
		entityID = par1Entity.entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 14;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSleep(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityID = par1DataInput.readInt();
		field_73622_e = par1DataInput.readByte();
		bedX = par1DataInput.readInt();
		bedY = par1DataInput.readByte();
		bedZ = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityID);
		par1DataOutput.writeByte(field_73622_e);
		par1DataOutput.writeInt(bedX);
		par1DataOutput.writeByte(bedY);
		par1DataOutput.writeInt(bedZ);
	}
}
