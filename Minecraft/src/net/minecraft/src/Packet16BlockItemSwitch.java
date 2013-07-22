package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet16BlockItemSwitch extends Packet
{
	public int id;
	
	public Packet16BlockItemSwitch()
	{
	}
	
	public Packet16BlockItemSwitch(int p_i3345_1_)
	{
		id = p_i3345_1_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleBlockItemSwitch(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		id = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeShort(id);
	}
}
