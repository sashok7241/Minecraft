package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet54PlayNoteBlock extends Packet
{
	public int xLocation;
	public int yLocation;
	public int zLocation;
	public int instrumentType;
	public int pitch;
	public int blockId;
	
	public Packet54PlayNoteBlock()
	{
	}
	
	public Packet54PlayNoteBlock(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		xLocation = par1;
		yLocation = par2;
		zLocation = par3;
		instrumentType = par5;
		pitch = par6;
		blockId = par4;
	}
	
	@Override public int getPacketSize()
	{
		return 14;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleBlockEvent(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xLocation = par1DataInput.readInt();
		yLocation = par1DataInput.readShort();
		zLocation = par1DataInput.readInt();
		instrumentType = par1DataInput.readUnsignedByte();
		pitch = par1DataInput.readUnsignedByte();
		blockId = par1DataInput.readShort() & 4095;
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(xLocation);
		par1DataOutput.writeShort(yLocation);
		par1DataOutput.writeInt(zLocation);
		par1DataOutput.write(instrumentType);
		par1DataOutput.write(pitch);
		par1DataOutput.writeShort(blockId & 4095);
	}
}
