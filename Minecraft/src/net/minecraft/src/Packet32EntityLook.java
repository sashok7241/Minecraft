package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet32EntityLook extends Packet30Entity
{
	public Packet32EntityLook()
	{
		rotating = true;
	}
	
	public Packet32EntityLook(int p_i3330_1_, byte p_i3330_2_, byte p_i3330_3_)
	{
		super(p_i3330_1_);
		yaw = p_i3330_2_;
		pitch = p_i3330_3_;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 6;
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		super.readPacketData(p_73267_1_);
		yaw = p_73267_1_.readByte();
		pitch = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		super.writePacketData(p_73273_1_);
		p_73273_1_.writeByte(yaw);
		p_73273_1_.writeByte(pitch);
	}
}
