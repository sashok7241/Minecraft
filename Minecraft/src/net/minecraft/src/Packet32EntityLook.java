package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet32EntityLook extends Packet30Entity
{
	public Packet32EntityLook()
	{
		rotating = true;
	}
	
	public Packet32EntityLook(int par1, byte par2, byte par3)
	{
		super(par1);
		yaw = par2;
		pitch = par3;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 6;
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		super.readPacketData(par1DataInput);
		yaw = par1DataInput.readByte();
		pitch = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		super.writePacketData(par1DataOutput);
		par1DataOutput.writeByte(yaw);
		par1DataOutput.writeByte(pitch);
	}
}
