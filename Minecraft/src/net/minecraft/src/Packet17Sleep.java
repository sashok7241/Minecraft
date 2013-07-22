package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityID = par1DataInputStream.readInt();
		field_73622_e = par1DataInputStream.readByte();
		bedX = par1DataInputStream.readInt();
		bedY = par1DataInputStream.readByte();
		bedZ = par1DataInputStream.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityID);
		par1DataOutputStream.writeByte(field_73622_e);
		par1DataOutputStream.writeInt(bedX);
		par1DataOutputStream.writeByte(bedY);
		par1DataOutputStream.writeInt(bedZ);
	}
}
