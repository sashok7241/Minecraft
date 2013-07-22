package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet43Experience extends Packet
{
	public float experience;
	public int experienceTotal;
	public int experienceLevel;
	
	public Packet43Experience()
	{
	}
	
	public Packet43Experience(float par1, int par2, int par3)
	{
		experience = par1;
		experienceTotal = par2;
		experienceLevel = par3;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 4;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleExperience(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		experience = par1DataInput.readFloat();
		experienceLevel = par1DataInput.readShort();
		experienceTotal = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeFloat(experience);
		par1DataOutput.writeShort(experienceLevel);
		par1DataOutput.writeShort(experienceTotal);
	}
}
