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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleServerPing(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		try
		{
			readSuccessfully = par1DataInputStream.readByte();
		} catch(Throwable var3)
		{
			readSuccessfully = 0;
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
	}
}
