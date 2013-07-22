package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet254ServerPing extends Packet
{
	public int readSuccessfully = 0;
	
	@Override public int getPacketSize()
	{
		return 0;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleServerPing(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		try
		{
			readSuccessfully = p_73267_1_.readByte();
		} catch(Throwable var3)
		{
			readSuccessfully = 0;
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
	}
}
