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
	
	public Packet132TileEntityData(int par1, int par2, int par3, int par4, NBTTagCompound par5NBTTagCompound)
	{
		isChunkDataPacket = true;
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
		actionType = par4;
		customParam1 = par5NBTTagCompound;
	}
	
	@Override public int getPacketSize()
	{
		return 25;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleTileEntityData(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readShort();
		zPosition = par1DataInputStream.readInt();
		actionType = par1DataInputStream.readByte();
		customParam1 = readNBTTagCompound(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeShort(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte((byte) actionType);
		writeNBTTagCompound(customParam1, par1DataOutputStream);
	}
}
