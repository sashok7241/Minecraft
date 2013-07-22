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
	
	public Packet131MapData(short p_i3306_1_, short p_i3306_2_, byte[] p_i3306_3_)
	{
		isChunkDataPacket = true;
		itemID = p_i3306_1_;
		uniqueID = p_i3306_2_;
		itemData = p_i3306_3_;
	}
	
	@Override public int getPacketSize()
	{
		return 4 + itemData.length;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleMapData(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		itemID = p_73267_1_.readShort();
		uniqueID = p_73267_1_.readShort();
		itemData = new byte[p_73267_1_.readUnsignedShort()];
		p_73267_1_.readFully(itemData);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeShort(itemID);
		p_73273_1_.writeShort(uniqueID);
		p_73273_1_.writeShort(itemData.length);
		p_73273_1_.write(itemData);
	}
}
