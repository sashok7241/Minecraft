package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet
{
	public String reason;
	
	public Packet255KickDisconnect()
	{
	}
	
	public Packet255KickDisconnect(String p_i3316_1_)
	{
		reason = p_i3316_1_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return reason.length();
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleKickDisconnect(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		reason = readString(p_73267_1_, 256);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(reason, p_73273_1_);
	}
}
