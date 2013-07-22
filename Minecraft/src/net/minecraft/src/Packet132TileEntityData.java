package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet132TileEntityData extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int actionType;
	public NBTTagCompound customParam1;
	
	public Packet132TileEntityData()
	{
		isChunkDataPacket = true;
	}
	
	public Packet132TileEntityData(int p_i3362_1_, int p_i3362_2_, int p_i3362_3_, int p_i3362_4_, NBTTagCompound p_i3362_5_)
	{
		isChunkDataPacket = true;
		xPosition = p_i3362_1_;
		yPosition = p_i3362_2_;
		zPosition = p_i3362_3_;
		actionType = p_i3362_4_;
		customParam1 = p_i3362_5_;
	}
	
	@Override public int getPacketSize()
	{
		return 25;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleTileEntityData(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readShort();
		zPosition = p_73267_1_.readInt();
		actionType = p_73267_1_.readByte();
		customParam1 = readNBTTagCompound(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeShort(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeByte((byte) actionType);
		writeNBTTagCompound(customParam1, p_73273_1_);
	}
}
