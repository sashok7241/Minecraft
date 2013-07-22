package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		super.readPacketData(par1DataInput);
		xPosition = par1DataInput.readByte();
		yPosition = par1DataInput.readByte();
		zPosition = par1DataInput.readByte();
		yaw = par1DataInput.readByte();
		pitch = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		super.writePacketData(par1DataOutput);
		par1DataOutput.writeByte(xPosition);
		par1DataOutput.writeByte(yPosition);
		par1DataOutput.writeByte(zPosition);
		par1DataOutput.writeByte(yaw);
		par1DataOutput.writeByte(pitch);
	}
}
