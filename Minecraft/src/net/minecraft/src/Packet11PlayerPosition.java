package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet11PlayerPosition extends Packet10Flying
{
	public Packet11PlayerPosition()
	{
		moving = true;
	}
	
	public Packet11PlayerPosition(double p_i3332_1_, double p_i3332_3_, double p_i3332_5_, double p_i3332_7_, boolean p_i3332_9_)
	{
		xPosition = p_i3332_1_;
		yPosition = p_i3332_3_;
		stance = p_i3332_5_;
		zPosition = p_i3332_7_;
		onGround = p_i3332_9_;
		moving = true;
	}
	
	@Override public int getPacketSize()
	{
		return 33;
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readDouble();
		yPosition = p_73267_1_.readDouble();
		stance = p_73267_1_.readDouble();
		zPosition = p_73267_1_.readDouble();
		super.readPacketData(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeDouble(xPosition);
		p_73273_1_.writeDouble(yPosition);
		p_73273_1_.writeDouble(stance);
		p_73273_1_.writeDouble(zPosition);
		super.writePacketData(p_73273_1_);
	}
}
