package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet130UpdateSign extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public String[] signLines;
	
	public Packet130UpdateSign()
	{
		isChunkDataPacket = true;
	}
	
	public Packet130UpdateSign(int p_i3357_1_, int p_i3357_2_, int p_i3357_3_, String[] p_i3357_4_)
	{
		isChunkDataPacket = true;
		xPosition = p_i3357_1_;
		yPosition = p_i3357_2_;
		zPosition = p_i3357_3_;
		signLines = new String[] { p_i3357_4_[0], p_i3357_4_[1], p_i3357_4_[2], p_i3357_4_[3] };
	}
	
	@Override public int getPacketSize()
	{
		int var1 = 0;
		for(int var2 = 0; var2 < 4; ++var2)
		{
			var1 += signLines[var2].length();
		}
		return var1;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleUpdateSign(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readShort();
		zPosition = p_73267_1_.readInt();
		signLines = new String[4];
		for(int var2 = 0; var2 < 4; ++var2)
		{
			signLines[var2] = readString(p_73267_1_, 15);
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeShort(yPosition);
		p_73273_1_.writeInt(zPosition);
		for(int var2 = 0; var2 < 4; ++var2)
		{
			writeString(signLines[var2], p_73273_1_);
		}
	}
}
