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
	
	public Packet43Experience(float p_i3351_1_, int p_i3351_2_, int p_i3351_3_)
	{
		experience = p_i3351_1_;
		experienceTotal = p_i3351_2_;
		experienceLevel = p_i3351_3_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleExperience(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		experience = p_73267_1_.readFloat();
		experienceLevel = p_73267_1_.readShort();
		experienceTotal = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeFloat(experience);
		p_73273_1_.writeShort(experienceLevel);
		p_73273_1_.writeShort(experienceTotal);
	}
}
