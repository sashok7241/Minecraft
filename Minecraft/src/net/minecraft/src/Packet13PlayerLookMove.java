package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet13PlayerLookMove extends Packet10Flying
{
	public Packet13PlayerLookMove()
	{
		rotating = true;
		moving = true;
	}
	
	public Packet13PlayerLookMove(double par1, double par3, double par5, double par7, float par9, float par10, boolean par11)
	{
		xPosition = par1;
		yPosition = par3;
		stance = par5;
		zPosition = par7;
		yaw = par9;
		pitch = par10;
		onGround = par11;
		rotating = true;
		moving = true;
	}
	
	@Override public int getPacketSize()
	{
		return 41;
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readDouble();
		yPosition = par1DataInput.readDouble();
		stance = par1DataInput.readDouble();
		zPosition = par1DataInput.readDouble();
		yaw = par1DataInput.readFloat();
		pitch = par1DataInput.readFloat();
		super.readPacketData(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeDouble(xPosition);
		par1DataOutput.writeDouble(yPosition);
		par1DataOutput.writeDouble(stance);
		par1DataOutput.writeDouble(zPosition);
		par1DataOutput.writeFloat(yaw);
		par1DataOutput.writeFloat(pitch);
		super.writePacketData(par1DataOutput);
	}
}
