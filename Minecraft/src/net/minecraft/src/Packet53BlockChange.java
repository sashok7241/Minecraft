package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet53BlockChange extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int type;
	public int metadata;
	
	public Packet53BlockChange()
	{
		isChunkDataPacket = true;
	}
	
	public Packet53BlockChange(int par1, int par2, int par3, World par4World)
	{
		isChunkDataPacket = true;
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
		type = par4World.getBlockId(par1, par2, par3);
		metadata = par4World.getBlockMetadata(par1, par2, par3);
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleBlockChange(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readUnsignedByte();
		zPosition = par1DataInput.readInt();
		type = par1DataInput.readShort();
		metadata = par1DataInput.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.write(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeShort(type);
		par1DataOutput.write(metadata);
	}
}
