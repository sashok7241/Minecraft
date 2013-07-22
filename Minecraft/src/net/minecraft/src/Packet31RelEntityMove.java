package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet31RelEntityMove extends Packet30Entity
{
	public Packet31RelEntityMove()
	{
	}
	
	public Packet31RelEntityMove(int par1, byte par2, byte par3, byte par4)
	{
		super(par1);
		xPosition = par2;
		yPosition = par3;
		zPosition = par4;
	}
	
	@Override public int getPacketSize()
	{
		return 7;
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		super.readPacketData(par1DataInputStream);
		xPosition = par1DataInputStream.readByte();
		yPosition = par1DataInputStream.readByte();
		zPosition = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		super.writePacketData(par1DataOutputStream);
		par1DataOutputStream.writeByte(xPosition);
		par1DataOutputStream.writeByte(yPosition);
		par1DataOutputStream.writeByte(zPosition);
	}
}
