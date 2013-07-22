package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet
{
	public int healthMP;
	public int food;
	public float foodSaturation;
	
	public Packet8UpdateHealth()
	{
	}
	
	public Packet8UpdateHealth(int par1, int par2, float par3)
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		healthMP = par1DataInputStream.readShort();
		food = par1DataInputStream.readShort();
		foodSaturation = par1DataInputStream.readFloat();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeShort(healthMP);
		par1DataOutputStream.writeShort(food);
		par1DataOutputStream.writeFloat(foodSaturation);
	}
}
