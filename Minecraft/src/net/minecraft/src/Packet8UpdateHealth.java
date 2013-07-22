package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet
{
	public float healthMP;
	public int food;
	public float foodSaturation;
	
	public Packet8UpdateHealth()
	{
	}
	
	public Packet8UpdateHealth(float par1, int par2, float par3)
	{
		healthMP = par1;
		food = par2;
		foodSaturation = par3;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleUpdateHealth(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		healthMP = par1DataInput.readFloat();
		food = par1DataInput.readShort();
		foodSaturation = par1DataInput.readFloat();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeFloat(healthMP);
		par1DataOutput.writeShort(food);
		par1DataOutput.writeFloat(foodSaturation);
	}
}
