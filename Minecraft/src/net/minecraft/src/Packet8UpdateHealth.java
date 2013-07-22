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
	
	public Packet8UpdateHealth(int p_i3352_1_, int p_i3352_2_, float p_i3352_3_)
	{
		healthMP = p_i3352_1_;
		food = p_i3352_2_;
		foodSaturation = p_i3352_3_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleUpdateHealth(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		healthMP = p_73267_1_.readShort();
		food = p_73267_1_.readShort();
		foodSaturation = p_73267_1_.readFloat();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeShort(healthMP);
		p_73273_1_.writeShort(food);
		p_73273_1_.writeFloat(foodSaturation);
	}
}
