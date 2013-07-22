package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet106Transaction extends Packet
{
	public int windowId;
	public short shortWindowId;
	public boolean accepted;
	
	public Packet106Transaction()
	{
	}
	
	public Packet106Transaction(int p_i3307_1_, short p_i3307_2_, boolean p_i3307_3_)
	{
		windowId = p_i3307_1_;
		shortWindowId = p_i3307_2_;
		accepted = p_i3307_3_;
	}
	
	@Override public int getPacketSize()
	{
		return 4;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleTransaction(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		windowId = p_73267_1_.readByte();
		shortWindowId = p_73267_1_.readShort();
		accepted = p_73267_1_.readByte() != 0;
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(windowId);
		p_73273_1_.writeShort(shortWindowId);
		p_73273_1_.writeByte(accepted ? 1 : 0);
	}
}
