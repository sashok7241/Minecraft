package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet33RelEntityMoveLook extends Packet30Entity
{
	public Packet33RelEntityMoveLook()
	{
		rotating = true;
	}
	
	public Packet33RelEntityMoveLook(int p_i3329_1_, byte p_i3329_2_, byte p_i3329_3_, byte p_i3329_4_, byte p_i3329_5_, byte p_i3329_6_)
	{
		super(p_i3329_1_);
		xPosition = p_i3329_2_;
		yPosition = p_i3329_3_;
		zPosition = p_i3329_4_;
		yaw = p_i3329_5_;
		pitch = p_i3329_6_;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		super.readPacketData(p_73267_1_);
		xPosition = p_73267_1_.readByte();
		yPosition = p_73267_1_.readByte();
		zPosition = p_73267_1_.readByte();
		yaw = p_73267_1_.readByte();
		pitch = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		super.writePacketData(p_73273_1_);
		p_73273_1_.writeByte(xPosition);
		p_73273_1_.writeByte(yPosition);
		p_73273_1_.writeByte(zPosition);
		p_73273_1_.writeByte(yaw);
		p_73273_1_.writeByte(pitch);
	}
}
