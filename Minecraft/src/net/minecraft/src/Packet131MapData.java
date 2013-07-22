package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet131MapData extends Packet
{
	public short itemID;
	public short uniqueID;
	public byte[] itemData;
	
	public Packet131MapData()
	{
		isChunkDataPacket = true;
	}
	
	public Packet131MapData(short par1, short par2, byte[] par3ArrayOfByte)
	{
		isChunkDataPacket = true;
		itemID = par1;
		uniqueID = par2;
		itemData = par3ArrayOfByte;
	}
	
	@Override public int getPacketSize()
	{
		return 4 + itemData.length;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleMapData(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		itemID = par1DataInputStream.readShort();
		uniqueID = par1DataInputStream.readShort();
		itemData = new byte[par1DataInputStream.readUnsignedShort()];
		par1DataInputStream.readFully(itemData);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeShort(itemID);
		par1DataOutputStream.writeShort(uniqueID);
		par1DataOutputStream.writeShort(itemData.length);
		par1DataOutputStream.write(itemData);
	}
}
