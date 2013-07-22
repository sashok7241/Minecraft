package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		experience = par1DataInputStream.readFloat();
		experienceLevel = par1DataInputStream.readShort();
		experienceTotal = par1DataInputStream.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeFloat(experience);
		par1DataOutputStream.writeShort(experienceLevel);
		par1DataOutputStream.writeShort(experienceTotal);
	}
}
