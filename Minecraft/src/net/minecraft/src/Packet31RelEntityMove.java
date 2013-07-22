package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet31RelEntityMove extends Packet30Entity
{
	public Packet31RelEntityMove()
	{
	}
	
	public Packet31RelEntityMove(int p_i3328_1_, byte p_i3328_2_, byte p_i3328_3_, byte p_i3328_4_)
	{
		super(p_i3328_1_);
		xPosition = p_i3328_2_;
		yPosition = p_i3328_3_;
		zPosition = p_i3328_4_;
	}
	
	@Override public int getPacketSize()
	{
		return 7;
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		super.readPacketData(p_73267_1_);
		xPosition = p_73267_1_.readByte();
		yPosition = p_73267_1_.readByte();
		zPosition = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		super.writePacketData(p_73273_1_);
		p_73273_1_.writeByte(xPosition);
		p_73273_1_.writeByte(yPosition);
		p_73273_1_.writeByte(zPosition);
	}
}
