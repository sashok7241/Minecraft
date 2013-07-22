package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		xLocation = par1DataInputStream.readInt();
		yLocation = par1DataInputStream.readShort();
		zLocation = par1DataInputStream.readInt();
		instrumentType = par1DataInputStream.read();
		pitch = par1DataInputStream.read();
		blockId = par1DataInputStream.readShort() & 4095;
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(xLocation);
		par1DataOutputStream.writeShort(yLocation);
		par1DataOutputStream.writeInt(zLocation);
		par1DataOutputStream.write(instrumentType);
		par1DataOutputStream.write(pitch);
		par1DataOutputStream.writeShort(blockId & 4095);
	}
}
