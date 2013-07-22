package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet14BlockDig extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int face;
	public int status;
	
	public Packet14BlockDig()
	{
	}
	
	public Packet14BlockDig(int p_i3337_1_, int p_i3337_2_, int p_i3337_3_, int p_i3337_4_, int p_i3337_5_)
	{
		status = p_i3337_1_;
		xPosition = p_i3337_2_;
		yPosition = p_i3337_3_;
		zPosition = p_i3337_4_;
		face = p_i3337_5_;
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleBlockDig(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		status = p_73267_1_.read();
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.read();
		zPosition = p_73267_1_.readInt();
		face = p_73267_1_.read();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.write(status);
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.write(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.write(face);
	}
}
