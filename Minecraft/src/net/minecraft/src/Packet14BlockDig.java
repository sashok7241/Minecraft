package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet14BlockDig extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int face;
	public int status;
	
	public Packet14BlockDig()
	{
	}
	
	public Packet14BlockDig(int par1, int par2, int par3, int par4, int par5)
	{
		status = par1;
		xPosition = par2;
		yPosition = par3;
		zPosition = par4;
		face = par5;
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleBlockDig(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		status = par1DataInput.readUnsignedByte();
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readUnsignedByte();
		zPosition = par1DataInput.readInt();
		face = par1DataInput.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.write(status);
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.write(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.write(face);
	}
}
