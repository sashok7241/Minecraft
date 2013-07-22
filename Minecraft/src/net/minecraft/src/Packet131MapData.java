package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		itemID = par1DataInput.readShort();
		uniqueID = par1DataInput.readShort();
		itemData = new byte[par1DataInput.readUnsignedShort()];
		par1DataInput.readFully(itemData);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeShort(itemID);
		par1DataOutput.writeShort(uniqueID);
		par1DataOutput.writeShort(itemData.length);
		par1DataOutput.write(itemData);
	}
}
