package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet33RelEntityMoveLook extends Packet30Entity
{
	public Packet33RelEntityMoveLook()
	{
		rotating = true;
	}
	
	public Packet33RelEntityMoveLook(int par1, byte par2, byte par3, byte par4, byte par5, byte par6)
	{
		super(par1);
		xPosition = par2;
		yPosition = par3;
		zPosition = par4;
		yaw = par5;
		pitch = par6;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		super.readPacketData(par1DataInputStream);
		xPosition = par1DataInputStream.readByte();
		yPosition = par1DataInputStream.readByte();
		zPosition = par1DataInputStream.readByte();
		yaw = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		super.writePacketData(par1DataOutputStream);
		par1DataOutputStream.writeByte(xPosition);
		par1DataOutputStream.writeByte(yPosition);
		par1DataOutputStream.writeByte(zPosition);
		par1DataOutputStream.writeByte(yaw);
		par1DataOutputStream.writeByte(pitch);
	}
}
