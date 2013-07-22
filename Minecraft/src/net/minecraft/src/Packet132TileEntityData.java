package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readShort();
		zPosition = par1DataInput.readInt();
		actionType = par1DataInput.readByte();
		customParam1 = readNBTTagCompound(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeShort(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeByte((byte) actionType);
		writeNBTTagCompound(customParam1, par1DataOutput);
	}
}
