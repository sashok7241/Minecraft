package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet13PlayerLookMove extends Packet10Flying
{
	public Packet13PlayerLookMove()
	{
		rotating = true;
		moving = true;
	}
	
	public Packet13PlayerLookMove(double p_i3333_1_, double p_i3333_3_, double p_i3333_5_, double p_i3333_7_, float p_i3333_9_, float p_i3333_10_, boolean p_i3333_11_)
	{
		xPosition = p_i3333_1_;
		yPosition = p_i3333_3_;
		stance = p_i3333_5_;
		zPosition = p_i3333_7_;
		yaw = p_i3333_9_;
		pitch = p_i3333_10_;
		onGround = p_i3333_11_;
		rotating = true;
		moving = true;
	}
	
	@Override public int getPacketSize()
	{
		return 41;
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readDouble();
		yPosition = p_73267_1_.readDouble();
		stance = p_73267_1_.readDouble();
		zPosition = p_73267_1_.readDouble();
		yaw = p_73267_1_.readFloat();
		pitch = p_73267_1_.readFloat();
		super.readPacketData(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeDouble(xPosition);
		p_73273_1_.writeDouble(yPosition);
		p_73273_1_.writeDouble(stance);
		p_73273_1_.writeDouble(zPosition);
		p_73273_1_.writeFloat(yaw);
		p_73273_1_.writeFloat(pitch);
		super.writePacketData(p_73273_1_);
	}
}
