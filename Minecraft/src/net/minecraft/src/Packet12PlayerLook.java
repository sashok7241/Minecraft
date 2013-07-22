package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet12PlayerLook extends Packet10Flying
{
	public Packet12PlayerLook()
	{
		rotating = true;
	}
	
	public Packet12PlayerLook(float par1, float par2, boolean par3)
	{
		yaw = par1;
		pitch = par2;
		onGround = par3;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		yaw = par1DataInput.readFloat();
		pitch = par1DataInput.readFloat();
		super.readPacketData(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeFloat(yaw);
		par1DataOutput.writeFloat(pitch);
		super.writePacketData(par1DataOutput);
	}
}
