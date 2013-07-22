package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet11PlayerPosition extends Packet10Flying
{
	public Packet11PlayerPosition()
	{
		moving = true;
	}
	
	public Packet11PlayerPosition(double par1, double par3, double par5, double par7, boolean par9)
	{
		xPosition = par1;
		yPosition = par3;
		stance = par5;
		zPosition = par7;
		onGround = par9;
		moving = true;
	}
	
	@Override public int getPacketSize()
	{
		return 33;
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readDouble();
		yPosition = par1DataInput.readDouble();
		stance = par1DataInput.readDouble();
		zPosition = par1DataInput.readDouble();
		super.readPacketData(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeDouble(xPosition);
		par1DataOutput.writeDouble(yPosition);
		par1DataOutput.writeDouble(stance);
		par1DataOutput.writeDouble(zPosition);
		super.writePacketData(par1DataOutput);
	}
}
