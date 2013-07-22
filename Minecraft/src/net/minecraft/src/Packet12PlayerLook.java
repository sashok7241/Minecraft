package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet12PlayerLook extends Packet10Flying
{
	public Packet12PlayerLook()
	{
		rotating = true;
	}
	
	public Packet12PlayerLook(float p_i3334_1_, float p_i3334_2_, boolean p_i3334_3_)
	{
		yaw = p_i3334_1_;
		pitch = p_i3334_2_;
		onGround = p_i3334_3_;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		yaw = p_73267_1_.readFloat();
		pitch = p_73267_1_.readFloat();
		super.readPacketData(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeFloat(yaw);
		p_73273_1_.writeFloat(pitch);
		super.writePacketData(p_73273_1_);
	}
}
